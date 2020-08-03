package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class FishingConfiguration extends Configuration {

	public FishingConfiguration(File dataFolder) {
		super(new File(dataFolder + "/fishing.yml"));

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Loading fishing config...");

		YamlConfigBackup config = getConfig();

		File file = ConfigUtils.getTempFile("/resources/fishing_defaults.yml");

		YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			else
				config.addDefault(str, defaultConfig.get(str));

		config.writeDefaults();

		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Fishing config initialized!");
		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
