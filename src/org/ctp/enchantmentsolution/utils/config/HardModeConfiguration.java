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

public class HardModeConfiguration extends Configuration {

	public HardModeConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/hard.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing Hard Mode configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/hard_defaults.yml");

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
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Hard Mode configuration initialized!");

		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
