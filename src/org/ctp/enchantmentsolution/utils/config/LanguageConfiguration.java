package org.ctp.enchantmentsolution.utils.config;

import java.io.File;

import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.utils.files.LanguageFile;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class LanguageConfiguration extends Configuration {

	private LanguageFile language;

	public LanguageConfiguration(File file, String languageFile, LanguageFile language) {
		super(new File(file + "/" + languageFile), false);

		this.language = language;

		setDefaults();
	}

	@Override
	public void setDefaults() {
		YamlConfigBackup config = getConfig();

		config.copyDefaults(language.getConfig());

		config.writeDefaults();
	}

	@Override
	public void migrateVersion() {

	}

	public Language getLanguage() {
		return language.getLanguage();
	}

}
