package org.ctp.enchantmentsolution.utils.files;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.nms.ItemNameNMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;

public class LanguageFile {

	private Language language;
	private File file;
	private YamlConfig config;

	public LanguageFile(File dataFolder, Language language) {
		setLanguage(language);

		File tempFile = ConfigUtils.getTempFile("/resources/" + language.getLocale() + ".yml");

		file = new File(dataFolder + "/language/" + language.getLocale() + ".yml");

		YamlConfiguration.loadConfiguration(file);
		config = new YamlConfig(file, new String[] {});
		config.getFromConfig();

		YamlConfig defaultConfig = new YamlConfig(tempFile, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			else
				config.addDefault(str, defaultConfig.get(str));

		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			String enchantmentDescription = enchant.getDefaultDescription(Language.US);
			if (enchantmentDescription == null) switch (language) {
				case US:
					enchantmentDescription = "No description specified";
					break;
				case GERMAN:
					enchantmentDescription = "Keine Beschreibung angegeben";
					break;
				case CHINA_SIMPLE:
					enchantmentDescription = "没有说明";
					break;
			}
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to set language defaults.");
					continue;
				}
				config.addDefault("enchantment.descriptions." + plugin.getName().toLowerCase() + "." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) config.addDefault("enchantment.descriptions." + "custom_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			else
				config.addDefault("enchantment.descriptions." + "default_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
		}

		for(Iterator<java.util.Map.Entry<Material, String>> it = ItemType.ALL.getUnlocalizedNames().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Material, String> e = it.next();
			config.addDefault("vanilla." + e.getValue(), ItemNameNMS.returnLocalizedItemName(language, e.getKey()));
		}

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
