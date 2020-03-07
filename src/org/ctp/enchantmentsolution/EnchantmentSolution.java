package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.advancements.ESAdvancementProgress;
import org.ctp.enchantmentsolution.commands.*;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.listeners.*;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementEntityDeath;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementPlayerEvent;
import org.ctp.enchantmentsolution.listeners.chestloot.ChestLootListener;
import org.ctp.enchantmentsolution.listeners.enchantments.*;
import org.ctp.enchantmentsolution.listeners.fishing.EnchantsFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingListener;
import org.ctp.enchantmentsolution.listeners.inventory.*;
import org.ctp.enchantmentsolution.listeners.legacy.UpdateEnchantments;
import org.ctp.enchantmentsolution.listeners.mobs.MobSpawning;
import org.ctp.enchantmentsolution.listeners.mobs.Villagers;
import org.ctp.enchantmentsolution.listeners.vanilla.AnvilListener;
import org.ctp.enchantmentsolution.listeners.vanilla.EnchantmentListener;
import org.ctp.enchantmentsolution.listeners.vanilla.GrindstoneListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.threads.*;
import org.ctp.enchantmentsolution.utils.*;
import org.ctp.enchantmentsolution.utils.abillityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abillityhelpers.EntityAccuracy;
import org.ctp.enchantmentsolution.utils.compatibility.AuctionHouseUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.files.SaveUtils;
import org.ctp.enchantmentsolution.version.BukkitVersion;
import org.ctp.enchantmentsolution.version.PluginVersion;
import org.ctp.enchantmentsolution.version.VersionCheck;

public class EnchantmentSolution extends JavaPlugin {

	private static EnchantmentSolution PLUGIN;
	private static List<ESAdvancementProgress> PROGRESS = new ArrayList<ESAdvancementProgress>();
	private static List<AnimalMob> ANIMALS = new ArrayList<AnimalMob>();
	private static List<DrownedEntity> DROWNED = new ArrayList<DrownedEntity>();
	private static List<EntityAccuracy> ACCURACY = new ArrayList<EntityAccuracy>();
	private static HashMap<String, List<ItemStack>> SOUL_ITEMS = new HashMap<>();
	private List<InventoryData> inventories = new ArrayList<InventoryData>();
	private boolean initialization = true;
	private BukkitVersion bukkitVersion;
	private PluginVersion pluginVersion;
	private SQLite db;
	private Plugin jobsReborn;
	private VersionCheck check;
	private WikiThread wiki;
	private String mcmmoVersion, mcmmoType;
	private Plugin veinMiner;

