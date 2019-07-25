package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.advancements.ESLocalization;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.nms.ItemNameNMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class LanguageFiles {
	
	private File languageFile, englishUSFile, germanFile;
	private YamlConfig englishUS, german;
	private Language defaultLanguage;
	private YamlConfigBackup language;
	private ConfigFiles files;
	
	public LanguageFiles(ConfigFiles files, File langFile, Language lang) {
		this.files = files;
		languageFile = langFile;
		defaultLanguage = lang;
		
		createDefaultFiles();
		
		YamlConfig main = files.getDefaultConfig();
		boolean getFromConfig = true;
		if(main.getBoolean("reset_language")) {
			getFromConfig = false;
			main.set("reset_language", false);
			main.saveConfig();
		}
		
		save(getFromConfig);
	}
	
	public void addDefault(String path, CustomEnchantment enchantment, String type) {
		switch(type) {
		case "display_name":
			englishUS.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDisplayName(Language.US)));
			german.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDisplayName(Language.GERMAN)));
			break;
		case "description":
			englishUS.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDescription(Language.US)));
			german.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDescription(Language.GERMAN)));
			break;
		}
	}
	
	private void save(boolean getFromConfig) {
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading language file...");
		}
		language = new YamlConfigBackup(languageFile, null);
		if(getFromConfig) {
			language.getFromConfig();
		}
		language.copyDefaults(getLanguageFile());
		
		language.saveConfig();
		if(EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Language file initialized!");
		}
	}

	public YamlConfigBackup getLanguageConfig() {
		return language;
	}

	public void setLanguageConfig(YamlConfigBackup language) {
		this.language = language;
	}
	
	public void setLanguage(File langFile, Language lang) {
		languageFile = langFile;
		defaultLanguage = lang;
		createDefaultFiles();
		YamlConfig main = EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig();
		boolean getFromConfig = true;
		if(main.getBoolean("reset_language")) {
			getFromConfig = false;
			main.set("reset_language", false);
			main.saveConfig();
		}
		save(getFromConfig);
	}
	
	private YamlConfig getLanguageFile() {
		switch(defaultLanguage) {
		case US:
			return englishUS;
		case GERMAN:
			return german;
		}
		return englishUS;
	}
	
	private void createDefaultFiles() {
		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();
		
		try {
			File langs = new File(dataFolder + "/languages/");
			if (!langs.exists()) {
				langs.mkdirs();
			}
			englishUSFile = new File(dataFolder + "/languages/en_us.yml");
			YamlConfiguration.loadConfiguration(englishUSFile);
			germanFile = new File(dataFolder + "/languages/de_de.yml");
			YamlConfiguration.loadConfiguration(germanFile);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		defaultenglishUSFile();
		defaultGermanFile();
	}
	
	private void defaultenglishUSFile() {
		if(englishUS == null)
			englishUS = new YamlConfigBackup(englishUSFile, new String[0]);
				
		File file = files.getTempFile("/resources/en_us.yml");
		
		YamlConfig defaultEnglishConfig = new YamlConfig(file, new String[] {});
		defaultEnglishConfig.getFromConfig();
		for(String str : defaultEnglishConfig.getAllEntryKeys()) {
			if(defaultEnglishConfig.get(str) != null) {
				if(str.startsWith("config_comments.")) {
					englishUS.addComments(str, defaultEnglishConfig.getStringList(str).toArray(new String[] {}));
				} else {
					englishUS.addDefault(str, defaultEnglishConfig.get(str));
				}
			}
		}
		
		for (Iterator<java.util.Map.Entry<Material, String>> it = ItemType.ALL.getUnlocalizedNames().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Material, String> e = it.next();
			englishUS.addDefault("vanilla." + e.getValue(), ItemNameNMS.returnLocalizedItemName(Language.US, e.getKey()));
		}
		
		for(ESAdvancement advancement : ESAdvancement.values()) {
			List<ESLocalization> localizations = advancement.getLocalizations();
			String localeName = "No name specified";
			String localeDescription = "No description specified";
			for(ESLocalization locale : localizations) {
				if(locale.getLanguage() == Language.US) {
					localeName = locale.getName();
					localeDescription = locale.getDescription();
				}
			}
			englishUS.addDefault("advancements." + advancement.getNamespace().getKey() + ".name", localeName);
			englishUS.addDefault("advancements." + advancement.getNamespace().getKey() + ".description", localeDescription);
		}
		
		englishUS.saveConfig();
		file.delete();
	}

	private void defaultGermanFile() {
		if(german == null)
			german = new YamlConfigBackup(germanFile, new String[0]);
				
		File file = files.getTempFile("/resources/de_de.yml");
		
		YamlConfig defaultGermanConfig = new YamlConfig(file, new String[] {});
		defaultGermanConfig.getFromConfig();
		for(String str : defaultGermanConfig.getAllEntryKeys()) {
			if(defaultGermanConfig.get(str) != null) {
				if(str.startsWith("config_comments.")) {
					german.addComments(str, defaultGermanConfig.getStringList(str).toArray(new String[] {}));
				} else {
					german.addDefault(str, defaultGermanConfig.get(str));
				}
			}
		}
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			String enchantmentDescription = enchant.getDefaultDescription(Language.GERMAN);
			if(enchantmentDescription == null) {
				enchantmentDescription = "Keine Beschreibung angegeben";
			}
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set language defaults.");
					continue;
				}
				german.addDefault("enchantment.descriptions." + plugin.getName().toLowerCase() + "." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				german.addDefault("enchantment.descriptions." + "custom_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else {
				german.addDefault("enchantment.descriptions." + "default_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			}
		}
		
		for (Iterator<java.util.Map.Entry<Material, String>> it = ItemType.ALL.getUnlocalizedNames().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Material, String> e = it.next();
			german.addDefault("vanilla." + e.getValue(), ItemNameNMS.returnLocalizedItemName(Language.GERMAN, e.getKey()));
		}
		
		german.saveConfig();
		file.delete();
	}
}
