package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.config.SimpleConfig;
import org.ctp.enchantmentsolution.utils.config.SimpleConfigManager;

public class ConfigFiles {

	public static YamlConfiguration MAGMA_WALKER_CONFIG;
	public static File MAGMA_WALKER_FILE;
	public static YamlConfiguration MAIN_CONFIG;
	public static File MAIN_FILE;
	public static File DATA_FOLDER = EnchantmentSolution.PLUGIN.getDataFolder();
	private static SimpleConfig CONFIG;
	
	public static SimpleConfig getDefaultConfig() {
		return CONFIG;
	}

	public static void createConfigFiles() {
		File dataFolder = DATA_FOLDER;
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			File abilities = new File(dataFolder + "/abilities/");
			if (!abilities.exists()) {
				abilities.mkdirs();
			}
			MAGMA_WALKER_FILE = new File(dataFolder + "/abilities/magma-walker.yml");
			if (!MAGMA_WALKER_FILE.exists()) {
				MAGMA_WALKER_FILE.createNewFile();
			}
			MAGMA_WALKER_CONFIG = YamlConfiguration.loadConfiguration(MAGMA_WALKER_FILE);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			defaultFile();
			MAIN_FILE = new File(dataFolder + "/config.yml");
			MAIN_CONFIG = YamlConfiguration.loadConfiguration(MAIN_FILE);
			try {
				if(MAIN_CONFIG.getInt("level_divisor") <= 0) {
					MAIN_CONFIG.set("level_divisor", 4);
				}
				MAIN_CONFIG.save(MAIN_FILE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void defaultFile() {
		SimpleConfigManager manager = new SimpleConfigManager(EnchantmentSolution.PLUGIN);
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		CONFIG = manager.getNewConfig("config.yml", header);
		
		CONFIG.addDefault("starter", (ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "Enchantment Solution" + ChatColor.DARK_GRAY + "]").replace(ChatColor.COLOR_CHAR, '&'), new String[] {"What to display in front of messages"});
		CONFIG.addDefault("max_enchantments", 0, new String[] {"Max enchantments on each item. 0 allows infinite"});
		CONFIG.addDefault("level_divisor", 4, new String[] {"Greater numbers allow more anvil uses"});
		CONFIG.addDefault("level_50_enchants", true, new String[] {"Allow enchantments up to level 50"});
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				CONFIG.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				CONFIG.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
			}
		}
		CONFIG.saveConfig();
		
		EnchantmentSolution.PLUGIN.getLogger().info("Config initialized");
	}
}
