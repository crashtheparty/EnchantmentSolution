package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class HardModeConfiguration extends Configuration {

	public HardModeConfiguration(File dataFolder, BackupDB db) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/hard.yml"), db);

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Loading Hard Mode configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile("/resources/hard_defaults.yml");

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

		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Hard Mode configuration initialized!");

		config.saveConfig();

		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
