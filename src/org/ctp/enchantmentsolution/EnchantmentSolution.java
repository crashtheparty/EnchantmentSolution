package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.listeners.BlockBreak;
import org.ctp.enchantmentsolution.listeners.ChatMessage;
import org.ctp.enchantmentsolution.listeners.InventoryClick;
import org.ctp.enchantmentsolution.listeners.InventoryClose;
import org.ctp.enchantmentsolution.listeners.PlayerChatTabComplete;
import org.ctp.enchantmentsolution.listeners.PlayerInteract;
import org.ctp.enchantmentsolution.listeners.VanishListener;
import org.ctp.enchantmentsolution.listeners.VersionCheck;
import org.ctp.enchantmentsolution.listeners.abilities.BeheadingListener;
import org.ctp.enchantmentsolution.listeners.abilities.BrineListener;
import org.ctp.enchantmentsolution.listeners.abilities.DrownedListener;
import org.ctp.enchantmentsolution.listeners.abilities.ExpShareListener;
import org.ctp.enchantmentsolution.listeners.abilities.FishingListener;
import org.ctp.enchantmentsolution.listeners.abilities.FlowerGiftListener;
import org.ctp.enchantmentsolution.listeners.abilities.FrequentFlyerListener;
import org.ctp.enchantmentsolution.listeners.abilities.GoldDiggerListener;
import org.ctp.enchantmentsolution.listeners.abilities.HardBounceListener;
import org.ctp.enchantmentsolution.listeners.abilities.IcarusListener;
import org.ctp.enchantmentsolution.listeners.abilities.IronDefenseListener;
import org.ctp.enchantmentsolution.listeners.abilities.KnockUpListener;
import org.ctp.enchantmentsolution.listeners.abilities.LifeListener;
import org.ctp.enchantmentsolution.listeners.abilities.MagicGuardListener;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;
import org.ctp.enchantmentsolution.listeners.abilities.NoRestListener;
import org.ctp.enchantmentsolution.listeners.abilities.PillageListener;
import org.ctp.enchantmentsolution.listeners.abilities.SacrificeListener;
import org.ctp.enchantmentsolution.listeners.abilities.SandVeilListener;
import org.ctp.enchantmentsolution.listeners.abilities.ShockAspectListener;
import org.ctp.enchantmentsolution.listeners.abilities.SmelteryListener;
import org.ctp.enchantmentsolution.listeners.abilities.SniperListener;
import org.ctp.enchantmentsolution.listeners.abilities.SoulboundListener;
import org.ctp.enchantmentsolution.listeners.abilities.SplatterFestListener;
import org.ctp.enchantmentsolution.listeners.abilities.StoneThrowListener;
import org.ctp.enchantmentsolution.listeners.abilities.TankListener;
import org.ctp.enchantmentsolution.listeners.abilities.TelepathyListener;
import org.ctp.enchantmentsolution.listeners.abilities.TransmutationListener;
import org.ctp.enchantmentsolution.listeners.abilities.UnrestListener;
import org.ctp.enchantmentsolution.listeners.abilities.VoidWalkerListener;
import org.ctp.enchantmentsolution.listeners.abilities.WarpListener;
import org.ctp.enchantmentsolution.listeners.abilities.WidthHeightListener;
import org.ctp.enchantmentsolution.listeners.chestloot.ChestLootListener;
import org.ctp.enchantmentsolution.listeners.fishing.EnchantsFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingNMS;
import org.ctp.enchantmentsolution.listeners.legacy.UpdateEnchantments;
import org.ctp.enchantmentsolution.listeners.mobs.MobSpawning;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;
import org.ctp.enchantmentsolution.utils.save.SaveUtils;
import org.ctp.enchantmentsolution.version.BukkitVersion;
import org.ctp.enchantmentsolution.version.PluginVersion;
import org.ctp.eswrapper.EsWrapper;

public class EnchantmentSolution extends JavaPlugin {

	public static EnchantmentSolution PLUGIN;
	public static List<InventoryData> INVENTORIES = new ArrayList<InventoryData>();
	public static HashMap<Material, HashMap<List<EnchantmentLevel>, Integer>> DEBUG = new HashMap<Material, HashMap<List<EnchantmentLevel>, Integer>>();
	public static boolean NEWEST_VERSION = true, DISABLE = false;
	private static SQLite DB;
	private static String MCMMO_TYPE;
	private static BukkitVersion BUKKIT_VERSION;
	private static PluginVersion PLUGIN_VERSION;

	public void onEnable() {
		PLUGIN = this;
		BUKKIT_VERSION = new BukkitVersion();
		PLUGIN_VERSION = new PluginVersion();
		if(!BUKKIT_VERSION.isVersionAllowed()) {
			Bukkit.getLogger().log(Level.WARNING, "Bukkit Version " + BUKKIT_VERSION.getVersion() + " is not compatible with this plugin. Please use a version that is compatible.");
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
		getServer().getPluginManager().registerEvents(new LifeListener(null),
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
		getServer().getPluginManager().registerEvents(new FrequentFlyerListener(), this);
		getServer().getPluginManager().registerEvents(new TankListener(), this);
		getServer().getPluginManager().registerEvents(new BrineListener(), this);
		getServer().getPluginManager().registerEvents(new DrownedListener(), this);
		getServer().getPluginManager().registerEvents(new UnrestListener(), this);
		getServer().getPluginManager().registerEvents(new NoRestListener(), this);
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
		getServer().getPluginManager().registerEvents(new ChestLootListener(), this);
		getServer().getPluginManager().registerEvents(new MobSpawning(), this);
		getServer().getPluginManager().registerEvents(new VanishListener(), this);
		getServer().getPluginManager().registerEvents(new VersionCheck(), this);
		getServer().getPluginManager().registerEvents(new ChatMessage(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new UpdateEnchantments(), this);
		
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
				ChatUtils.sendToConsole(Level.INFO, "Using the Classic BukkitVersion! Compatibility should be intact.");
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
				new FrequentFlyerListener(), 20l, 20l);
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
		SaveUtils.setWalkerData();
		
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

	public static BukkitVersion getBukkitVersion() {
		return BUKKIT_VERSION;
	}

	public static PluginVersion getPluginVersion() {
		return PLUGIN_VERSION;
	}
}
