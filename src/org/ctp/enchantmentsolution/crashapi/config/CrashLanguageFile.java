package org.ctp.enchantmentsolution.crashapi.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfig;

public class CrashLanguageFile {

	private Language language;
	private File file;
	private YamlConfig config;

	public CrashLanguageFile(File dataFolder, Language language) {
		setLanguage(language);

		file = new File(dataFolder + "/language/" + language.getLocale() + ".yml");

		YamlConfiguration.loadConfiguration(file);
		config = new YamlConfig(file, new String[] {});
		config.getFromConfig();

		config.saveConfig();
	}

	public YamlConfig getConfig() {
		return config;
	}

	public void setConfig(YamlConfig config) {
		this.config = config;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
