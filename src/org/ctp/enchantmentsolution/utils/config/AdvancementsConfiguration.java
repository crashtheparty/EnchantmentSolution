package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.Configurations;

public class AdvancementsConfiguration extends Configuration {

	public AdvancementsConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/advancements.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing advancements configuration...");
		YamlConfigBackup config = getConfig();

		config.addDefault("discovery_advancements", false);

		for(ESAdvancement advancement: ESAdvancement.values())
			if (advancement.getParent() == null) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", false);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", false);
			} else if (advancement.getActivatedVersion() < CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".enable", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".toast", true);
				config.addDefault("advancements." + advancement.getNamespace().getKey() + ".announce", true);
			}

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Advancements configuration initialized!");
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();
		YamlConfigBackup main = Configurations.getConfigurations().getConfig().getConfig();

		for(String s: main.getLevelEntryKeys("advancements")) {
			for(String t: main.getLevelEntryKeys(s))
				if (main.get(t) != null) {
					config.set(t, main.get(t));
					main.removeKey(t);
				}
			main.removeKey(s);
		}
		main.saveConfig();
	}

	@Override
	public void repairConfig() {}

}
