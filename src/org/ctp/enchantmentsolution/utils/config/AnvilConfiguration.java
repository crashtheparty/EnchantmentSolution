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

public class AnvilConfiguration extends Configuration {

	public AnvilConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/anvil.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();
		YamlConfig main = Configurations.getConfigurations().getConfig().getConfig();

		if (main.contains("anvil.custom_gui")) {
			config.set("custom_gui", main.get("anvil.custom_gui"));
			main.removeKey("anvil.custom_gui");
		}

		if (main.contains("anvil.level_divisor")) {
			config.set("level_divisor", main.get("anvil.level_divisor"));
			main.removeKey("anvil.level_divisor");
		}

		if (main.contains("anvil.default_use")) {
			config.set("default_use", main.get("anvil.default_use"));
			main.removeKey("anvil.default_use");
		}

		if (main.contains("anvil.max_repair_level")) {
			config.set("max_repair_level", main.get("anvil.max_repair_level"));
			main.removeKey("anvil.max_repair_level");
		}

		if (main.contains("anvil.repair_cost_limit")) {
			config.set("repair_cost_limit", main.get("anvil.repair_cost_limit"));
			main.removeKey("anvil.repair_cost_limit");
		}

		if (main.contains("anvil.damage")) {
			config.set("damage", main.get("anvil.damage"));
			main.removeKey("anvil.damage");
		}

		if (main.contains("anvil.rename")) {
			config.set("rename", main.get("anvil.rename"));
			main.removeKey("anvil.rename");
		}
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing anvil configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/anvil_defaults.yml");

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

		config.addMinMax("max_repair_level", 40, 1000000);

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Anvil configuration initialized!");

		file.delete();
	}
}
