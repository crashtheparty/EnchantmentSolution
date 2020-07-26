package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class FishingConfiguration extends Configuration {

	public FishingConfiguration(File dataFolder, BackupDB db) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/fishing.yml"), db);

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Loading fishing config...");

		YamlConfigBackup config = getConfig();

		File file = ConfigUtils.getTempFile("/resources/fishing_defaults.yml");

		YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			else
				config.addDefault(str, defaultConfig.get(str));

		config.writeDefaults();

		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Fishing config initialized!");
		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
