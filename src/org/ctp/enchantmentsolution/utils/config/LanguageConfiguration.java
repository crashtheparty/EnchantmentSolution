package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.Language;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.files.ESLanguageFile;

public class LanguageConfiguration extends Configuration {

	private ESLanguageFile language;

	public LanguageConfiguration(File file, String languageFile, ESLanguageFile language, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(file + "/" + languageFile), db, header);

		this.language = language;

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing language configuration...");
		YamlConfigBackup config = getConfig();
		config.addDefault("starter", "&8[&dEnchantment Solution&8]");
		config.copyDefaults(language.getConfig());

		config.writeDefaults();
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Language configuration initialized!");
	}

	@Override
	public void migrateVersion() {
		Configurations config = Configurations.getConfigurations();
		if (config.getConfig().getString("starter") != null) {
			getConfig().set("starter", config.getConfig().getString("starter"));
			config.getConfig().getConfig().removeKey("starter");
			config.getConfig().save();
		}
	}

	@Override
	public void repairConfig() {}

	public Language getLanguage() {
		return language.getLanguage();
	}

}
