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

public class GrindstoneConfiguration extends Configuration {

	public GrindstoneConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/grindstone.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();
		YamlConfig main = Configurations.getConfigurations().getConfig().getConfig();

		if (main.contains("grindstone.custom_gui")) {
			config.set("custom_gui", main.get("grindstone.custom_gui"));
			main.removeKey("grindstone.custom_gui");
		}

		if (main.contains("grindstone.take_enchantments")) {
			config.set("take_enchantments", main.get("grindstone.take_enchantments"));
			main.removeKey("grindstone.take_enchantments");
		}

		if (main.contains("grindstone.set_repair_cost")) {
			config.set("set_repair_cost", main.get("grindstone.set_repair_cost"));
			main.removeKey("grindstone.set_repair_cost");
		}

		if (main.contains("grindstone.destroy_take_item")) {
			config.set("destroy_take_item", main.get("grindstone.destroy_take_item"));
			main.removeKey("grindstone.destroy_take_item");
		}
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing grindstone configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/grindstone_defaults.yml");

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

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Grindstone configuration initialized!");

		file.delete();
	}
}
