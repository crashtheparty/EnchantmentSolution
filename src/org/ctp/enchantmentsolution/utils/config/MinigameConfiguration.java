package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class MinigameConfiguration extends Configuration {

	public MinigameConfiguration(File dataFolder) {
		super(new File(dataFolder + "/minigame.yml"));

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Loading Minigame configuration...");

		YamlConfigBackup config = getConfig();

		File file = ConfigUtils.getTempFile("/resources/minigame_defaults.yml");

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

		config.addEnum("type", Arrays.asList("FAST", "MONDAYS", "CUSTOM"));
		
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Minigame configuration initialized!");

		config.saveConfig();

		file.delete();
	}

	@Override
	public void migrateVersion() {}

}
