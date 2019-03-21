package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.PlayerLevels;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.enchantments.mcmmo.Fishing;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.config.YamlInfo;

public class ConfigFiles {

	private static File MAGMA_WALKER_FILE, MAIN_FILE, FISHING_FILE, LANGUAGE_FILE, ENCHANTMENT_FILE, ENCHANTMENT_ADVANCED_FILE;
	private static File DATA_FOLDER = EnchantmentSolution.PLUGIN.getDataFolder();
	private static YamlConfig MAGMA_WALKER;
	private static YamlConfigBackup CONFIG, FISHING, LANGUAGE, ENCHANTMENT, ENCHANTMENT_ADVANCED;
	
	public static YamlConfigBackup getDefaultConfig() {
		return CONFIG;
	}
	
	public static YamlConfigBackup getFishingConfig() {
		return FISHING;
	}
	
	public static YamlConfig getMagmaWalkerConfig() {
		return MAGMA_WALKER;
	}
	
	public static YamlConfigBackup getLanguageFile() {
		return LANGUAGE;
	}
	
	public static YamlConfigBackup getEnchantmentConfig() {
		return ENCHANTMENT;
	}
	
	public static YamlConfigBackup getEnchantmentAdvancedConfig() {
		return ENCHANTMENT_ADVANCED;
	}
	
	public static void createConfigFiles() {
		File dataFolder = DATA_FOLDER;
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			File extras = new File(dataFolder + "/extras/");
			if (!extras.exists()) {
				extras.mkdirs();
			}
			MAGMA_WALKER_FILE = new File(dataFolder + "/extras/magma-walker.yml");
			if (!MAGMA_WALKER_FILE.exists()) {
				MAGMA_WALKER_FILE.createNewFile();
			}
			YamlConfiguration.loadConfiguration(MAGMA_WALKER_FILE);
			magmaWalker();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			MAIN_FILE = new File(dataFolder + "/config.yml");
			YamlConfiguration.loadConfiguration(MAIN_FILE);
			FISHING_FILE = new File(dataFolder + "/extras/fishing.yml");
			YamlConfiguration.loadConfiguration(FISHING_FILE);
			ENCHANTMENT_FILE = new File(dataFolder + "/enchantments.yml");
			YamlConfiguration.loadConfiguration(ENCHANTMENT_FILE);
			ENCHANTMENT_ADVANCED_FILE = new File(dataFolder + "/enchantments_advanced.yml");
			YamlConfiguration.loadConfiguration(ENCHANTMENT_ADVANCED_FILE);
			defaultFile();
			mcMMOFishing();
			enchantmentFile();
			enchantmentAdvancedFile();
			if(CONFIG.getInt("level_divisor") <= 0) {
				CONFIG.set("level_divisor", 4);
			}
			if(CONFIG.getBoolean("use_custom_names")) {
				CONFIG.set("use_advanced_file", true);
				CONFIG.removeKey("use_custom_names");
			}
			CONFIG.saveConfig();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		loadLangFile(dataFolder, 0);
		save();
	}
	
	public static void revert() {
		ConfigFiles.getDefaultConfig().revert();
		ConfigFiles.getFishingConfig().revert();
		ConfigFiles.getLanguageFile().revert();
		ConfigFiles.getEnchantmentConfig().revert();
		ConfigFiles.getEnchantmentAdvancedConfig().revert();
	}
	
	public static void revert(YamlConfigBackup config, int backup) {
		config.revert();
		List<YamlInfo> info = EnchantmentSolution.getDb().getBackup(config, backup);
		for(YamlInfo i : info) {
			if(i.getValue() != null) {
				config.set(i.getPath(), i.getValue());
			}
		}

		save();
	}
	
