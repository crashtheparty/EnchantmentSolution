package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.data.inventory.InventoryData;
import org.ctp.crashapi.data.items.ItemSerialization;
import org.ctp.crashapi.data.items.ItemSlot;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.crashapi.events.EquipEvent.EquipMethod;
import org.ctp.crashapi.listeners.EquipListener;
import org.ctp.crashapi.resources.advancements.CrashAdvancementProgress;
import org.ctp.crashapi.version.PluginVersion;
import org.ctp.crashapi.version.Version;
import org.ctp.crashapi.version.Version.VersionType;
import org.ctp.crashapi.version.VersionCheck;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.commands.EnchantmentSolutionCommand;
import org.ctp.enchantmentsolution.database.ESBackup;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.listeners.*;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementEntityListener;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementPlayerListener;
import org.ctp.enchantmentsolution.listeners.chestloot.ChestLootListener;
import org.ctp.enchantmentsolution.listeners.enchantments.*;
import org.ctp.enchantmentsolution.listeners.fishing.EnchantsFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingListener;
import org.ctp.enchantmentsolution.listeners.hard.HardModeListener;
import org.ctp.enchantmentsolution.listeners.inventory.*;
import org.ctp.enchantmentsolution.listeners.mobs.MobSpawning;
import org.ctp.enchantmentsolution.listeners.mobs.PiglinTrade;
import org.ctp.enchantmentsolution.listeners.mobs.Villagers;
import org.ctp.enchantmentsolution.listeners.vanilla.AnvilListener;
import org.ctp.enchantmentsolution.listeners.vanilla.EnchantmentListener;
import org.ctp.enchantmentsolution.listeners.vanilla.GrindstoneListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.rpg.listener.RPGListener;
import org.ctp.enchantmentsolution.threads.*;
import org.ctp.enchantmentsolution.utils.*;
import org.ctp.enchantmentsolution.utils.abilityhelpers.AnimalMob;
import org.ctp.enchantmentsolution.utils.abilityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abilityhelpers.EntityAccuracy;
import org.ctp.enchantmentsolution.utils.commands.ESCommand;
import org.ctp.enchantmentsolution.utils.compatibility.AuctionHouseUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.debug.ESChatUtils;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile;
import org.ctp.enchantmentsolution.utils.files.SaveUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;

import solutions.nuhvel.spigot.rc.RestrictedCreativeAPI;

public class EnchantmentSolution extends CrashAPIPlugin {

	private static EnchantmentSolution PLUGIN;
	private static List<CrashAdvancementProgress> PROGRESS = new ArrayList<CrashAdvancementProgress>();
	private static List<AnimalMob> ANIMALS = new ArrayList<AnimalMob>();
	private static List<DrownedEntity> DROWNED = new ArrayList<DrownedEntity>();
	private static List<EntityAccuracy> ACCURACY = new ArrayList<EntityAccuracy>();
	private static List<ESPlayer> PLAYERS = new ArrayList<ESPlayer>();
	private static Configurations CONFIGURATIONS;
	private List<InventoryData> inventories = new ArrayList<InventoryData>();
	private boolean restrictedCreative = false, quests = false;
	private PluginVersion pluginVersion;
	private BackupDB db;
	private Plugin jobsReborn;
	private VersionCheck check;
	private WikiThread wiki;
	private String mcmmoVersion, mcmmoType = "Disabled";
	private Plugin veinMiner;
	private RPGListener rpg;
	private static ArrayList<Location> GAIA_BLOCKS = new ArrayList<Location>();

	@Override
	public void onLoad() {
		PLUGIN = this;
		pluginVersion = new PluginVersion(this, new Version(getDescription().getVersion(), VersionType.UNKNOWN));

		if (!getDataFolder().exists()) getDataFolder().mkdirs();
		Chatable.get().sendInfo("Minecraft Version: " + VersionUtils.getMinecraftVersion());
		Chatable.get().sendInfo("Minecraft API Version: " + VersionUtils.getMinecraftAPIVersion());
		Chatable.get().sendInfo("EnchantmentSolution Version: " + VersionUtils.getESVersionName());

		db = new ESBackup(this);
		db.load();
		RegisterEnchantments.addEnchantments();
		Chatable.get().sendInfo("Loading Item Break Types...");
		ItemBreakFile.setFiles();
		ItemSpecialBreakFile.setFiles();
		Chatable.get().sendInfo("Item Break Types Loaded!");
		CONFIGURATIONS = Configurations.getConfigurations();
		CONFIGURATIONS.onEnable();
	}

