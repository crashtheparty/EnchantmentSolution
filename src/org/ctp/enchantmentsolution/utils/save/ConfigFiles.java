package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.config.YamlInfo;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ConfigFiles {

	private File abilityFile, mainFile, fishingFile, enchantmentFile, enchantmentAdvancedFile;
	private File dataFolder;
	private YamlConfig abilityConfig;
	private YamlConfigBackup config, fishing, enchantment, enchantmentAdvanced;
	private LanguageFiles languageFiles;
	private List<String> enchantingTypes = 
			Arrays.asList("vanilla_30", "vanilla_30_custom", "enhanced_30", "enhanced_30_custom", "enhanced_50", "enhanced_50_custom");

	public ConfigFiles(EnchantmentSolution plugin) {
		dataFolder = plugin.getDataFolder();
	}

	public YamlConfigBackup getDefaultConfig() {
		return config;
	}

	public YamlConfigBackup getFishingConfig() {
		return fishing;
	}

	public YamlConfig getAbilityConfig() {
		return abilityConfig;
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
			abilityFile = new File(dataFolder + "/extras/ability-enchantments.yml");
			if (!abilityFile.exists()) {
				abilityFile.createNewFile();
			}
			YamlConfiguration.loadConfiguration(abilityFile);
			abilityConfig();
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
		List<YamlInfo> info = EnchantmentSolution.getPlugin().getDb().getBackup(config, backup);
		for(YamlInfo i: info) {
			if (i.getValue() != null) {
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

		updateEnchantments();
		
		getDefaultConfig().saveConfig();
		getFishingConfig().saveConfig();
		getEnchantmentConfig().saveConfig();
		getEnchantmentAdvancedConfig().saveConfig();
		
		PlayerLevels.resetPlayerLevels();
		DefaultEnchantments.setEnchantments();
		loadLangFile(dataFolder);

		EnchantmentSolution.getPlugin().getDb().updateConfig(getDefaultConfig());
		EnchantmentSolution.getPlugin().getDb().updateConfig(getFishingConfig());
		EnchantmentSolution.getPlugin().getDb().updateConfig(getLanguageFile());
		EnchantmentSolution.getPlugin().getDb().updateConfig(getEnchantmentConfig());
		EnchantmentSolution.getPlugin().getDb().updateConfig(getEnchantmentAdvancedConfig());
		
		if(!EnchantmentSolution.getPlugin().isInitializing()) {
			EnchantmentSolution.getPlugin().setVersionCheck(config.getBoolean("version.get_latest"), config.getBoolean("version.get_experimental"));
			AdvancementUtils.createAdvancements();
		}
	}

	public void reload() {
		try {
			defaultFile();
			mcMMOFishing();
			enchantmentFile();
			enchantmentAdvancedFile();
			String setType = config.getString("enchanting_table.enchanting_type");
			if(!enchantingTypes.contains(setType)) {
				config.set("enchanting_table.enchanting_type", "enhanced_50");
			}
			config.saveConfig();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		save();
	}

	private void loadLangFile(File dataFolder) {
		String langFile = config.getString("language_file");
		if (languageFiles == null) {
			languageFiles = new LanguageFiles(this, new File(dataFolder + "/" + langFile), 
					Language.getLanguage(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("language")));
		} else {
			languageFiles.setLanguage(new File(dataFolder + "/" + langFile), 
					Language.getLanguage(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("language")));
		}
	}

	private void defaultFile() {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading default config...");
		}

		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
		config = new YamlConfigBackup(mainFile, header);
		config.getFromConfig();
		
		File file = getTempFile("/resources/config_defaults.yml");

	    YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
	    defaultConfig.getFromConfig();
		for(String str : defaultConfig.getAllEntryKeys()) {
			if(defaultConfig.get(str) != null) {
				if(str.startsWith("config_comments.")) {
					config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
				} else {
					config.addDefault(str, defaultConfig.get(str));
				}
			}
		}
		config.addEnum("language", Language.getValues());
		config.addEnum("disable_enchant_method", Arrays.asList("vanish", "visible", "repairable"));
		config.addEnum("enchanting_table.enchanting_type", enchantingTypes);
		config.addMinMax("anvil.max_repair_level", 40, 1000000);
		if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			config.addDefault("loots.pillager_outpost.bookshelves", 10);
			config.addDefault("loots.pillager_outpost.levels", 1);
			config.addDefault("loots.pillager_outpost.treasure", true);
		}

		for(ESAdvancement advancement : ESAdvancement.values()) {
			if(advancement == ESAdvancement.ENCHANTMENT_SOLUTION) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", false);
			} else if(advancement.getActivatedVersion() < EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", true);
			}
		}

		config.saveConfig();
		file.delete();
		
		migrateDefaultFile();
		
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Default config initialized!");
		}
	}

	private void enchantmentFile() {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading enchantment config...");
		}

		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
		enchantment = new YamlConfigBackup(enchantmentFile, header);

		enchantment.getFromConfig();

		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING,
							"Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
									+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				enchantment.addDefault(plugin.getName().toLowerCase() + "." + enchant.getName() + ".enabled", true);
				enchantment.addDefault(plugin.getName().toLowerCase() + "." + enchant.getName() + ".treasure",
						enchant.isTreasure());
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				enchantment.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				enchantment.addDefault("custom_enchantments." + enchant.getName() + ".treasure", enchant.isTreasure());
			}
		}
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Enchantment config initialized!");
		}
	}

	private void enchantmentAdvancedFile() {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading advanced enchantment config...");
		}

		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
		enchantmentAdvanced = new YamlConfigBackup(enchantmentAdvancedFile, header);

		if (!config.getBoolean("enchanting_table.reset_enchantments_advanced")) {
			enchantmentAdvanced.getFromConfig();
		}

		enchantmentAdvanced.addDefault("use_starting_level", true, new String[] {
				"Enchantments will not be available unless the enchanting level is the set value or above" });
		enchantmentAdvanced.addDefault("use_lapis_modifier", true,
				new String[] { "Enchanting with higher amounts of lapis give higher enchantability" });
		enchantmentAdvanced.addDefault("lapis_modifiers.constant", -1,
				new String[] { "Extra enchantability: (lapis + constant) * modifier" });
		enchantmentAdvanced.addDefault("lapis_modifiers.modifier", 2);
		enchantmentAdvanced.addDefault("multi_enchant_divisor", 75.0D,
				new String[] { "Chance of multiple enchantments on one item. Lower value = more enchantments." });
		enchantmentAdvanced.addDefault("use_permissions", false,
				new String[] { "Use the permission system per player for all enchantments.",
						"Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"",
						"enchant_name: Enchantment name as used below",
						"type: either table (for enchanting items) or anvil (for combining items)",
						"int: the enchantment level", "Override permission: enchantmentsolution.permissions.ignore" });

		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING,
							"Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
									+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				String namespace = plugin.getName().toLowerCase();
				enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".enabled",
						true);
				enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".treasure",
						enchant.isTreasure());
				enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".weight",
						enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum(namespace + "." + enchant.getName() + ".weight",
						Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(),
								Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".enchantability_constant",
						enchant.getDefaultConstant());
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".enchantability_modifier",
						enchant.getDefaultModifier());
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".enchantability_start_level",
						enchant.getDefaultStartLevel());
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".enchantability_max_level",
						enchant.getDefaultMaxLevel());
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".conflicting_enchantments",
						enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum(
						namespace + "." + enchant.getName() + ".conflicting_enchantments",
						DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault(
						namespace + "." + enchant.getName() + ".disabled_items",
						enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum(
						namespace + "." + enchant.getName() + ".disabled_items",
						ItemUtils.getRepairMaterialsStrings());
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enabled", true);
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".treasure",
						enchant.isTreasure());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".weight",
						enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".weight",
						Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(),
								Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_constant",
						enchant.getDefaultConstant());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_modifier",
						enchant.getDefaultModifier());
				enchantmentAdvanced.addDefault(
						"custom_enchantments." + enchant.getName() + ".enchantability_start_level",
						enchant.getDefaultStartLevel());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".enchantability_max_level",
						enchant.getDefaultMaxLevel());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".conflicting_enchantments",
						enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".conflicting_enchantments",
						DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault("custom_enchantments." + enchant.getName() + ".disabled_items",
						enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum("custom_enchantments." + enchant.getName() + ".disabled_items",
						ItemUtils.getRepairMaterialsStrings());
			} else {
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enabled", true);
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".treasure",
						enchant.isTreasure());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".weight",
						enchant.getDefaultWeightName());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".weight",
						Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(),
								Weight.COMMON.getName(), Weight.NULL.getName()));
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_constant",
						enchant.getDefaultConstant());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".enchantability_modifier",
						enchant.getDefaultModifier());
				enchantmentAdvanced.addDefault(
						"default_enchantments." + enchant.getName() + ".enchantability_start_level",
						enchant.getDefaultStartLevel());
				enchantmentAdvanced.addDefault(
						"default_enchantments." + enchant.getName() + ".enchantability_max_level",
						enchant.getDefaultMaxLevel());
				enchantmentAdvanced.addDefault(
						"default_enchantments." + enchant.getName() + ".conflicting_enchantments",
						enchant.conflictingDefaultList());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".conflicting_enchantments",
						DefaultEnchantments.getEnchantmentNames());
				enchantmentAdvanced.addDefault("default_enchantments." + enchant.getName() + ".disabled_items",
						enchant.getDisabledItemsStrings());
				enchantmentAdvanced.addEnum("default_enchantments." + enchant.getName() + ".disabled_items",
						ItemUtils.getRepairMaterialsStrings());
			}
		}
		
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Advanced enchantment config initialized!");
		}
	}

	public void updateExternalEnchantments(JavaPlugin plugin) {
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				if (plugin.equals(((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin())) {
					String namespace = plugin.getName().toLowerCase();
					enchantment.addDefault(namespace + "." + enchant.getName() + ".enabled", true);
					enchantment.addDefault(namespace + "." + enchant.getName() + ".treasure",
							enchant.isTreasure());
					enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".enabled", true);
					enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".treasure",
							enchant.isTreasure());
					enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".weight",
							enchant.getDefaultWeightName());
					enchantmentAdvanced.addEnum(namespace + "." + enchant.getName() + ".weight",
							Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(),
									Weight.COMMON.getName(), Weight.NULL.getName()));
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".enchantability_constant",
							enchant.getDefaultConstant());
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".enchantability_modifier",
							enchant.getDefaultModifier());
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".enchantability_start_level",
							enchant.getDefaultStartLevel());
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".enchantability_max_level",
							enchant.getDefaultMaxLevel());
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".conflicting_enchantments",
							enchant.conflictingDefaultList());
					enchantmentAdvanced.addEnum(
							namespace + "." + enchant.getName() + ".conflicting_enchantments",
							DefaultEnchantments.getEnchantmentNames());
					enchantmentAdvanced.addDefault(namespace + "." + enchant.getName() + ".disabled_items",
							enchant.getDisabledItemsStrings());
					enchantmentAdvanced.addEnum(namespace + "." + enchant.getName() + ".disabled_items",
							ItemUtils.getRepairMaterialsStrings());
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
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING,
							"Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
									+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				String namespace = plugin.getName().toLowerCase();
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault(
							namespace + "." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault(
						"enchantment.display_names." + namespace + "." + enchant.getName(),
						enchant, "display_name");
				languageFiles.addDefault(
						"enchantment.descriptions." + namespace + "." + enchant.getName(), enchant,
						"description");
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				String displayName = enchantmentAdvanced
						.getString("custom_enchantments." + enchant.getName() + ".display_name");
				if (displayName != null) {
					getLanguageFile().set("enchantment.descriptions.custom_enchantments." + enchant.getName(),
							displayName);
					enchant.setDisplayName(displayName);
					enchantmentAdvanced.removeKey("custom_enchantments." + enchant.getName() + ".display_name");
				}
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault(
							"custom_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault(
							"custom_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault("enchantment.display_names.custom_enchantments." + enchant.getName(), enchant,
						"display_name");
				languageFiles.addDefault("enchantment.descriptions.custom_enchantments." + enchant.getName(), enchant,
						"description");
			} else {
				for(int i = 0; i < enchant.getMaxLevel(); i++) {
					enchantmentAdvanced.addDefault(
							"default_enchantments." + enchant.getName() + ".permissions.table.level" + (i + 1), false);
					enchantmentAdvanced.addDefault(
							"default_enchantments." + enchant.getName() + ".permissions.anvil.level" + (i + 1), false);
				}
				languageFiles.addDefault("enchantment.descriptions.default_enchantments." + enchant.getName(), enchant,
						"description");
			}
		}
	}

	private void abilityConfig() {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading ability enchantment file...");
		}
		
		abilityConfig = new YamlConfig(abilityFile, new String[0]);

		abilityConfig.getFromConfig();

		abilityConfig.saveConfig();
		
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Ability enchantment file initialized!");
		}
	}

	private void mcMMOFishing() {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading fishing config...");
		}

		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
		fishing = new YamlConfigBackup(fishingFile, header);

		fishing.getFromConfig();
		
		File file = getTempFile("/resources/fishing_defaults.yml");
	    
	    YamlConfig defaultFishingConfig = new YamlConfig(file, new String[] {});
	    defaultFishingConfig.getFromConfig();
		for(String str : defaultFishingConfig.getAllEntryKeys()) {
			if(defaultFishingConfig.get(str) != null) {
				if(str.startsWith("config_comments.")) {
					fishing.addComments(str, defaultFishingConfig.getStringList(str).toArray(new String[] {}));
				} else {
					fishing.addDefault(str, defaultFishingConfig.get(str));
				}
			}
		}

		fishing.saveConfig();

		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Fishing config initialized!");
		}
		file.delete();
	}
	
	public void generateDebug() {
		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
		YamlConfig backup = new YamlConfig(new File(dataFolder + "/debug.yml"), header);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z Z");
		backup.set("time", format.format(new Date()));
		backup.set("version.bukkit", EnchantmentSolution.getPlugin().getBukkitVersion().getVersion());
		backup.set("version.bukkit_num", EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber());
		backup.set("version.plugin", EnchantmentSolution.getPlugin().getPluginVersion().getCurrent());
		backup.set("plugins.jobs_reborn", EnchantmentSolution.getPlugin().isJobsEnabled());
		backup.set("plugins.mcmmo", EnchantmentSolution.getPlugin().getMcMMOType());
		backup.set("plugins.mcmmo_version", EnchantmentSolution.getPlugin().getMcMMOVersion());
		
		Iterator<Entry<PlayerLevels, List<Integer>>> iterator = PlayerLevels.PLAYER_LEVELS.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<PlayerLevels, List<Integer>> entry = iterator.next();
			List<List<EnchantmentLevel>> enchantmentLevels = entry.getKey().getEnchants();
			for(int i = 0; i < enchantmentLevels.size(); i++) {
				List<EnchantmentLevel> enchantments = enchantmentLevels.get(i);
				for(int j = 0; j < enchantments.size(); j++) {
					EnchantmentLevel enchantment = enchantments.get(j);
					backup.set("players.levels." + entry.getKey().getPlayer().getName() + ".item." + entry.getKey().getMaterial().name().toLowerCase()
							+ ".books." + entry.getKey().getBooks() + "." + i + "." + j, enchantment.getEnchant().getName() + " " + enchantment.getLevel());
				}
			}
		}
		
		for(String s : config.getAllEntryKeys()) {
			if(config.contains(s)) {
				backup.set("config." + s, config.get(s));
			}
		}
		
		for(String s : enchantment.getAllEntryKeys()) {
			if(enchantment.contains(s)) {
				backup.set("enchantment." + s, enchantment.get(s));
			}
		}
		
		for(String s : enchantmentAdvanced.getAllEntryKeys()) {
			if(enchantmentAdvanced.contains(s)) {
				backup.set("enchantmentAdvanced." + s, enchantmentAdvanced.get(s));
			}
		}
		
		for(String s : languageFiles.getLanguageConfig().getAllEntryKeys()) {
			if(languageFiles.getLanguageConfig().contains(s)) {
				backup.set("language." + s, languageFiles.getLanguageConfig().get(s));
			}
		}
		
		for(String s : fishing.getAllEntryKeys()) {
			if(fishing.contains(s)) {
				backup.set("fishing." + s, fishing.get(s));
			}
		}
		
		backup.saveConfig();
	}
	
	private void migrateDefaultFile() {
		if (config.getInt("anvil.level_divisor") <= 0) {
			config.set("anvil.level_divisor", 4);
		}
		if(config.getBoolean("level_50_enchants")) {
			if(config.getBoolean("use_advanced_file")) {
				config.set("enchanting_table.enchanting_type", "enhanced_50_custom");
			} else {
				config.set("enchanting_table.enchanting_type", "enhanced_50");
			}
			config.removeKey("level_50_enchants");
			config.removeKey("use_advanced_file");
		} else if (config.getBooleanValue("level_50_enchants") != null){
			if(config.getBoolean("use_advanced_file")) {
				config.set("enchanting_table.enchanting_type", "enhanced_30_custom");
			} else {
				config.set("enchanting_table.enchanting_type", "enhanced_30");
			}
			config.removeKey("level_50_enchants");
			config.removeKey("use_advanced_file");
		}
		if(config.getBooleanValue("lapis_in_table") != null) {
			config.set("enchanting_table.lapis_in_table", config.getBoolean("lapis_in_table"));
			config.removeKey("lapis_in_table");
		}
		if(config.getBooleanValue("use_enchanted_books") != null) {
			config.set("enchanting_table.use_enchanted_books", config.getBoolean("use_enchanted_books"));
			config.removeKey("use_enchanted_books");
		}
		if(config.getBooleanValue("enchantability_decay") != null) {
			config.set("enchanting_table.decay", config.getBoolean("enchantability_decay"));
			config.removeKey("enchantability_decay");
		}
		if(config.getInteger("max_repair_level") != null) {
			config.set("anvil.max_repair_level", config.getInt("max_repair_level"));
			config.removeKey("max_repair_level");
		}
		if(config.getBooleanValue("get_latest_version") != null) {
			config.set("version.get_latest", config.getBoolean("get_latest_version"));
			config.removeKey("get_latest_version");
		}
		String setType = config.getString("enchanting_table.enchanting_type");
		if(!enchantingTypes.contains(setType)) {
			config.set("enchanting_table.enchanting_type", "enhanced_50");
		}
		config.saveConfig();
	}
	
	public File getTempFile(String resource) {
		File file = null;
	    URL res = getClass().getResource(resource);
	    if (res.getProtocol().equals("jar")) {
	    	InputStream input = null;
	    	OutputStream out = null;
	        try {
	            input = getClass().getResourceAsStream(resource);
	            file = File.createTempFile("/tempfile", ".tmp");
	            out = new FileOutputStream(file);
	            int read;
	            byte[] bytes = new byte[1024];

	            while ((read = input.read(bytes)) != -1) {
	                out.write(bytes, 0, read);
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } finally {
	        	try {
		        	input.close();
	        	} catch (IOException ex) {
	        		ex.printStackTrace();
	        	}
	        	try {
		            out.close();
	        	} catch (IOException ex) {
	        		ex.printStackTrace();
	        	}
	        }
	    } else {
	        //this will probably work in your IDE, but not from a JAR
	        file = new File(res.getFile());
	    }

	    if (file != null && !file.exists()) {
	        throw new RuntimeException("Error: File " + file + " not found!");
	    }
	    file.deleteOnExit();
	    return file;
	}
}
