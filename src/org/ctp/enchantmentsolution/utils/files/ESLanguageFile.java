package org.ctp.enchantmentsolution.utils.files;

import java.io.File;
import java.util.Locale;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.config.CrashLanguageFile;
import org.ctp.crashapi.config.Language;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.crashapi.utils.StringUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;

public class ESLanguageFile extends CrashLanguageFile {

	public ESLanguageFile(File dataFolder, Language language) {
		super(dataFolder, language);
		YamlConfig config = getConfig();
		config.getFromConfig();

		File tempFile = CrashConfigUtils.getTempFile(getClass(), "/resources/" + language.getLocale() + ".yml");
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
					Chatable.get().sendWarning("Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to set language defaults.");
					continue;
				}
				config.addDefault("enchantment.descriptions." + plugin.getName().toLowerCase(Locale.ROOT) + "." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) config.addDefault("enchantment.descriptions." + "custom_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			else
				config.addDefault("enchantment.descriptions." + "default_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
		}

		config.saveConfig();
	}
}