	@Override
	public void onEnable() {
		registerEvent(new InventoryClick());
		registerEvent(new InventoryClose());
		registerEvent(new PlayerInteract());
		registerEvent(new ChatMessage());
		registerEvent(new InventoryPlayerListener());

		registerEvent(new ExtraBlockListener());
		registerEvent(new VanishListener());
		registerEvent(new EquipListener());
		registerEvent(new GlobalPlayerListener());

		registerEvent(new FishingListener());
		registerEvent(new DeathListener());
		registerEvent(new SoulListener());
		registerEvent(new DamageListener());
		registerEvent(new PlayerListener());
		registerEvent(new AfterEffectsListener());
		registerEvent(new AttributeListener());
		registerEvent(new ProjectileListener());
		registerEvent(new BlockListener());
		registerEvent(new PotionEffectListener());

		registerEvent(new AnvilListener());
		registerEvent(new EnchantmentListener());
		registerEvent(new GrindstoneListener());

		registerEvent(new MobSpawning());
		registerEvent(new Villagers());
		registerEvent(new PiglinTrade());
		registerEvent(new ChestLootListener());

		registerEvent(new AdvancementEntityListener());
		registerEvent(new AdvancementPlayerListener());

		rpg = new RPGListener();
		registerEvent(rpg);
		Bukkit.getScheduler().runTaskTimer(PLUGIN, rpg, 1l, 1l);
		registerEvent(new HardModeListener());

		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new AbilityThreads(), 80l, 80l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new AdvancementThread(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new EntityThreads(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new WalkerThread(), 1l, 1l);

		EnchantmentSolutionCommand c = new EnchantmentSolutionCommand();
		getCommand("EnchantmentSolution").setExecutor(c);
		getCommand("EnchantmentSolution").setTabCompleter(c);
		for(ESCommand s: EnchantmentSolutionCommand.getCommands()) {
			PluginCommand command = getCommand(s.getCommand());
			if (command != null) {
				command.setExecutor(c);
				command.setTabCompleter(c);
				command.setAliases(s.getAliases());
			} else
				getChat().sendWarning("Couldn't find command '" + s.getCommand() + ".'");
		}

		check = new VersionCheck(pluginVersion, "https://raw.githubusercontent.com/crashtheparty/EnchantmentSolution/master/VersionHistory", "https://www.spigotmc.org/resources/enchantment-solution.59556/", "https://github.com/crashtheparty/EnchantmentSolution", ConfigString.LATEST_VERSION.getBoolean(), ConfigString.EXPERIMENTAL_VERSION.getBoolean());
		registerEvent(check);
		checkVersion();
		MetricsUtils.init();
		AdvancementUtils.createAdvancements();

		registerEvent(new WikiListener());
		wiki = new WikiThread();
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, wiki, 20l, 20l);

		InterfaceRegistry.firstLoad();

		Bukkit.getScheduler().runTaskLater(this, () -> {
			try {
				SaveUtils.getData();
			} catch (Exception ex) {
				getChat().sendWarning("Error in loading data to data.yml - will possibly break.");
				Chatable.sendStackTrace(ex);
			}
			CONFIGURATIONS.getEnchantments().setEnchantmentInformation();
			CONFIGURATIONS.getEnchantments().save();
			addCompatibility();
		}, 1l);
	}

	@Override
	public void onDisable() {
		closeInventories(null);
		SaveUtils.setData();
	}

	public void closeInventories(Class<? extends InventoryData> clazz) {
		List<InventoryData> data = new ArrayList<InventoryData>();
		data.addAll(inventories);
		for(InventoryData inv: data)
			if (clazz == null || clazz.isAssignableFrom(data.getClass())) inv.close(true);
	}

	private void registerEvent(Listener l) {
		getServer().getPluginManager().registerEvents(l, this);
	}

	public BackupDB getDb() {
		return db;
	}

	public static EnchantmentSolution getPlugin() {
		return PLUGIN;
	}

	@Override
	public PluginVersion getPluginVersion() {
		return pluginVersion;
	}

	public InventoryData getInventory(Player player) {
		for(InventoryData inv: inventories)
			if (inv.getPlayer().getUniqueId().equals(player.getUniqueId())) return inv;

		return null;
	}

	public boolean hasInventory(InventoryData inv) {
		return inventories.contains(inv);
	}

	public void addInventory(InventoryData inv) {
		inventories.add(inv);
	}

	public void removeInventory(InventoryData inv) {
		inventories.remove(inv);
	}

	public static List<CrashAdvancementProgress> getProgress() {
		return PROGRESS;
	}

