package org.ctp.enchantmentsolution.utils.config;

import java.util.List;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.VersionUtils;

public class ConfigUtils {

	private ConfigUtils() {

	}

	public static boolean useLegacyGrindstone() {
		if (VersionUtils.getBukkitVersionNumber() < 4) return ConfigString.LEGACY_GRINDSTONE.getBoolean();
		return false;
	}

	public static boolean isRepairable(CustomEnchantment enchant) {
		if (Configurations.getConfigurations().getConfig().getString("disable_enchant_method").equals("repairable")) return true;

		if (enchant.isEnabled()) return true;

		return false;
	}

	public static boolean getBoolean(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) return config.getBoolean(s);
		return false;
	}

	public static int getInt(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) return config.getInt(s);
		return 0;
	}

	public static double getDouble(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) return config.getDouble(s);
		return 0;
	}

	public static String getString(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) return config.getString(s);
		return null;
	}

	public static Language getLanguage() {
		return Configurations.getConfigurations().getLanguage().getLanguage();
	}

	public static List<String> getStringList(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) return config.getStringList(s);
		return null;
	}

	public static boolean isAdvancementActive(String string) {
		return Configurations.getConfigurations().getAdvancements().getBoolean("advancements." + string + ".enable");
	}

	public static boolean toastAdvancement(String string) {
		return Configurations.getConfigurations().getAdvancements().getBoolean("advancements." + string + ".toast");
	}

	public static boolean announceAdvancement(String string) {
		return Configurations.getConfigurations().getAdvancements().getBoolean("advancements." + string + ".announce");
	}

	public static String getAdvancementName(String string) {
		String name = Configurations.getConfigurations().getLanguage().getString("advancements." + string + ".name");
		if (name == null) name = Configurations.getConfigurations().getLanguage().getString("misc.null_advancement_name");
		if (name == null) name = "No Name";
		return name;
	}

	public static String getAdvancementDescription(String string) {
		String desc = Configurations.getConfigurations().getLanguage().getString("advancements." + string + ".description");
		if (desc == null) desc = Configurations.getConfigurations().getLanguage().getString("misc.null_advancement_description");
		if (desc == null) desc = "No Description";
		return desc;
	}

	public static boolean getAdvancedBoolean(ConfigString string, boolean defVal) {
		if (string.getLocation().contains("advanced")) {
			if (ConfigString.ADVANCED_OPTIONS.getBoolean()) return string.getBoolean();
			return defVal;
		} else
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
	}

	public static int getAdvancedInt(ConfigString string, int defVal) {
		if (string.getLocation().contains("advanced")) {
			if (ConfigString.ADVANCED_OPTIONS.getBoolean()) return string.getInt();
			return defVal;
		} else
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
	}

	public static double getAdvancedDouble(ConfigString string, double defVal) {
		if (string.getLocation().contains("advanced")) {
			if (ConfigString.ADVANCED_OPTIONS.getBoolean()) return string.getDouble();
			return defVal;
		} else
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
	}
}
