package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class RPGConfiguration extends Configuration {

	public RPGConfiguration(File dataFolder) {
		super(new File(dataFolder + "/rpg.yml"));

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Loading RPG configuration...");

		YamlConfigBackup config = getConfig();

		File file = ConfigUtils.getTempFile("/resources/rpg_defaults.yml");

		YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) try {
				config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			} catch (Exception ex) {
				ChatUtils.sendWarning("Config key " + str.replaceFirst("config_comments.", "") + " does not exist in the defaults file!");
			}
			else
				config.addDefault(str, defaultConfig.get(str));
		
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("RPG configuration initialized!");

		config.saveConfig();

		file.delete();
	}

	@Override
	public void migrateVersion() {
	}
}
