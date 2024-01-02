package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;

public class EnchantingTableConfiguration extends Configuration {

	public EnchantingTableConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/enchanting_table.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();
		YamlConfig main = Configurations.getConfigurations().getConfig().getConfig();
		YamlConfig enchantments = Configurations.getConfigurations().getConfig().getConfig();

		if (main.contains("enchanting_table.custom_gui")) {
			config.set("custom_gui", main.get("enchanting_table.custom_gui"));
			main.removeKey("enchanting_table.custom_gui");
		}

		if (main.contains("enchanting_table.level_fifty")) {
			config.set("level_fifty", main.get("enchanting_table.level_fifty"));
			main.removeKey("enchanting_table.level_fifty");
		}

		if (main.contains("enchanting_table.lapis_in_table")) {
			config.set("lapis_in_table", main.get("enchanting_table.lapis_in_table"));
			main.removeKey("enchanting_table.lapis_in_table");
		}

		if (main.contains("enchanting_table.reset_on_reload")) {
			config.set("reset_on_reload", main.get("enchanting_table.reset_on_reload"));
			main.removeKey("enchanting_table.reset_on_reload");
		}

		if (enchantments.contains("extra_enchantables")) {
			config.set("extra_enchantables", enchantments.get("extra_enchantables"));
			main.removeKey("extra_enchantables");
		}

		if (enchantments.contains("advanced_options.multi_enchant_divisor")) {
			if (enchantments.getBoolean("advanced_options.use")) {
				String level = "level" + (ConfigString.LEVEL_FIFTY.getBoolean() ? "_fifty" : "_thirty");
				config.set("multi_enchant_divisor." + level, enchantments.get("advanced_options.multi_enchant_divisor"));
			}
			enchantments.removeKey("advanced_options.multi_enchant_divisor");
		}

		if (enchantments.contains("advanced_options.enchantability_decay")) {
			if (enchantments.getBoolean("advanced_options.use")) config.set("enchantability_decay", enchantments.get("advanced_options.enchantability_decay"));
			enchantments.removeKey("advanced_options.enchantability_decay");
		}

		if (config.contains("advanced_options.starting_level")) {
			config.set("use_minimum_level", config.get("advanced_options.starting_level"));
			config.removeKey("advanced_options.starting_level");
		}
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing enchanting table configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/enchanting_table_defaults.yml");

		YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) try {
				config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			} catch (Exception ex) {
				Chatable.get().sendWarning("Config key " + str.replaceFirst("config_comments.", "") + " does not exist in the defaults file!");
			}
			else
				config.addDefault(str, defaultConfig.get(str));

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Enchanting table configuration initialized!");

		file.delete();
	}

}
