package org.ctp.enchantmentsolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.commands.Enchant;
import org.ctp.enchantmentsolution.commands.EnchantInfo;
import org.ctp.enchantmentsolution.commands.RemoveEnchant;
import org.ctp.enchantmentsolution.commands.UnsafeEnchant;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.listeners.InventoryClick;
import org.ctp.enchantmentsolution.listeners.InventoryClose;
import org.ctp.enchantmentsolution.listeners.PlayerInteract;
import org.ctp.enchantmentsolution.listeners.abilities.BeheadingListener;
import org.ctp.enchantmentsolution.listeners.abilities.BrineListener;
import org.ctp.enchantmentsolution.listeners.abilities.ExpShareListener;
import org.ctp.enchantmentsolution.listeners.abilities.FishingListener;
import org.ctp.enchantmentsolution.listeners.abilities.FrequentFlyerListener;
import org.ctp.enchantmentsolution.listeners.abilities.KnockUpListener;
import org.ctp.enchantmentsolution.listeners.abilities.LifeListener;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;
import org.ctp.enchantmentsolution.listeners.abilities.SacrificeListener;
import org.ctp.enchantmentsolution.listeners.abilities.ShockAspectListener;
import org.ctp.enchantmentsolution.listeners.abilities.SmelteryListener;
import org.ctp.enchantmentsolution.listeners.abilities.SniperListener;
import org.ctp.enchantmentsolution.listeners.abilities.SoulboundListener;
import org.ctp.enchantmentsolution.listeners.abilities.TankListener;
import org.ctp.enchantmentsolution.listeners.abilities.TelepathyListener;
import org.ctp.enchantmentsolution.listeners.abilities.WarpListener;
import org.ctp.enchantmentsolution.nms.Version;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;
import org.ctp.enchantmentsolution.utils.save.SaveUtils;

public class EnchantmentSolution extends JavaPlugin {

	public static EnchantmentSolution PLUGIN;
	public static List<EnchantmentTable> TABLES = new ArrayList<EnchantmentTable>();
	public static List<Anvil> ANVILS = new ArrayList<Anvil>();
	public static HashMap<Material, HashMap<List<EnchantmentLevel>, Integer>> DEBUG = new HashMap<Material, HashMap<List<EnchantmentLevel>, Integer>>();

	public void onEnable() {
		PLUGIN = this;
		if(!Version.VERSION_ALLOWED) {
			Bukkit.getLogger().log(Level.WARNING, "Version " + Version.VERSION + " is not compatible with this plugin. Please use a version that is compatible.");
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
			return;
		}
		
		ConfigFiles.createConfigFiles();

		DefaultEnchantments.addDefaultEnchantments();

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

		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new MagmaWalkerListener(), 20l, 20l);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN,
				new FrequentFlyerListener(), 20l, 20l);

		getCommand("Enchant").setExecutor(new Enchant());
		getCommand("Info").setExecutor(new EnchantInfo());
		getCommand("RemoveEnchant").setExecutor(new RemoveEnchant());
		getCommand("EnchantUnsafe").setExecutor(new UnsafeEnchant());
	}

	public void onDisable() {
		SaveUtils.setMagmaWalkerData();
	}
}
