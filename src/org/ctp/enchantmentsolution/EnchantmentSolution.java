package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.commands.*;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.listeners.*;
import org.ctp.enchantmentsolution.listeners.abilities.*;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementEntityDeath;
import org.ctp.enchantmentsolution.listeners.advancements.AdvancementThread;
import org.ctp.enchantmentsolution.listeners.chestloot.ChestLootListener;
import org.ctp.enchantmentsolution.listeners.fishing.EnchantsFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingNMS;
import org.ctp.enchantmentsolution.listeners.legacy.UpdateEnchantments;
import org.ctp.enchantmentsolution.listeners.mobs.MobSpawning;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;
import org.ctp.enchantmentsolution.utils.save.SaveUtils;
import org.ctp.enchantmentsolution.version.BukkitVersion;
import org.ctp.enchantmentsolution.version.PluginVersion;
import org.ctp.eswrapper.EsWrapper;

public class EnchantmentSolution extends JavaPlugin {

	private static EnchantmentSolution PLUGIN;
	private List<InventoryData> inventories = new ArrayList<InventoryData>();
	private boolean disable = false, initialization = true;
	private SQLite db;
	private String mcmmoType;
	private BukkitVersion bukkitVersion;
	private PluginVersion pluginVersion;
	private Plugin jobsReborn;
	private ConfigFiles files;
	private VersionCheck check;
	@SuppressWarnings("unused")
	private AdvancementUtils advancementUtils;