	@Override
	public void onEnable() {
		PLUGIN = this;
		bukkitVersion = new BukkitVersion();
		pluginVersion = new PluginVersion(this, getDescription().getVersion());

		if (!getDataFolder().exists()) getDataFolder().mkdirs();

		db = new SQLite(this);
		db.load();
		RegisterEnchantments.addRegisterEnchantments();

		Configurations.onEnable();

		registerEvent(new InventoryClick());
		registerEvent(new InventoryClose());
		registerEvent(new PlayerInteract());
		registerEvent(new ChatMessage());

		registerEvent(new ExtraBlockListener());
		registerEvent(new VanishListener());
		registerEvent(new EquipListener());
		registerEvent(new BadAttributesListener());

		registerEvent(new FishingListener());
		registerEvent(new DropsListener());
		registerEvent(new SoulListener());
		registerEvent(new DamageListener());
		registerEvent(new PlayerListener());
		registerEvent(new AfterEffectsListener());
		registerEvent(new AttributeListener());
		registerEvent(new ProjectileListener());
		registerEvent(new BlockListener());
		registerEvent(new PotionEffectListener());
		registerEvent(new InventoryListener());

		registerEvent(new AnvilListener());
		registerEvent(new EnchantmentListener());
		registerEvent(new GrindstoneListener());

		registerEvent(new MobSpawning());
		registerEvent(new Villagers());
		registerEvent(new UpdateEnchantments());
		registerEvent(new ChestLootListener());

		registerEvent(new AdvancementEntityDeath());
		registerEvent(new AdvancementPlayerEvent());

		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new AbilityRunnable(), 80l, 80l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new AdvancementThread(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new ElytraRunnable(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new MiscRunnable(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new SnapshotRunnable(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, new WalkerRunnable(), 1l, 1l);

		getCommand("Enchant").setExecutor(new Enchant());
		getCommand("Info").setExecutor(new EnchantInfo());
		getCommand("RemoveEnchant").setExecutor(new RemoveEnchant());
		getCommand("EnchantUnsafe").setExecutor(new UnsafeEnchant());
		getCommand("ESReload").setExecutor(new Reload());
		getCommand("ESConfig").setExecutor(new ConfigEdit());
		getCommand("ESReset").setExecutor(new Reset());
		getCommand("ESDebug").setExecutor(new Debug());
		getCommand("ESCalc").setExecutor(new EnchantabilityCalculator());
		getCommand("ESBook").setExecutor(new Book());
		getCommand("ESAnvil").setExecutor(new AnvilCommand());
		getCommand("ESGrindstone").setExecutor(new GrindstoneCommand());
		getCommand("ConfigLore").setExecutor(new ConfigLore());
		getCommand("Enchant").setTabCompleter(new PlayerChatTabComplete());
		getCommand("Info").setTabCompleter(new PlayerChatTabComplete());
		getCommand("RemoveEnchant").setTabCompleter(new PlayerChatTabComplete());
		getCommand("EnchantUnsafe").setTabCompleter(new PlayerChatTabComplete());

		check = new VersionCheck(pluginVersion, "https://raw.githubusercontent.com/crashtheparty/EnchantmentSolution/master/VersionHistory", "https://www.spigotmc.org/resources/enchantment-solution.59556/", "https://github.com/crashtheparty/EnchantmentSolution", ConfigString.LATEST_VERSION.getBoolean(), ConfigString.EXPERIMENTAL_VERSION.getBoolean());
		registerEvent(check);
		checkVersion();
		MetricsUtils.init();
		AdvancementUtils.createAdvancements();

		registerEvent(new WikiListener());
		wiki = new WikiThread();
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, wiki, 20l, 20l);
		initialization = false;

		Bukkit.getScheduler().runTaskLater(this, () -> {
			SaveUtils.getData();
			addCompatibility();
		}, 1l);
	}

	@Override
	public void onDisable() {
		List<InventoryData> data = new ArrayList<InventoryData>();
		data.addAll(inventories);
		for(InventoryData inv: data)
			inv.close(true);
		SaveUtils.setData();
	}

	private void registerEvent(Listener l) {
		getServer().getPluginManager().registerEvents(l, this);
	}

	public SQLite getDb() {
		return db;
	}

	public static EnchantmentSolution getPlugin() {
		return PLUGIN;
	}

	public boolean isInitializing() {
		return initialization;
	}

	public BukkitVersion getBukkitVersion() {
		return bukkitVersion;
	}

	public PluginVersion getPluginVersion() {
		return pluginVersion;
	}

	public void resetInventories() {
		for(int i = inventories.size() - 1; i >= 0; i--) {
			InventoryData inv = inventories.get(i);
			inv.close(true);
		}
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

	public static List<ESAdvancementProgress> getProgress() {
		return PROGRESS;
	}

	public static void addProgress(ESAdvancementProgress progress) {
		PROGRESS.add(progress);
	}

	public static ESAdvancementProgress getAdvancementProgress(OfflinePlayer player, ESAdvancement advancement,
	String criteria) {
		for(ESAdvancementProgress progress: EnchantmentSolution.getProgress())
			if (progress.getPlayer().equals(player) && progress.getAdvancement() == advancement && progress.getCriteria().equals(criteria)) return progress;
		ESAdvancementProgress progress = new ESAdvancementProgress(advancement, criteria, 0, player);
		EnchantmentSolution.addProgress(progress);
		return progress;
	}

	public static List<ESAdvancementProgress> getAdvancementProgress() {
		List<ESAdvancementProgress> progress = new ArrayList<ESAdvancementProgress>();
		for(ESAdvancementProgress pr: PROGRESS)
			progress.add(pr);
		return progress;
	}

	public static void completed(ESAdvancementProgress esProgress) {
		PROGRESS.remove(esProgress);
	}

	public static boolean exists(Player player, ESAdvancement advancement, String criteria) {
		for(ESAdvancementProgress progress: PROGRESS)
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

	public static List<ItemStack> getSoulItems(Player player) {
		return SOUL_ITEMS.get(player.getUniqueId().toString());
	}

	public static void setSoulItems(Player player, List<ItemStack> items) {
		SOUL_ITEMS.put(player.getUniqueId().toString(), items);
	}

	public static void removeSoulItems(Player player) {
		SOUL_ITEMS.put(player.getUniqueId().toString(), null);
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
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, check, 20 * 60 * 60 * 4l, 20 * 60 * 60 * 4l);
	}

	public WikiThread getWiki() {
		return wiki;
	}

	public void addCompatibility() {
		if (Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
			mcmmoVersion = Bukkit.getPluginManager().getPlugin("mcMMO").getDescription().getVersion();
			ChatUtils.sendToConsole(Level.INFO, "mcMMO Version: " + mcmmoVersion);
			if (mcmmoVersion.substring(0, mcmmoVersion.indexOf(".")).equals("2")) {
				ChatUtils.sendToConsole(Level.INFO, "Using the Overhaul Version!");
				String[] mcVersion = mcmmoVersion.split("\\.");
				boolean warning = false;
				for(int i = 0; i < mcVersion.length; i++)
					try {
						int num = Integer.parseInt(mcVersion[i]);
						if (i == 0 && num > 2) warning = true;
						else if (i == 1 && num > 1) warning = true;
						else if (i == 2 && num > 111) warning = true;
					} catch (NumberFormatException ex) {
						warning = true;
					}
				if (warning) {
					ChatUtils.sendToConsole(Level.WARNING, "McMMO Overhaul updates sporidically. Compatibility may break between versions.");
					ChatUtils.sendToConsole(Level.WARNING, "If there are any compatibility issues, please notify the plugin author immediately.");
					ChatUtils.sendToConsole(Level.WARNING, "Current Working Version: 2.1.111");
				}
				mcmmoType = "Overhaul";
			} else {
				ChatUtils.sendToConsole(Level.INFO, "Using the Classic Version! Compatibility should be intact.");
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
			ChatUtils.sendInfo("Jobs Reborn compatibility enabled!");
		}

		if (Bukkit.getPluginManager().isPluginEnabled("VeinMiner")) {
			veinMiner = Bukkit.getPluginManager().getPlugin("VeinMiner");
			ChatUtils.sendInfo("Vein Miner compatibility enabled!");
			registerEvent(new VeinMinerListener());
		}

		if (Bukkit.getPluginManager().isPluginEnabled("AuctionHouse")) {
			AuctionHouseUtils.resetAuctionHouse();
			ChatUtils.sendInfo("Auction House compatibility enabled!");
		}
	}
}
