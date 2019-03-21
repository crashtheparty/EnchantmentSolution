package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.commands.ConfigEdit;
import org.ctp.enchantmentsolution.commands.Enchant;
import org.ctp.enchantmentsolution.commands.EnchantInfo;
import org.ctp.enchantmentsolution.commands.Reload;
import org.ctp.enchantmentsolution.commands.RemoveEnchant;
import org.ctp.enchantmentsolution.commands.Reset;
import org.ctp.enchantmentsolution.commands.UnsafeEnchant;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.listeners.*;
import org.ctp.enchantmentsolution.listeners.abilities.*;
import org.ctp.enchantmentsolution.listeners.chestloot.ChestLootListener;
import org.ctp.enchantmentsolution.listeners.fishing.EnchantsFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingNMS;
import org.ctp.enchantmentsolution.listeners.mobs.MobSpawning;
import org.ctp.enchantmentsolution.nms.Version;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;
import org.ctp.enchantmentsolution.utils.save.SaveUtils;
import org.ctp.eswrapper.EsWrapper;

public class EnchantmentSolution extends JavaPlugin {

	public static EnchantmentSolution PLUGIN;
	public static List<InventoryData> INVENTORIES = new ArrayList<InventoryData>();
	public static boolean NEWEST_VERSION = true, DISABLE = false;
	private static SQLite DB;
	private static String MCMMO_TYPE;
	private static Plugin JOBS_REBORN;
	private static ConfigFiles FILES;

	public void onEnable() {
		PLUGIN = this;
		if(!Version.VERSION_ALLOWED) {
			Bukkit.getLogger().log(Level.WARNING, "Version " + Version.VERSION + " is not compatible with this plugin. Please use a version that is compatible.");
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
			return;
		}
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		DB = new SQLite(this);
		DB.load();
		
		DefaultEnchantments.addDefaultEnchantments();
		
		ConfigFiles.createConfigFiles();
		
		if(DISABLE) {
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
			return;
		}

		SaveUtils.getData();
		
		DefaultEnchantments.setEnchantments();

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
		getServer().getPluginManager().registerEvents(new CurseOfLagListener(), this);
		getServer().getPluginManager().registerEvents(new ChestLootListener(), this);
		getServer().getPluginManager().registerEvents(new MobSpawning(), this);
		getServer().getPluginManager().registerEvents(new VanishListener(), this);
		getServer().getPluginManager().registerEvents(new VersionCheck(), this);
		getServer().getPluginManager().registerEvents(new ChatMessage(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		
		if(Bukkit.getPluginManager().isPluginEnabled("Jobs")) {
			JOBS_REBORN = Bukkit.getPluginManager().getPlugin("Jobs");
			ChatUtils.sendToConsole(Level.INFO, "Jobs Reborn compatibility enabled!");
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
						MCMMO_TYPE = "Overhaul";
					} else {
						ChatUtils.sendToConsole(Level.WARNING, "Compatibility plugin not found! Turning off compatibility.");
						MCMMO_TYPE = "Disabled";
					}
				} catch(NoClassDefFoundError ex) {
					ChatUtils.sendToConsole(Level.WARNING, "Compatibility plugin not found! Turning off compatibility.");
					MCMMO_TYPE = "Disabled";
				}
			} else {
				ChatUtils.sendToConsole(Level.INFO, "Using the Classic Version! Compatibility should be intact.");
				MCMMO_TYPE = "Classic";
			}
		} else {
			MCMMO_TYPE = "Disabled";
		}
		
		switch(MCMMO_TYPE) {
		case "Overhaul":
		case "Classic":
			getServer().getPluginManager().registerEvents(new McMMOFishingNMS(), this);
			break;
		case "Disabled":
			getServer().getPluginManager().registerEvents(new EnchantsFishingListener(), this);
			break;
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new MagmaWalkerListener(), 20l, 20l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new FrequentFlyerListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
						new DrownedListener(), 1l, 1l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new LifeListener(), 1l, 1l);

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
		
		ConfigFiles.updateEnchantments();
		
		checkVersion();
	}

	public void onDisable() {
		SaveUtils.setMagmaWalkerData();
		
		resetInventories();
	}
	
	public static void resetInventories() {
		for(int i = INVENTORIES.size() - 1; i >= 0; i--) {
			InventoryData inv = INVENTORIES.get(i);
			inv.close(true);
		}
	}
	
	public static InventoryData getInventory(Player player) {
		for(InventoryData inv : INVENTORIES) {
			if(inv.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return inv;
			}
		}
		
		return null;
	}
	
	public static boolean hasInventory(InventoryData inv) {
		return INVENTORIES.contains(inv);
	}
	
	public static void addInventory(InventoryData inv) {
		INVENTORIES.add(inv);
	}
	
	public static void removeInventory(InventoryData inv) {
		INVENTORIES.remove(inv);
	}
	
	private void checkVersion(){
		Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, new VersionCheck(), 20l, 20 * 60 * 60 * 4l);
    }

	public static SQLite getDb() {
		return DB;
	}

	public static String getMcMMOType() {
		return MCMMO_TYPE;
	}

	public static boolean isJobsEnabled() {
		return JOBS_REBORN != null;
	}

	public static ConfigFiles getConfigFiles() {
		return FILES;
	}
}