	public void onEnable() {
		PLUGIN = this;
		bukkitVersion = new BukkitVersion();
		pluginVersion = new PluginVersion(this, getDescription().getVersion());
		if(!bukkitVersion.isVersionAllowed()) {
			Bukkit.getLogger().log(Level.WARNING, "Bukkit Version " + bukkitVersion.getVersion() + " is not compatible with this plugin. Please use a version that is compatible.");
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
			return;
		}
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		db = new SQLite(this);
		db.load();
		DefaultEnchantments.addDefaultEnchantments();
		
		files = new ConfigFiles(this);
		files.createConfigFiles();
		
		if(disable) {
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
			return;
		}

		SaveUtils.getData();
		
		getServer().getPluginManager().registerEvents(new PlayerInteract(),
				this);
		getServer().getPluginManager().registerEvents(new InventoryClick(),
				this);
		getServer().getPluginManager().registerEvents(new InventoryClose(),
				this);
		getServer().getPluginManager().registerEvents(new SoulboundListener(),
				this);
		getServer().getPluginManager().registerEvents(
				new ShockAspectListener(), this);
		getServer().getPluginManager().registerEvents(new WarpListener(), this);
		getServer().getPluginManager().registerEvents(new KnockUpListener(),
				this);
		getServer().getPluginManager().registerEvents(new BeheadingListener(),
				this);
		getServer().getPluginManager().registerEvents(new WarpListener(), this);
		getServer().getPluginManager().registerEvents(new ExpShareListener(),
				this);
		getServer().getPluginManager().registerEvents(
				new MagmaWalkerListener(), this);
		getServer().getPluginManager().registerEvents(new SniperListener(),
				this);
		getServer().getPluginManager().registerEvents(new TelepathyListener(),
				this);
		getServer().getPluginManager().registerEvents(new SmelteryListener(),
				this);
		getServer().getPluginManager().registerEvents(new SacrificeListener(),
				this);
		getServer().getPluginManager().registerEvents(new FishingListener(), this);
		getServer().getPluginManager().registerEvents(new TankListener(), this);
		getServer().getPluginManager().registerEvents(new BrineListener(), this);
		getServer().getPluginManager().registerEvents(new DrownedListener(), this);
		getServer().getPluginManager().registerEvents(new WidthHeightListener(), this);
		getServer().getPluginManager().registerEvents(new VoidWalkerListener(), this);
		getServer().getPluginManager().registerEvents(new IcarusListener(), this);
		getServer().getPluginManager().registerEvents(new IronDefenseListener(), this);
		getServer().getPluginManager().registerEvents(new HardBounceListener(), this);
		getServer().getPluginManager().registerEvents(new MagicGuardListener(), this);
		getServer().getPluginManager().registerEvents(new SplatterFestListener(), this);
		getServer().getPluginManager().registerEvents(new SandVeilListener(), this);
		getServer().getPluginManager().registerEvents(new TransmutationListener(), this);
		getServer().getPluginManager().registerEvents(new GoldDiggerListener(), this);
		getServer().getPluginManager().registerEvents(new FlowerGiftListener(), this);
		getServer().getPluginManager().registerEvents(new StoneThrowListener(), this);
		getServer().getPluginManager().registerEvents(new PillageListener(), this);
		getServer().getPluginManager().registerEvents(new GungHoListener(), this);
		getServer().getPluginManager().registerEvents(new WandListener(), this);
		getServer().getPluginManager().registerEvents(new MoisturizeListener(), this);
		getServer().getPluginManager().registerEvents(new IrenesLassoListener(), this);
		getServer().getPluginManager().registerEvents(new CurseOfLagListener(), this);
		getServer().getPluginManager().registerEvents(new ChestLootListener(), this);
		getServer().getPluginManager().registerEvents(new MobSpawning(), this);
		getServer().getPluginManager().registerEvents(new VanishListener(), this);
		getServer().getPluginManager().registerEvents(new ChatMessage(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new UpdateEnchantments(), this);
		getServer().getPluginManager().registerEvents(new AdvancementEntityDeath(), this);
		
		if(Bukkit.getPluginManager().isPluginEnabled("Jobs")) {
			jobsReborn = Bukkit.getPluginManager().getPlugin("Jobs");
			ChatUtils.sendInfo("Jobs Reborn compatibility enabled!");
		}
		
		if(Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
			String version = Bukkit.getPluginManager().getPlugin("mcMMO").getDescription().getVersion();
			ChatUtils.sendToConsole(Level.INFO, "mcMMO Version: " + version);
			if(version.substring(0, version.indexOf(".")).equals("2")) {
				ChatUtils.sendToConsole(Level.INFO, "Using the Overhaul Version!");
				ChatUtils.sendToConsole(Level.INFO, "Checking for compatibility plugin...");
				try {
					if(Bukkit.getPluginManager().isPluginEnabled(EsWrapper.getPlugin())) {
						ChatUtils.sendToConsole(Level.INFO, "Found compatibility plugin!");
						ChatUtils.sendToConsole(EsWrapper.getLevel(), EsWrapper.getMessage());
						mcmmoType = "Overhaul";
					} else {
						ChatUtils.sendToConsole(Level.WARNING, "Compatibility plugin not found! Turning off compatibility.");
						mcmmoType = "Disabled";
					}
				} catch(NoClassDefFoundError ex) {
					ChatUtils.sendToConsole(Level.WARNING, "Compatibility plugin not found! Turning off compatibility.");
					mcmmoType = "Disabled";
				}
			} else {
				ChatUtils.sendToConsole(Level.INFO, "Using the Classic Version! Compatibility should be intact.");
				mcmmoType = "Classic";
			}
		} else {
			mcmmoType = "Disabled";
		}
		
		switch(mcmmoType) {
		case "Overhaul":
		case "Classic":
			getServer().getPluginManager().registerEvents(new McMMOFishingNMS(), this);
			break;
		case "Disabled":
			getServer().getPluginManager().registerEvents(new EnchantsFishingListener(), this);
			break;
		}
		if(McMMO.getAbilities() != null) {
			getServer().getPluginManager().registerEvents(McMMO.getAbilities(), this);
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new MagmaWalkerListener(), 20l, 20l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new FrequentFlyerListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
						new DrownedListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new UnrestListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new NoRestListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new VoidWalkerListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new IcarusListener(), 20l, 20l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new MagicGuardListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new GungHoListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new LifeListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new AdvancementThread(), 1l, 1l);

		getCommand("Enchant").setExecutor(new Enchant());
		getCommand("Info").setExecutor(new EnchantInfo());
		getCommand("RemoveEnchant").setExecutor(new RemoveEnchant());
		getCommand("EnchantUnsafe").setExecutor(new UnsafeEnchant());
		getCommand("ESReload").setExecutor(new Reload());
		getCommand("ESConfig").setExecutor(new ConfigEdit());
		getCommand("ESReset").setExecutor(new Reset());
		getCommand("Enchant").setTabCompleter(new PlayerChatTabComplete());
		getCommand("Info").setTabCompleter(new PlayerChatTabComplete());
		getCommand("RemoveEnchant").setTabCompleter(new PlayerChatTabComplete());
		getCommand("EnchantUnsafe").setTabCompleter(new PlayerChatTabComplete());
				
		check = new VersionCheck(pluginVersion, "https://raw.githubusercontent.com/crashtheparty/EnchantmentSolution/master/VersionHistory", 
				"https://www.spigotmc.org/resources/enchantment-solution.59556/", "https://github.com/crashtheparty/EnchantmentSolution", 
				getConfigFiles().getDefaultConfig().getBoolean("get_latest_version"));
		getServer().getPluginManager().registerEvents(check, this);
		checkVersion();
		initialization = false;
		
		advancementUtils = new AdvancementUtils();
		
	}

	public void onDisable() {
		if(bukkitVersion.isVersionAllowed() && !disable) {
			try {
				resetInventories();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				SaveUtils.setAbilityData();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void resetInventories() {
		for(int i = inventories.size() - 1; i >= 0; i--) {
			InventoryData inv = inventories.get(i);
			inv.close(true);
		}
	}
	
	public InventoryData getInventory(Player player) {
		for(InventoryData inv : inventories) {
			if(inv.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return inv;
			}
		}
		
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
	
	private void checkVersion(){
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, check, 20l, 20 * 60 * 60 * 4l);
    }

	public SQLite getDb() {
		return db;
	}

	public String getMcMMOType() {
		return mcmmoType;
	}

	public BukkitVersion getBukkitVersion() {
		return bukkitVersion;
	}

	public PluginVersion getPluginVersion() {
		return pluginVersion;
	}

	public boolean isJobsEnabled() {
		return jobsReborn != null;
	}

	public ConfigFiles getConfigFiles() {
		return files;
	}
	
	public static EnchantmentSolution getPlugin() {
		return PLUGIN;
	}

	public boolean isInitializing() {
		return initialization;
	}

}