	public static void save() {
		getDefaultConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getFishingConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getLanguageFile().setComments(getDefaultConfig().getBoolean("use_comments"));
		getEnchantmentConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getEnchantmentAdvancedConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		
		getDefaultConfig().saveConfig();
		getFishingConfig().saveConfig();
		getLanguageFile().saveConfig();
		getEnchantmentConfig().saveConfig();
		getEnchantmentAdvancedConfig().saveConfig();
		
		DefaultEnchantments.setEnchantments();
		PlayerLevels.resetPlayerLevels();
		updateEnchantments();
		
		EnchantmentSolution.getDb().updateConfig(getDefaultConfig());
		EnchantmentSolution.getDb().updateConfig(getFishingConfig());
		EnchantmentSolution.getDb().updateConfig(getLanguageFile());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentConfig());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentAdvancedConfig());
	}
	
	public static void reload() {
		File dataFolder = DATA_FOLDER;
		try {
			defaultFile();
			mcMMOFishing();
			enchantmentFile();
			enchantmentAdvancedFile();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		loadLangFile(dataFolder, 0);

		save();
	}
	
	private static void loadLangFile(File dataFolder, int tries) {
		if(tries > 5) {
			ChatUtils.sendToConsole(Level.SEVERE, "Failed to load language file. Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(EnchantmentSolution.PLUGIN);
			return;
		}
		try {
			String langFile = CONFIG.getString("language_file");
			LANGUAGE_FILE = new File(dataFolder + "/" + langFile);
			YamlConfiguration.loadConfiguration(LANGUAGE_FILE);
			
			language(langFile);
		} catch (final Exception e) {
			tries++;
			CONFIG.set("language_file", "language.yml");
			CONFIG.saveConfig();
			ChatUtils.sendToConsole(Level.WARNING, "Couldn't load language file; set to language.yml"); 
			e.printStackTrace();
			loadLangFile(dataFolder, tries);
		}
	}
	
	private static void defaultFile() {
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		CONFIG = new YamlConfigBackup(MAIN_FILE, header);
		
		CONFIG.getFromConfig();
		
		CONFIG.addDefault("starter", (ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "Enchantment Solution" + ChatColor.DARK_GRAY + "]").replace(ChatColor.COLOR_CHAR, '&'), new String[] {"What to display in front of messages"});
		CONFIG.addDefault("max_enchantments", 0, new String[] {"Max enchantments on each item. 0 allows infinite"});
		CONFIG.addDefault("lapis_in_table", true, new String[] {"Lapis must be placed in the enchantment table before items can be enchanted."});
		CONFIG.addDefault("level_divisor", 4, new String[] {"Greater numbers allow more anvil uses"});
		CONFIG.addDefault("level_50_enchants", true, new String[] {"Allow enchantments up to level 50", "- To make this easier, you can try the XpBank plugin: https://www.spigotmc.org/resources/xpbank.59580/"});
		CONFIG.addDefault("max_repair_level", 60, new String[] {"The highest repair level that will be allowed in the anvil."});
		CONFIG.addMinMax("max_repair_level", 40, 1000000);
		CONFIG.addDefault("disable_enchant_method", "visible", new String[] {"How disabling an enchantment in enchantments.yml or enchantments_advanced.yml will work.", 
				"Options:", "vanish - removes enchantment from items", "visible - keeps enchantment on item, but custom effects will not work and anvil will remove enchant", 
				"repairable - same as above but anvil will not remove enchant"});
		CONFIG.addEnum("disable_enchant_method", Arrays.asList("vanish", "visible", "repairable"));
		CONFIG.addDefault("use_advanced_file", false, new String[] {"Use enchantments_advanced.yml as the enchantment config."});
		CONFIG.addDefault("default_anvil_use", false, new String[] {"Allow default use of anvil GUI via option at bottom right of custom GUI.", 
				"Using this feature MAY REMOVE CUSTOM ENCHANTMENTS FROM ITEMS on accident. Should only be true if anvil is used for custom recipes."});
		CONFIG.addDefault("chest_loot", true, new String[] {"Allow custom and/or high level enchants to spawn in chests"});
		CONFIG.addDefault("mob_loot", true, new String[] {"Allow custom and/or high level enchantments to spawn on mobs"});
		CONFIG.addDefault("fishing_loot", true, new String[] {"Allow custom and/or high level enchantments to appear while fishing"});
		CONFIG.addDefault("loots.mobs.bookshelves", 0, new String[] {"Modify types of enchantments generated by setting the minimum amount of bookshelves"});
		CONFIG.addDefault("loots.mobs.levels", 0, new String[] {"Modify types of enchantments generated by setting the minimum lapis level"});
		CONFIG.addDefault("loots.mobs.treasure", false, new String[] {"Whether the enchantments generated from this format should contain treasure enchantments"});
		CONFIG.addDefault("loots.fishing.bookshelves", 0);
		CONFIG.addDefault("loots.fishing.levels", 0);
		CONFIG.addDefault("loots.fishing.treasure", true);
		CONFIG.addDefault("loots.end_city_treasure.bookshelves", 15);
		CONFIG.addDefault("loots.end_city_treasure.levels", 3);
		CONFIG.addDefault("loots.end_city_treasure.treasure", true);
		CONFIG.addDefault("loots.simple_dungeon.bookshelves", 0);
		CONFIG.addDefault("loots.simple_dungeon.levels", 0);
		CONFIG.addDefault("loots.simple_dungeon.treasure", true);
		CONFIG.addDefault("loots.shipwreck_supply.bookshelves", 0);
		CONFIG.addDefault("loots.shipwreck_supply.levels", 0);
		CONFIG.addDefault("loots.shipwreck_supply.treasure", true);
		CONFIG.addDefault("loots.woodland_mansion.bookshelves", 10);
		CONFIG.addDefault("loots.woodland_mansion.levels", 1);
		CONFIG.addDefault("loots.woodland_mansion.treasure", true);
		CONFIG.addDefault("loots.underwater_ruin_big.bookshelves", 0);
		CONFIG.addDefault("loots.underwater_ruin_big.levels", 0);
		CONFIG.addDefault("loots.underwater_ruin_big.treasure", true);
		CONFIG.addDefault("loots.underwater_ruin_small.bookshelves", 0);
		CONFIG.addDefault("loots.underwater_ruin_small.levels", 0);
		CONFIG.addDefault("loots.underwater_ruin_small.treasure", true);
		CONFIG.addDefault("language_file", "language.yml", new String[] {"The yml language file"});
		CONFIG.addDefault("use_comments", true, new String[] {"Show helpful comments in the config files"});
		CONFIG.addDefault("get_latest_version", true, new String[] {"Check github for updates to the plugin"});
		
		DateFormat month = new SimpleDateFormat("M");
		DateFormat day = new SimpleDateFormat("dd");
		Date date = new Date();
		try {
			Integer monthInt = Integer.parseInt(month.format(date));
			Integer dayInt = Integer.parseInt(day.format(date));
			if(monthInt == 4 && dayInt == 1) {
				CONFIG.addDefault("april_fools_day", true, new String[] {"Enable the April Fools Day gag"});
			} else {
				CONFIG.removeKey("april_fools_day");
			}
		} catch (Exception ex) {}
		
		CONFIG.saveConfig();

		ChatUtils.sendInfo("Default config initialized!");
	}
	
	private static void enchantmentFile() {
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		ENCHANTMENT = new YamlConfigBackup(ENCHANTMENT_FILE, header);
		
		ENCHANTMENT.getFromConfig();
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				ENCHANTMENT.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				ENCHANTMENT.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
			}
		}
		ENCHANTMENT.saveConfig();

		ChatUtils.sendInfo("Enchantment config initialized!");
	}
	
	private static void enchantmentAdvancedFile() {
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		ENCHANTMENT_ADVANCED = new YamlConfigBackup(ENCHANTMENT_ADVANCED_FILE, header);
		
		ENCHANTMENT_ADVANCED.getFromConfig();
		
		ENCHANTMENT_ADVANCED.addDefault("use_starting_level", true, new String[] {"Enchantments will not be available unless the enchanting level is the set value or above"});
		ENCHANTMENT_ADVANCED.addDefault("use_lapis_modifier", true, new String[] {"Enchanting with higher amounts of lapis give higher enchantability"});
		ENCHANTMENT_ADVANCED.addDefault("lapis_modifiers.constant", -1, new String[] {"Extra enchantability: (lapis + constant) * modifier"});
		ENCHANTMENT_ADVANCED.addDefault("lapis_modifiers.modifier", 2);
		ENCHANTMENT_ADVANCED.addDefault("multi_enchant_divisor", 75.0D, new String[] {"Chance of multiple enchantments on one item. Lower value = more enchantments."});
		ENCHANTMENT_ADVANCED.addDefault("use_permissions", false, new String[] {"Use the permission system per player for all enchantments.", 
				"Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"", "enchant_name: Enchantment name as used below", 
				"type: either table (for enchanting items) or anvil (for combining items)", "int: the enchantment level",
				"Override permission: enchantmentsolution.permissions.ignore"});
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
				ENCHANTMENT_ADVANCED.addEnum("custom_enchantments." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
						Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".display_name", enchant.getDefaultDisplayName());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
				ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
			} else {
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enabled", true);
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
				ENCHANTMENT_ADVANCED.addEnum("default_enchantments." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
						Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
				ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
			}
		}
		ENCHANTMENT_ADVANCED.saveConfig();

		ChatUtils.sendInfo("Advanced enchantment config initialized!");
	}
	
	public static void updateEnchantments() {
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				String displayName = ENCHANTMENT.getString("custom_enchantments." + enchant.getName() + ".display_name");
				if(displayName != null) {
					ENCHANTMENT_ADVANCED.set("custom_enchantments." + enchant.getName() + ".display_name", displayName);
					enchant.setDisplayName(displayName);
					ENCHANTMENT.removeKey("custom_enchantments." + enchant.getName() + ".display_name");
				}
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					ENCHANTMENT_ADVANCED.addDefault("custom_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
			} else {
				String displayName = ENCHANTMENT_ADVANCED.getString("default_enchantments." + enchant.getName() + ".display_name");
				if(displayName != null) {
					ENCHANTMENT_ADVANCED.set("default_enchantments." + enchant.getName() + ".display_name", null);
				}
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					ENCHANTMENT_ADVANCED.addDefault("default_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
			}
		}
		ENCHANTMENT.saveConfig();
		ENCHANTMENT_ADVANCED.saveConfig();
		
		EnchantmentSolution.getDb().updateConfig(getEnchantmentConfig());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentAdvancedConfig());
	}
	
	private static void magmaWalker() {
		ChatUtils.sendInfo("Loading magma walker enchantment file...");
		
		MAGMA_WALKER = new YamlConfig(MAGMA_WALKER_FILE, new String[0]);
		
		MAGMA_WALKER.getFromConfig();
		
		MAGMA_WALKER.saveConfig();

		ChatUtils.sendInfo("Magma Walker file initialized!");
	}
	
	private static void mcMMOFishing() {
		ChatUtils.sendInfo("Loading fishing config...");
		
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		FISHING = new YamlConfigBackup(FISHING_FILE, header);
		
		FISHING.getFromConfig();
		
		FISHING.addDefault("Enchantments_Rarity_30.COMMON.enchants", Fishing.enchantmentDefaults("COMMON", false));
		FISHING.addDefault("Enchantments_Rarity_50.COMMON.enchants", Fishing.enchantmentDefaults("COMMON", true));
		FISHING.addDefault("Enchantments_Rarity_30.COMMON.multiple_enchants_chance", .10);
		FISHING.addDefault("Enchantments_Rarity_50.COMMON.multiple_enchants_chance", .10);
		FISHING.addDefault("Enchantments_Rarity_30.UNCOMMON.enchants", Fishing.enchantmentDefaults("UNCOMMON", false));
		FISHING.addDefault("Enchantments_Rarity_50.UNCOMMON.enchants", Fishing.enchantmentDefaults("UNCOMMON", true));
		FISHING.addDefault("Enchantments_Rarity_30.UNCOMMON.multiple_enchants_chance", .20);
		FISHING.addDefault("Enchantments_Rarity_50.UNCOMMON.multiple_enchants_chance", .18);
		FISHING.addDefault("Enchantments_Rarity_30.RARE.enchants", Fishing.enchantmentDefaults("RARE", false));
		FISHING.addDefault("Enchantments_Rarity_50.RARE.enchants", Fishing.enchantmentDefaults("RARE", true));
		FISHING.addDefault("Enchantments_Rarity_30.RARE.multiple_enchants_chance", .33);
		FISHING.addDefault("Enchantments_Rarity_50.RARE.multiple_enchants_chance", .28);
		FISHING.addDefault("Enchantments_Rarity_30.EPIC.enchants", Fishing.enchantmentDefaults("EPIC", false));
		FISHING.addDefault("Enchantments_Rarity_50.EPIC.enchants", Fishing.enchantmentDefaults("EPIC", true));
		FISHING.addDefault("Enchantments_Rarity_30.EPIC.multiple_enchants_chance", .50);
		FISHING.addDefault("Enchantments_Rarity_50.EPIC.multiple_enchants_chance", .42);
		FISHING.addDefault("Enchantments_Rarity_30.LEGENDARY.enchants", Fishing.enchantmentDefaults("LEGENDARY", false));
		FISHING.addDefault("Enchantments_Rarity_50.LEGENDARY.enchants", Fishing.enchantmentDefaults("LEGENDARY", true));
		FISHING.addDefault("Enchantments_Rarity_30.LEGENDARY.multiple_enchants_chance", .75);
		FISHING.addDefault("Enchantments_Rarity_50.LEGENDARY.multiple_enchants_chance", .60);
		FISHING.addDefault("Enchantments_Rarity_50.ANCIENT.enchants", Fishing.enchantmentDefaults("ANCIENT", true));
		FISHING.addDefault("Enchantments_Rarity_50.ANCIENT.multiple_enchants_chance", .85);
		
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_1.COMMON", 5.00);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_1.UNCOMMON", 1.00);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_1.RARE", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_1.EPIC", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_1.LEGENDARY", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_2.COMMON", 7.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_2.UNCOMMON", 1.00);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_2.RARE", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_2.EPIC", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_2.LEGENDARY", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_3.COMMON", 7.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_3.UNCOMMON", 2.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_3.RARE", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_3.EPIC", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_3.LEGENDARY", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_4.COMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_4.UNCOMMON", 2.75);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_4.RARE", 0.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_4.EPIC", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_4.LEGENDARY", 0.05);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_5.COMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_5.UNCOMMON", 4.00);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_5.RARE", 0.75);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_5.EPIC", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_5.LEGENDARY", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_6.COMMON", 9.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_6.UNCOMMON", 5.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_6.RARE", 1.75);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_6.EPIC", 0.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_6.LEGENDARY", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_7.COMMON", 8.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_7.UNCOMMON", 7.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_7.RARE", 2.75);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_7.EPIC", 0.75);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_7.LEGENDARY", 0.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_8.COMMON", 7.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_8.UNCOMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_8.RARE", 5.25);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_8.EPIC", 1.50);
		FISHING.addDefault("Enchantment_Drop_Rates_30.Tier_8.LEGENDARY", 0.75);
		
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.COMMON", 5.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.UNCOMMON", 1.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.RARE", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.EPIC", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.LEGENDARY", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_1.ANCIENT", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.COMMON", 8.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.UNCOMMON", 1.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.RARE", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.EPIC", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.LEGENDARY", 0.02);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_2.ANCIENT", 0.01);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.COMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.UNCOMMON", 2.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.RARE", 0.75);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.EPIC", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.LEGENDARY", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_3.ANCIENT", 0.05);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.COMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.UNCOMMON", 3.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.RARE", 1.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.EPIC", 0.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.LEGENDARY", 0.15);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_4.ANCIENT", 0.05);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.COMMON", 9.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.UNCOMMON", 5.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.RARE", 2.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.EPIC", 0.75);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.LEGENDARY", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_5.ANCIENT", 0.10);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.COMMON", 6.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.UNCOMMON", 8.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.RARE", 3.75);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.EPIC", 1.75);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.LEGENDARY", 0.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_6.ANCIENT", 0.15);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.COMMON", 5.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.UNCOMMON", 10.0);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.RARE", 4.75);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.EPIC", 2.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.LEGENDARY", 1.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_7.ANCIENT", 0.25);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.COMMON", 4.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.UNCOMMON", 8.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.RARE", 8.00);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.EPIC", 3.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.LEGENDARY", 1.50);
		FISHING.addDefault("Enchantment_Drop_Rates_50.Tier_8.ANCIENT", 0.50);
		
		FISHING.saveConfig();
		
		ChatUtils.sendInfo("Fishing config initialized!");
	}
	
	private static void language(String fileName) {
		ChatUtils.sendInfo("Loading language file...");
		
		LANGUAGE = new YamlConfigBackup(LANGUAGE_FILE, new String[0]);
		
		LANGUAGE.getFromConfig();
		
		LANGUAGE.addDefault("anvil.name", (ChatColor.BLUE + "Anvil").replace("§", "&"));
		LANGUAGE.addDefault("anvil.mirror", (ChatColor.WHITE + "").replace("§", "&"));
		LANGUAGE.addDefault("anvil.rename", (ChatColor.GREEN + "Rename Items").replace("§", "&"));
		LANGUAGE.addDefault("anvil.repair-cost", (ChatColor.GREEN + "Level Cost: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		LANGUAGE.addDefault("anvil.repair-cost-high", (ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		LANGUAGE.addDefault("anvil.cannot-repair", (ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "Cannot Repair This Item").replace("§", "&"));
		LANGUAGE.addDefault("anvil.combine", (ChatColor.GREEN + "Combine Items").replace("§", "&"));
		LANGUAGE.addDefault("anvil.cannot-combine", (ChatColor.RED + "Can't Combine Items").replace("§", "&"));
		LANGUAGE.addDefault("anvil.cannot-rename", (ChatColor.RED + "Can't Rename Items").replace("§", "&"));
		LANGUAGE.addDefault("anvil.message-cannot-combine", "You may not combine these items!");
		LANGUAGE.addDefault("anvil.legacy-gui", (ChatColor.WHITE + "Open Minecraft Anvil GUI").replace("§", "&"));
		LANGUAGE.addDefault("anvil.legacy-gui-warning", (
				Arrays.asList((ChatColor.RED + "Custom enchantments will not work in this anvil and may be lost.").replace("§", "&"), 
						(ChatColor.RED + "Other custom features will also not work.").replace("§", "&"),
						(ChatColor.RED + "This should only be used for custom recipes.").replace("§", "&"))));
		LANGUAGE.addDefault("anvil.legacy-gui-open", (ChatColor.WHITE + "Minecraft anvil GUI will open on next anvil click.").replace("§", "&"));
		LANGUAGE.addDefault("anvil.legacy-gui-close", (ChatColor.WHITE + "Minecraft anvil GUI closed.").replace("§", "&"));
		
		LANGUAGE.addDefault("table.name", (ChatColor.BLUE + "Enchantment Table").replace("§", "&"));
		LANGUAGE.addDefault("table.black-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		LANGUAGE.addDefault("table.red-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		LANGUAGE.addDefault("table.blue-mirror", (ChatColor.DARK_BLUE + "Add Lapis").replace("§", "&"));
		LANGUAGE.addDefault("table.blue-mirror-lore", Arrays.asList(
				(ChatColor.BLUE + "Select lapis in your inventory to add it to this slot.").replace("§", "&")));
		LANGUAGE.addDefault("table.instructions-title", ("Enchantment Instructions.").replace("§", "&"));
		LANGUAGE.addDefault("table.instructions", Arrays.asList(
				"Click items to put them on the left.",
				"You will see a list of books with the level",
				" and lapis needed to enchant.", "Select a book to enchant.",
				"Select the item again to remove.",
				"You may see up to 4 items at a time."));
		LANGUAGE.addDefault("table.generate-enchants-error", ("There was an error generating enchantments.").replace("§", "&"));
		LANGUAGE.addDefault("table.enchant-level", ("Level %level% Enchant.").replace("§", "&"));
		LANGUAGE.addDefault("table.enchant-level-lore", Arrays.asList(
				"Lvl Req: %levelReq%.", "Lvl Cost: %level%."));
		LANGUAGE.addDefault("table.level-fifty-disabled", ("Level 50 enchantments disabled.").replace("§", "&"));
		LANGUAGE.addDefault("table.level-fifty-lack", ("Requires 15 bookshelves around table.").replace("§", "&"));
		LANGUAGE.addDefault("table.lapis-cost-okay", (ChatColor.GREEN + "Lapis Cost: %cost%").replace("§", "&"));
		LANGUAGE.addDefault("table.lapis-cost-lack", (ChatColor.RED + "Lapis Cost: %cost%").replace("§", "&"));
		LANGUAGE.addDefault("table.level-cost-okay", (ChatColor.GREEN + "Level Req: %levelReq%").replace("§", "&"));
		LANGUAGE.addDefault("table.level-cost-lack", (ChatColor.RED + "Level Req: %levelReq%").replace("§", "&"));
		LANGUAGE.addDefault("table.item-enchant-name", ("%name% Level %level% Enchants").replace("§", "&"));
		LANGUAGE.addDefault("table.enchant-name", ("%enchant%...").replace("§", "&"));
		LANGUAGE.addDefault("table.lack-reqs", ("You do not meet the requirements to enchant this item.").replace("§", "&"));
		LANGUAGE.addDefault("table.lack-enchants", ("This item does not have enchantments generated.").replace("§", "&"));
		
		LANGUAGE.addDefault("commands.no-permission", (ChatColor.RED + "You do not have permission to use this command!").replace("§", "&"));
		LANGUAGE.addDefault("commands.invalid-level", ("%level% is not a valid level. Setting level to 1.").replace("§", "&"));
		LANGUAGE.addDefault("commands.level-too-low", ("Cannot set a negative or 0 level. Setting level to 1.").replace("§", "&"));
		LANGUAGE.addDefault("commands.level-too-high", ("%level% is too high of a level. Setting level to %maxLevel%.").replace("§", "&"));
		LANGUAGE.addDefault("commands.add-enchant", ("Enchantment with name %enchant% with level %level% has been added to the item.").replace("§", "&"));
		LANGUAGE.addDefault("commands.cannot-enchant-item", ("Enchantment does not work with this item.").replace("§", "&"));
		LANGUAGE.addDefault("commands.too-many-enchants", ("This item has too many enchantments already.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-fail", ("You must try to enchant an item.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-not-found", ("Enchantment with name %enchant% not found.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-not-specified", ("You must specify an enchantment.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-removed", ("Enchantment with name %enchant% has been removed from the item.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-remove-from-item", ("You must specify an enchantment.").replace("§", "&"));
		LANGUAGE.addDefault("commands.reload", ("Config files have been reloaded. Please note that the enchantments.yml file requires a server restart to take effect.").replace("§", "&"));
		LANGUAGE.addDefault("commands.enchant-disabled", ("Cannot enchant item with a disabled enchantment.").replace("§", "&"));
		LANGUAGE.addDefault("commands.reset-inventory", ("Closed all custom inventories.").replace("§", "&"));
		
		LANGUAGE.addDefault("items.stole-soulbound", ("You have stolen the player's soulbound items!").replace("§", "&"));
		LANGUAGE.addDefault("items.soulbound-stolen", ("Your soulbound items have been stolen!").replace("§", "&"));
		
		LANGUAGE.saveConfig();
		if(LANGUAGE.getString("commands.reload").equals("Config files have been reloaded. Please note that the enchantments.yml file requires a server restart to take effect.")) {
			LANGUAGE.set("commands.reload", "Config files have been reloaded.");
			LANGUAGE.saveConfig();
		}
		
		ChatUtils.sendInfo("Language file initialized!");
	}

	public static boolean useStartLevel() {
		if(CONFIG.getBoolean("use_advanced_file")){
			return ENCHANTMENT_ADVANCED.getBoolean("use_starting_level");
		}
		return CONFIG.getBoolean("level_50_enchants");
	}
	
	public static boolean useLevel50() {
		if(CONFIG.getBoolean("use_advanced_file")) {
			return true;
		}
		return ConfigFiles.getDefaultConfig().getBoolean("level_50_enchants");
	}

	public static boolean useThirtyEnchantability() {
		if(CONFIG.getBoolean("use_advanced_file")){
			return !ENCHANTMENT_ADVANCED.getBoolean("use_lapis_modifier");
		}
		return !CONFIG.getBoolean("level_50_enchants");
	}
	
	public static boolean usePermissions() {
		if(CONFIG.getBoolean("use_advanced_file")){
			return ENCHANTMENT_ADVANCED.getBoolean("use_permissions");
		}
		return false;
	}
	
	public static boolean useDefaultAnvil() {
		return CONFIG.getBoolean("default_anvil_use");
	}
	
	public static boolean useLapisInTable() {
		return CONFIG.getBoolean("lapis_in_table");
	}
	
	public static int getMaxRepairLevel() {
		return CONFIG.getInt("max_repair_level");
	}
	
	public static int getLevelFromType(String type) {
		return CONFIG.getInt("loots."+type+".levels");
	}
	
	public static int getBookshelvesFromType(String type) {
		return CONFIG.getInt("loots."+type+".bookshelves");
	}
	
	public static boolean includeTreasureFromType(String type) {
		return CONFIG.getBoolean("loots."+type+".treasure");
	}
	
	public static boolean getAprilFools() {
		DateFormat month = new SimpleDateFormat("M");
		DateFormat day = new SimpleDateFormat("dd");
		Date date = new Date();
		
		try {
			Integer monthInt = Integer.parseInt(month.format(date));
			Integer dayInt = Integer.parseInt(day.format(date));
			if(monthInt == 4 && dayInt == 1) {
				if(CONFIG.getBooleanValue("april_fools_day") == null) {
					CONFIG.addDefault("april_fools_day", true);
					CONFIG.saveConfig();
				}
				return CONFIG.getBoolean("april_fools_day");
			} else {
				return false;
			}
		} catch (Exception ex) {}
		return false;
	}
 
}
