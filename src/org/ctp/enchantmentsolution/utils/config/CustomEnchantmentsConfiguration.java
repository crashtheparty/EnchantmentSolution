package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.List;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class CustomEnchantmentsConfiguration extends Configuration {

	public CustomEnchantmentsConfiguration(File dataFolder) {
		super(new File(dataFolder + "/extras/custom_enchantments.yml"), true);
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Loading custom enchantments configuration...");
		
		YamlConfigBackup config = getConfig();
		
		config.addDefault("custom_enchant.display-names.en_us", "Custom Enchant");
		config.addDefault("custom_enchant.display-names.de_de", "Failure to Uphold");
		config.addDefault("custom_enchant.descriptions.en_us", "A test custom enchantment for the custom enchantments configuration");
		
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Custom Enchantments configuration initialized!");
	}

	@Override
	public void migrateVersion() {}

	public List<String> getKeys(String string) {
		return getConfig().getLevelEntryKeysAtLevel(string);
	}

}
