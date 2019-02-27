package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enchantments.mcmmo.Fishing;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.config.YamlInfo;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ConfigFiles {

	private File walkerFile, mainFile, fishingFile, enchantmentFile, enchantmentAdvancedFile;
	private File dataFolder;
	private YamlConfig walkerConfig;
	private YamlConfigBackup config, fishing, enchantment, enchantmentAdvanced;
	private LanguageFiles languageFiles;
	
	public ConfigFiles(EnchantmentSolution plugin) {
		dataFolder = plugin.getDataFolder();
	}
	
	public YamlConfigBackup getDefaultConfig() {
		return config;
	}
	
	public YamlConfigBackup getFishingConfig() {
		return fishing;
	}
	
	public YamlConfig getWalkerConfig() {
		return walkerConfig;
	}
	
	public YamlConfigBackup getLanguageFile() {
		return languageFiles.getLanguageConfig();
	}
	
	public YamlConfigBackup getEnchantmentConfig() {
		return enchantment;
	}
	
	public YamlConfigBackup getEnchantmentAdvancedConfig() {
		return enchantmentAdvanced;
	}
	
	public void createConfigFiles() {
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			File extras = new File(dataFolder + "/extras/");
			if (!extras.exists()) {
				extras.mkdirs();
			}
			walkerFile = new File(dataFolder + "/extras/walker-enchantments.yml");
			if (!walkerFile.exists()) {
				walkerFile.createNewFile();
			}
			YamlConfiguration.loadConfiguration(walkerFile);
			walkerConfig();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			mainFile = new File(dataFolder + "/config.yml");
			YamlConfiguration.loadConfiguration(mainFile);
			fishingFile = new File(dataFolder + "/extras/fishing.yml");
			YamlConfiguration.loadConfiguration(fishingFile);
			enchantmentFile = new File(dataFolder + "/enchantments.yml");
			YamlConfiguration.loadConfiguration(enchantmentFile);
			enchantmentAdvancedFile = new File(dataFolder + "/enchantments_advanced.yml");
			YamlConfiguration.loadConfiguration(enchantmentAdvancedFile);
			defaultFile();
			mcMMOFishing();
			enchantmentFile();
			enchantmentAdvancedFile();
			if(config.getInt("level_divisor") <= 0) {
				config.set("level_divisor", 4);
			}
			if(config.getBoolean("use_custom_names")) {
				config.set("use_advanced_file", true);
				config.removeKey("use_custom_names");
			}
			config.saveConfig();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		loadLangFile(dataFolder);
		save();
	}
	
	public void revert() {
		config.revert();
		fishing.revert();
		getLanguageFile().revert();
		enchantment.revert();
		enchantmentAdvanced.revert();
	}
	
	public void revert(YamlConfigBackup config, int backup) {
		config.revert();
		List<YamlInfo> info = EnchantmentSolution.getDb().getBackup(config, backup);
		for(YamlInfo i : info) {
			if(i.getValue() != null) {
				config.set(i.getPath(), i.getValue());
			}
		}

		save();
	}
	
	public void save() {
		getDefaultConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getFishingConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getLanguageFile().setComments(getDefaultConfig().getBoolean("use_comments"));
		getEnchantmentConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		getEnchantmentAdvancedConfig().setComments(getDefaultConfig().getBoolean("use_comments"));
		
		getDefaultConfig().saveConfig();
		getFishingConfig().saveConfig();
		getEnchantmentConfig().saveConfig();
		getEnchantmentAdvancedConfig().saveConfig();
		loadLangFile(dataFolder);
		
		DefaultEnchantments.setEnchantments();
		PlayerLevels.resetPlayerLevels();
		updateEnchantments();
		
		EnchantmentSolution.getDb().updateConfig(getDefaultConfig());
		EnchantmentSolution.getDb().updateConfig(getFishingConfig());
		EnchantmentSolution.getDb().updateConfig(getLanguageFile());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentConfig());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentAdvancedConfig());
	}
	
	public void reload() {
		try {
			defaultFile();
			mcMMOFishing();
			enchantmentFile();
			enchantmentAdvancedFile();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		loadLangFile(dataFolder);

		save();
	}
	
	private void loadLangFile(File dataFolder) {
		String langFile = config.getString("language_file");
		if(languageFiles == null) {
			languageFiles = new LanguageFiles(new File(dataFolder + "/" + langFile), getLanguage());
		} else {
			languageFiles.setLanguage(new File(dataFolder + "/" + langFile), getLanguage());
		}
	}
	
	private void defaultFile() {
		ChatUtils.sendInfo("Loading default config...");
		
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		config = new YamlConfigBackup(mainFile, header);
		
		config.getFromConfig();
		
		config.addDefault("starter", (ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "Enchantment Solution" + ChatColor.DARK_GRAY + "]").replace(ChatColor.COLOR_CHAR, '&'), new String[] {"What to display in front of messages"});
		config.addDefault("language_file", "language.yml", new String[] {"The yml language file"});
		config.addDefault("language", Language.US.getLocale(), new String[] {"The default language of the language file"});
		config.addDefault("reset_language", false, new String[] {"Reload the entire language file"});
		config.addDefault("max_enchantments", 0, new String[] {"Max enchantments on each item. 0 allows infinite"});
		config.addDefault("lapis_in_table", true, new String[] {"Lapis must be placed in the enchantment table before items can be enchanted."});
		config.addDefault("level_divisor", 4, new String[] {"Greater numbers allow more anvil uses"});
		config.addDefault("level_50_enchants", true, new String[] {"Allow enchantments up to level 50", "- To make this easier, you can try the XpBank plugin: https://www.spigotmc.org/resources/xpbank.59580/"});
		config.addDefault("max_repair_level", 60, new String[] {"The highest repair level that will be allowed in the anvil."});
		config.addMinMax("max_repair_level", 40, 1000000);
		config.addDefault("disable_enchant_method", "visible", new String[] {"How disabling an enchantment in enchantments.yml or enchantments_advanced.yml will work.", 
				"Options:", "vanish - removes enchantment from items", "visible - keeps enchantment on item, but custom effects will not work and anvil will remove enchant", 
				"repairable - same as above but anvil will not remove enchant"});
		config.addEnum("disable_enchant_method", Arrays.asList("vanish", "visible", "repairable"));
		config.addDefault("use_advanced_file", false, new String[] {"Use enchantments_advanced.yml as the enchantment config."});
		config.addDefault("default_anvil_use", false, new String[] {"Allow default use of anvil GUI via option at bottom right of custom GUI.", 
				"Using this feature MAY REMOVE CUSTOM ENCHANTMENTS FROM ITEMS on accident. Should only be true if anvil is used for custom recipes."});
		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() < 4) {
			// CONFIG.addDefault("use_grindstone", false, new String[] {"Use the grindstone from within the anvil in version < 1.14"});
		}
		config.addDefault("update_legacy_enchantments", false, new String[] {"Update any enchantments generated in EnchantmentSolutionLegacy"});
		config.addDefault("chest_loot", true, new String[] {"Allow custom and/or high level enchants to spawn in chests"});
		config.addDefault("mob_loot", true, new String[] {"Allow custom and/or high level enchantments to spawn on mobs"});
		config.addDefault("fishing_loot", true, new String[] {"Allow custom and/or high level enchantments to appear while fishing"});
		config.addDefault("loots.mobs.bookshelves", 0, new String[] {"Modify types of enchantments generated by setting the minimum amount of bookshelves"});
		config.addDefault("loots.mobs.levels", 0, new String[] {"Modify types of enchantments generated by setting the minimum lapis level"});
		config.addDefault("loots.mobs.treasure", false, new String[] {"Whether the enchantments generated from this format should contain treasure enchantments"});
		config.addDefault("loots.fishing.bookshelves", 0);
		config.addDefault("loots.fishing.levels", 0);
		config.addDefault("loots.fishing.treasure", true);
		config.addDefault("loots.end_city_treasure.bookshelves", 15);
		config.addDefault("loots.end_city_treasure.levels", 3);
		config.addDefault("loots.end_city_treasure.treasure", true);
		config.addDefault("loots.dungeon.bookshelves", 0);
		config.addDefault("loots.dungeon.levels", 0);
		config.addDefault("loots.dungeon.treasure", true);
		config.addDefault("loots.shipwreck_supply.bookshelves", 0);
		config.addDefault("loots.shipwreck_supply.levels", 0);
		config.addDefault("loots.shipwreck_supply.treasure", true);
		config.addDefault("loots.woodland_mansion.bookshelves", 10);
		config.addDefault("loots.woodland_mansion.levels", 1);
		config.addDefault("loots.woodland_mansion.treasure", true);
		config.addDefault("loots.underwater_ruin_big.bookshelves", 0);
		config.addDefault("loots.underwater_ruin_big.levels", 0);
		config.addDefault("loots.underwater_ruin_big.treasure", true);
		config.addDefault("loots.underwater_ruin_small.bookshelves", 0);
		config.addDefault("loots.underwater_ruin_small.levels", 0);
		config.addDefault("loots.underwater_ruin_small.treasure", true);
		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() > 3) {
			config.addDefault("loots.pillager_outpost.bookshelves", 10);
			config.addDefault("loots.pillager_outpost.levels", 1);
			config.addDefault("loots.pillager_outpost.treasure", true);
		}
		config.addDefault("use_comments", true, new String[] {"Show helpful comments in the config files"});
		config.addDefault("get_latest_version", true, new String[] {"Check github for updates to the plugin"});
		
		config.saveConfig();

		ChatUtils.sendInfo("Default config initialized!");
	}
	
	private void enchantmentFile() {
		ChatUtils.sendInfo("Loading enchantment config...");
		
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		enchantment = new YamlConfigBackup(enchantmentFile, header);
		
		enchantment.getFromConfig();
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				enchantment.addDefault(plugin.getName() + "." + enchant.getName() + ".enabled", true);
				enchantment.addDefault(plugin.getName() + "." + enchant.getName() + ".treasure", enchant.isTreasure());
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				enchantment.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				enchantment.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
			}
		}
		enchantment.saveConfig();

		ChatUtils.sendInfo("Enchantment config initialized!");
	}
	
	private void enchantmentAdvancedFile() {
		ChatUtils.sendInfo("Loading advanced enchantment config...");
		
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		enchantmentAdvanced = new YamlConfigBackup(enchantmentAdvancedFile, header);
		
		enchantmentAdvanced.getFromConfig();
		
		enchantmentAdvanced.addDefault("use_starting_level", true, new String[] {"Enchantments will not be available unless the enchanting level is the set value or above"});
		enchantmentAdvanced.addDefault("use_lapis_modifier", true, new String[] {"Enchanting with higher amounts of lapis give higher enchantability"});
		enchantmentAdvanced.addDefault("lapis_modifiers.constant", -1, new String[] {"Extra enchantability: (lapis + constant) * modifier"});
		enchantmentAdvanced.addDefault("lapis_modifiers.modifier", 2);
		enchantmentAdvanced.addDefault("multi_enchant_divisor", 75.0D, new String[] {"Chance of multiple enchantments on one item. Lower value = more enchantments."});
		enchantmentAdvanced.addDefault("use_permissions", false, new String[] {"Use the permission system per player for all enchantments.", 
				"Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"", "enchant_name: Enchantment name as used below", 
				"type: either table (for enchanting items) or anvil (for combining items)", "int: the enchantment level",
				"Override permission: enchantmentsolution.permissions.ignore"});
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enabled", true);
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".treasure", enchant.isTreasure());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
						Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".conflicting_enchantments", enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".conflicting_enchantments", DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".disabled_items", enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".disabled_items", ItemUtils.getRepairMaterialsStrings());
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
						Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".conflicting_enchantments", enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".conflicting_enchantments", DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".disabled_items", enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".disabled_items", ItemUtils.getRepairMaterialsStrings());
			} else {
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enabled", true);
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
						Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".conflicting_enchantments", enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".conflicting_enchantments", DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".disabled_items", enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".disabled_items", ItemUtils.getRepairMaterialsStrings());
			}
		}
		enchantmentAdvanced.saveConfig();

		ChatUtils.sendInfo("Advanced enchantment config initialized!");
	}
	
	public void updateExternalEnchantments(JavaPlugin plugin) {
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				if(plugin.equals(((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin())) {
					enchantment.addDefault(plugin.getName() + "." + enchant.getName() + ".enabled", true);
					enchantment.addDefault(plugin.getName() + "." + enchant.getName() + ".treasure", enchant.isTreasure());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enabled", true);
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".treasure", enchant.isTreasure());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".weight", enchant.getDefaultWeightName());
					enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), 
							Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_constant", enchant.getDefaultFiftyConstant());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_modifier", enchant.getDefaultFiftyModifier());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_max_constant", enchant.getDefaultFiftyMaxConstant());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_start_level", enchant.getDefaultFiftyStartLevel());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".enchantability_max_level", enchant.getDefaultFiftyMaxLevel());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".conflicting_enchantments", enchant.conflictingDefaultList());
					enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".conflicting_enchantments", DefaultEnchantments.getEnchantmentNames());
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".disabled_items", enchant.getDisabledItemsStrings());
					enchantmentAdvanced.addEnum(plugin.getName() + "." + enchant.getName() + ".disabled_items", ItemUtils.getRepairMaterialsStrings());
				}
			}
		}
		
		enchantment.saveConfig();
		enchantmentAdvanced.saveConfig();
	}
	
	public void updateEnchantments() {
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				String displayName = enchantmentAdvanced.getString(plugin.getName() + "." + enchant.getName() + ".display_name");
				if(displayName != null) {
					getLanguageFile().set("enchantment.descriptions.custom_enchantments." + enchant.getName(), displayName);
					enchant.setDisplayName(displayName);
					enchantmentAdvanced.removeKey(plugin.getName() + "." + enchant.getName() + ".display_name");
				}
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault(plugin.getName() + "." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault("enchantment.display_names." + plugin.getName() + "." + enchant.getName(), enchant, "display_name");
				languageFiles.addDefault("enchantment.descriptions." + plugin.getName() + "." + enchant.getName(), enchant, "description");
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				String displayName = enchantmentAdvanced.getString("custom_enchantments." + enchant.getName() + ".display_name");
				if(displayName != null) {
					getLanguageFile().set("enchantment.descriptions.custom_enchantments." + enchant.getName(), displayName);
					enchant.setDisplayName(displayName);
					enchantmentAdvanced.removeKey("custom_enchantments." + enchant.getName() + ".display_name");
				}
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault("enchantment.display_names.custom_enchantments." + enchant.getName(), enchant, "display_name");
				languageFiles.addDefault("enchantment.descriptions.custom_enchantments." + enchant.getName(), enchant, "description");
			} else {
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault("enchantment.descriptions.default_enchantments." + enchant.getName(), enchant, "description");
			}
		}
		enchantment.saveConfig();
		enchantmentAdvanced.saveConfig();
		loadLangFile(dataFolder);
		
		EnchantmentSolution.getDb().updateConfig(getEnchantmentConfig());
		EnchantmentSolution.getDb().updateConfig(getEnchantmentAdvancedConfig());
	}
	
	private void walkerConfig() {
		ChatUtils.sendInfo("Loading walker enchantment file...");
		walkerConfig = new YamlConfig(walkerFile, new String[0]);
		
		walkerConfig.getFromConfig();
		
		walkerConfig.saveConfig();

		ChatUtils.sendInfo("Walker enchantment file initialized!");
	}
	
	private void mcMMOFishing() {
		ChatUtils.sendInfo("Loading fishing config...");
		
		String[] header = { "Enchantment Solution",
				"Plugin by", "crashtheparty"};
		fishing = new YamlConfigBackup(fishingFile, header);
		
		fishing.getFromConfig();
		
		fishing.addDefault("Enchantments_Rarity_30.COMMON.enchants", Fishing.enchantmentDefaults("COMMON", false));
		fishing.addDefault("Enchantments_Rarity_50.COMMON.enchants", Fishing.enchantmentDefaults("COMMON", true));
		fishing.addDefault("Enchantments_Rarity_30.COMMON.multiple_enchants_chance", .10);
		fishing.addDefault("Enchantments_Rarity_50.COMMON.multiple_enchants_chance", .10);
		fishing.addDefault("Enchantments_Rarity_30.UNCOMMON.enchants", Fishing.enchantmentDefaults("UNCOMMON", false));
		fishing.addDefault("Enchantments_Rarity_50.UNCOMMON.enchants", Fishing.enchantmentDefaults("UNCOMMON", true));
		fishing.addDefault("Enchantments_Rarity_30.UNCOMMON.multiple_enchants_chance", .20);
		fishing.addDefault("Enchantments_Rarity_50.UNCOMMON.multiple_enchants_chance", .18);
		fishing.addDefault("Enchantments_Rarity_30.RARE.enchants", Fishing.enchantmentDefaults("RARE", false));
		fishing.addDefault("Enchantments_Rarity_50.RARE.enchants", Fishing.enchantmentDefaults("RARE", true));
		fishing.addDefault("Enchantments_Rarity_30.RARE.multiple_enchants_chance", .33);
		fishing.addDefault("Enchantments_Rarity_50.RARE.multiple_enchants_chance", .28);
		fishing.addDefault("Enchantments_Rarity_30.EPIC.enchants", Fishing.enchantmentDefaults("EPIC", false));
		fishing.addDefault("Enchantments_Rarity_50.EPIC.enchants", Fishing.enchantmentDefaults("EPIC", true));
		fishing.addDefault("Enchantments_Rarity_30.EPIC.multiple_enchants_chance", .50);
		fishing.addDefault("Enchantments_Rarity_50.EPIC.multiple_enchants_chance", .42);
		fishing.addDefault("Enchantments_Rarity_30.LEGENDARY.enchants", Fishing.enchantmentDefaults("LEGENDARY", false));
		fishing.addDefault("Enchantments_Rarity_50.LEGENDARY.enchants", Fishing.enchantmentDefaults("LEGENDARY", true));
		fishing.addDefault("Enchantments_Rarity_30.LEGENDARY.multiple_enchants_chance", .75);
		fishing.addDefault("Enchantments_Rarity_50.LEGENDARY.multiple_enchants_chance", .60);
		fishing.addDefault("Enchantments_Rarity_50.ANCIENT.enchants", Fishing.enchantmentDefaults("ANCIENT", true));
		fishing.addDefault("Enchantments_Rarity_50.ANCIENT.multiple_enchants_chance", .85);
		
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_1.COMMON", 5.00);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_1.UNCOMMON", 1.00);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_1.RARE", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_1.EPIC", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_1.LEGENDARY", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_2.COMMON", 7.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_2.UNCOMMON", 1.00);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_2.RARE", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_2.EPIC", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_2.LEGENDARY", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_3.COMMON", 7.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_3.UNCOMMON", 2.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_3.RARE", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_3.EPIC", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_3.LEGENDARY", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_4.COMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_4.UNCOMMON", 2.75);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_4.RARE", 0.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_4.EPIC", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_4.LEGENDARY", 0.05);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_5.COMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_5.UNCOMMON", 4.00);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_5.RARE", 0.75);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_5.EPIC", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_5.LEGENDARY", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_6.COMMON", 9.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_6.UNCOMMON", 5.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_6.RARE", 1.75);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_6.EPIC", 0.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_6.LEGENDARY", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_7.COMMON", 8.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_7.UNCOMMON", 7.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_7.RARE", 2.75);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_7.EPIC", 0.75);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_7.LEGENDARY", 0.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_8.COMMON", 7.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_8.UNCOMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_8.RARE", 5.25);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_8.EPIC", 1.50);
		fishing.addDefault("Enchantment_Drop_Rates_30.Tier_8.LEGENDARY", 0.75);
		
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.COMMON", 5.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.UNCOMMON", 1.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.RARE", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.EPIC", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.LEGENDARY", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_1.ANCIENT", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.COMMON", 8.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.UNCOMMON", 1.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.RARE", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.EPIC", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.LEGENDARY", 0.02);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_2.ANCIENT", 0.01);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.COMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.UNCOMMON", 2.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.RARE", 0.75);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.EPIC", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.LEGENDARY", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_3.ANCIENT", 0.05);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.COMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.UNCOMMON", 3.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.RARE", 1.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.EPIC", 0.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.LEGENDARY", 0.15);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_4.ANCIENT", 0.05);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.COMMON", 9.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.UNCOMMON", 5.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.RARE", 2.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.EPIC", 0.75);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.LEGENDARY", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_5.ANCIENT", 0.10);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.COMMON", 6.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.UNCOMMON", 8.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.RARE", 3.75);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.EPIC", 1.75);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.LEGENDARY", 0.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_6.ANCIENT", 0.15);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.COMMON", 5.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.UNCOMMON", 10.0);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.RARE", 4.75);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.EPIC", 2.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.LEGENDARY", 1.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_7.ANCIENT", 0.25);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.COMMON", 4.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.UNCOMMON", 8.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.RARE", 8.00);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.EPIC", 3.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.LEGENDARY", 1.50);
		fishing.addDefault("Enchantment_Drop_Rates_50.Tier_8.ANCIENT", 0.50);
		
		fishing.saveConfig();
		
		ChatUtils.sendInfo("Fishing config initialized!");
	}

	public boolean useStartLevel() {
		if(config.getBoolean("use_advanced_file")){
			return enchantmentAdvanced.getBoolean("use_starting_level");
		}
		return config.getBoolean("level_50_enchants");
	}
	
	public boolean useLevel50() {
		if(config.getBoolean("use_advanced_file")) {
			return true;
		}
		return config.getBoolean("level_50_enchants");
	}

	public boolean useThirtyEnchantability() {
		if(config.getBoolean("use_advanced_file")){
			return !enchantmentAdvanced.getBoolean("use_lapis_modifier");
		}
		return !config.getBoolean("level_50_enchants");
	}
	
	public boolean usePermissions() {
		if(config.getBoolean("use_advanced_file")){
			return enchantmentAdvanced.getBoolean("use_permissions");
		}
		return false;
	}
	
	public boolean useDefaultAnvil() {
		return config.getBoolean("default_anvil_use");
	}
	
	public boolean useLapisInTable() {
		return config.getBoolean("lapis_in_table");
	}
	
	public boolean useLegacyGrindstone() {
//		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() < 4) {
//			return config.getBoolean("use_grindstone");
//		}
		return false;
	}
	
	public int getMaxRepairLevel() {
		return config.getInt("max_repair_level");
	}
	
	public boolean updateLegacyEnchantments() {
		return config.getBoolean("update_legacy_enchantments");
	}
	
	public int getLevelFromType(String type) {
		return config.getInt("loots."+type+".levels");
	}
	
	public int getBookshelvesFromType(String type) {
		return config.getInt("loots."+type+".bookshelves");
	}
	
	public boolean includeTreasureFromType(String type) {
		return config.getBoolean("loots."+type+".treasure");
	}
	
	public String getLocalizedName(Material material) {
		return getLanguageFile().getString("vanilla." + ItemType.getUnlocalizedName(material));
	}
	
	public Language getLanguage() {
		return Language.getLanguage(config.getString("language"));
	}
 
}