	public static void addProgress(CrashAdvancementProgress progress) {
		PROGRESS.add(progress);
	}

	public static CrashAdvancementProgress getAdvancementProgress(OfflinePlayer player, ESAdvancement advancement,
	String criteria) {
		for(CrashAdvancementProgress progress: EnchantmentSolution.getProgress())
			if (progress.getPlayer().equals(player) && progress.getAdvancement() == advancement && progress.getCriteria().equals(criteria)) return progress;
		CrashAdvancementProgress progress = new CrashAdvancementProgress(advancement, criteria, 0, player);
		EnchantmentSolution.addProgress(progress);
		return progress;
	}

	public static List<CrashAdvancementProgress> getAdvancementProgress() {
		List<CrashAdvancementProgress> progress = new ArrayList<CrashAdvancementProgress>();
		for(CrashAdvancementProgress pr: PROGRESS)
			progress.add(pr);
		return progress;
	}

	public static void completed(CrashAdvancementProgress esProgress) {
		PROGRESS.remove(esProgress);
	}

	public static boolean exists(Player player, ESAdvancement advancement, String criteria) {
		for(CrashAdvancementProgress progress: PROGRESS)
			if (progress.getPlayer().equals(player) && progress.getAdvancement() == advancement && progress.getCriteria().equals(criteria)) return true;
		return false;
	}

	public static List<AnimalMob> getAnimals() {
		return ANIMALS;
	}

	public static void setAnimals(List<AnimalMob> animals) {
		ANIMALS = animals;
	}

	public static void addAnimals(AnimalMob mob) {
		ANIMALS.add(mob);
	}

	public static void removeAnimals(AnimalMob remove) {
		ANIMALS.remove(remove);
	}

	public static List<DrownedEntity> getDrowned() {
		return DROWNED;
	}

	public static void addDrowned(DrownedEntity drowned) {
		DROWNED.add(drowned);
	}

	public static List<EntityAccuracy> getAccuracy() {
		return ACCURACY;
	}

	public static void addAccuracy(EntityAccuracy accuracy) {
		ACCURACY.add(accuracy);
	}

	public void setVersionCheck(boolean getRelease, boolean getExperimental) {
		check.setLatestVersion(getRelease);
		check.setExperimentalVersion(getExperimental);
		check.run();
	}

	public String getMcMMOVersion() {
		return mcmmoVersion;
	}

	public Plugin getVeinMiner() {
		return veinMiner;
	}

	public boolean isJobsEnabled() {
		return jobsReborn != null;
	}

	public String getMcMMOType() {
		return mcmmoType;
	}

