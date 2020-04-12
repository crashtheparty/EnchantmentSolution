package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.files.LanguageFile;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class LanguageConfiguration extends Configuration {

	private LanguageFile language;

	public LanguageConfiguration(File file, String languageFile, LanguageFile language) {
		super(new File(file + "/" + languageFile), false);

		this.language = language;

		setDefaults();
		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		YamlConfigBackup config = getConfig();
		config.addDefault("starter", "&8[&dEnchantment Solution&8]");
		config.copyDefaults(language.getConfig());

		config.writeDefaults();
	}

	@Override
	public void migrateVersion() {
		if (Configurations.getConfig().getString("starter") != null) {
			getConfig().set("starter", Configurations.getConfig().getString("starter"));
			Configurations.getConfig().getConfig().removeKey("starter");
			Configurations.getConfig().save();
		}
	}

	public Language getLanguage() {
		return language.getLanguage();
	}

}
