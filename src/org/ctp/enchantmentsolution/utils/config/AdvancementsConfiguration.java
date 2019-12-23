package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class AdvancementsConfiguration extends Configuration {

	public AdvancementsConfiguration(File dataFolder) {
		super(new File(dataFolder + "/advancements.yml"), true);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading advancements configuration...");
		}
		YamlConfigBackup config = getConfig();

		for(ESAdvancement advancement: ESAdvancement.values()) {
			if (advancement == ESAdvancement.ENCHANTMENT_SOLUTION) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", false);
			} else if (advancement.getActivatedVersion() < EnchantmentSolution.getPlugin().getBukkitVersion()
			.getVersionNumber()) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", true);
			}
		}

		if (EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Advancements configuration initialized!");
		}
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();
		YamlConfigBackup main = Configurations.getConfig().getConfig();

		for(String s : main.getLevelEntryKeys("advancements")) {
			for(String t : main.getLevelEntryKeys(s)) {
				if(main.get(t) != null) {
					config.set(t, main.get(t));
					main.removeKey(t);
				}
			}
			main.removeKey(s);
		}
		main.saveConfig();
	}

}