	private void checkVersion() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, check, 0, 20 * 60 * 60 * 4l);
	}

	public WikiThread getWiki() {
		return wiki;
	}

	@Override
	public void addCompatibility() {
		if (Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
			mcmmoVersion = Bukkit.getPluginManager().getPlugin("mcMMO").getDescription().getVersion();
			getChat().sendInfo("mcMMO Version: " + mcmmoVersion);
			if (mcmmoVersion.substring(0, mcmmoVersion.indexOf(".")).equals("2")) {
				getChat().sendInfo("Using the Overhaul Version!");
				String[] mcVersion = mcmmoVersion.split("\\.");
				boolean warning = false;
				for(int i = 0; i < mcVersion.length; i++)
					try {
						int num = Integer.parseInt(mcVersion[i]);
						if (i == 0 && num > 2) warning = true;
						else if (i == 1 && num > 1) warning = true;
						else if (i == 2 && num > 205) warning = true;
					} catch (NumberFormatException ex) {
						warning = true;
					}
				if (warning) {
					getChat().sendWarning("McMMO Overhaul updates sporidically. Compatibility may break between versions.");
					getChat().sendWarning("If there are any compatibility issues, please notify the plugin author immediately.");
					getChat().sendWarning("Current Working Version: 2.1.205");
				}
				mcmmoType = "Overhaul";
			} else {
				getChat().sendWarning("Using the Classic Version! Compatibility should be intact.");
				mcmmoType = "Classic";
			}
			registerEvent(new McMMOFishingListener());
		} else {
			mcmmoType = "Disabled";
			registerEvent(new EnchantsFishingListener());
		}

		if (!mcmmoType.equals("Disabled")) registerEvent(new McMMOAbility());

		if (Bukkit.getPluginManager().isPluginEnabled("Jobs")) {
			jobsReborn = Bukkit.getPluginManager().getPlugin("Jobs");
			getChat().sendInfo("Jobs Reborn compatibility enabled!");
		}

		if (Bukkit.getPluginManager().isPluginEnabled("VeinMiner")) {
			veinMiner = Bukkit.getPluginManager().getPlugin("VeinMiner");
			getChat().sendInfo("Vein Miner compatibility enabled!");
			registerEvent(new VeinMinerListener());
		}

		if (Bukkit.getPluginManager().isPluginEnabled("AuctionHouse")) {
			AuctionHouseUtils.resetAuctionHouse();
			getChat().sendInfo("Auction House compatibility enabled!");
		}

		if (Bukkit.getPluginManager().isPluginEnabled("RestrictedCreative")) {
			restrictedCreative = true;
			getChat().sendInfo("Restricted Creative compatibility enabled!");
		}

		if (Bukkit.getPluginManager().isPluginEnabled("Quests")) try {
			Class<?> clazz = BukkitQuestsPlugin.class;
			clazz.getName();
			quests = true;
			getChat().sendInfo("Quests compatibility enabled!");
		} catch (Exception | Error ex) {}
	}

	public static ESPlayer getESPlayer(OfflinePlayer player) {
		for(ESPlayer es: PLAYERS)
			if (es.getPlayer().getUniqueId().equals(player.getUniqueId())) return es;
		ESPlayer es = new ESPlayer(player);
		PLAYERS.add(es);
		return es;
	}

	public static List<ESPlayer> getAllESPlayers(boolean online) {
		List<ESPlayer> players = new ArrayList<ESPlayer>();
		if (online) for(Player player: Bukkit.getOnlinePlayers())
			players.add(getESPlayer(player));
		else
			for(OfflinePlayer player: Bukkit.getOfflinePlayers())
				players.add(getESPlayer(player));
		return players;
	}

	public static List<ESPlayer> getOverkillDeathPlayers() {
		List<ESPlayer> players = new ArrayList<ESPlayer>();
		for(Player player: Bukkit.getOnlinePlayers()) {
			ESPlayer es = getESPlayer(player);
			if (es.getOverkillDeaths().size() > 0) players.add(es);
		}
		return players;
	}

	public static NamespacedKey getKey(String name) {
		return new NamespacedKey(getPlugin(), name);
	}

	public boolean hasRestrictedCreative() {
		return restrictedCreative;
	}

	public boolean isRestrictedCreative(Block b) {
		return RestrictedCreativeAPI.isCreative(b);
	}

	public boolean hasQuests() {
		return quests;
	}

	public void reEquipItems() {
		for(Player p: Bukkit.getOnlinePlayers()) {
			ESPlayer esPlayer = getESPlayer(p);
			for(ItemSlot slot: esPlayer.getEquippedAndType()) {
				ItemStack item = slot.getItem();
				if (item != null && EnchantmentUtils.getTotalEnchantments(item) > 0) {
					EquipEvent armorEquipEvent = new EquipEvent(p, EquipMethod.COMMAND, slot.getType(), item, item);
					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
				}
			}
		}
	}

	public void reEquipItems(ESPlayer player, EnchantmentWrapper enchantment) {
		if (!player.isOnline()) return;
		for(ItemSlot slot: player.getEquippedAndType()) {
			ItemStack item = slot.getItem();
			if (item != null && EnchantmentUtils.hasEnchantment(item, enchantment)) {
				EquipEvent armorEquipEvent = new EquipEvent(player.getOnlinePlayer(), EquipMethod.COMMAND, slot.getType(), item, item);
				Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
			}
		}
	}

	@Override
	public String getStarter() {
		return getLanguageFile().getString("starter");
	}

	@Override
	public ESChatUtils getChat() {
		return ESChatUtils.getESChat();
	}

	@Override
	public ItemSerialization getItemSerial() {
		return ItemSerialization.getItemSerial(PLUGIN);
	}

	@Override
	public Configurations getConfigurations() {
		return CONFIGURATIONS;
	}

	@Override
	public YamlConfig getLanguageFile() {
		return CONFIGURATIONS.getLanguage().getConfig();
	}

	public static boolean gaiaHasLocation(Location loc) {
		for(Location l: GAIA_BLOCKS)
			if (loc.getBlock().equals(l.getBlock())) return true;
		return false;
	}

	public static boolean gaiaRemoveLocation(Location loc) {
		return GAIA_BLOCKS.remove(loc);
	}

	public static void gaiaAddLocation(Location loc) {
		if (!gaiaHasLocation(loc)) GAIA_BLOCKS.add(loc);
	}

	public static List<Location> gaiaGetLocations() {
		List<Location> locs = new ArrayList<Location>();
		for(Location block: GAIA_BLOCKS)
			locs.add(block.clone());
		return locs;
	}
}
