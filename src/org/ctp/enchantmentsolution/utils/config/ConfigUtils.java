package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.VersionUtils;

public class ConfigUtils {

	private ConfigUtils() {

	}

	public static boolean useLegacyGrindstone() {
		if (VersionUtils.getBukkitVersionNumber() < 4) {
			return ConfigString.LEGACY_GRINDSTONE.getBoolean();
		}
		return false;
	}

	public static boolean isRepairable(CustomEnchantment enchant) {
		if (Configurations.getConfig().getString("disable_enchant_method").equals("repairable")) {
			return true;
		}

		if (enchant.isEnabled()) {
			return true;
		}

		return false;
	}

	public static boolean getBoolean(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) {
			return config.getBoolean(s);
		}
		return false;
	}

	public static int getInt(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) {
			return config.getInt(s);
		}
		return 0;
	}

	public static double getDouble(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) {
			return config.getDouble(s);
		}
		return 0;
	}

	public static String getString(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) {
			return config.getString(s);
		}
		return null;
	}

	public static Language getLanguage() {
		return Configurations.getLanguage().getLanguage();
	}

	public static List<String> getStringList(Type type, String s) {
		Configuration config = type.getConfig();
		if (config != null) {
			return config.getStringList(s);
		}
		return null;
	}

	public static boolean isAdvancementActive(String string) {
		return Configurations.getAdvancements().getBoolean("advancements." + string + ".enable");
	}

	public static boolean toastAdvancement(String string) {
		return Configurations.getAdvancements().getBoolean("advancements." + string + ".toast");
	}

	public static boolean announceAdvancement(String string) {
		return Configurations.getAdvancements().getBoolean("advancements." + string + ".announce");
	}

	public static String getAdvancementName(String string) {
		String name = Configurations.getLanguage().getString("advancements." + string + ".name");
		if(name == null) name = Configurations.getLanguage().getString("misc.null_advancement_name");
		if(name == null) name = "No Name";
		return name;
	}

	public static String getAdvancementDescription(String string) {
		String desc = Configurations.getLanguage().getString("advancements." + string + ".description");
		if(desc == null) desc = Configurations.getLanguage().getString("misc.null_advancement_description");
		if(desc == null) desc = "No Description";
		return desc;
	}

	public static boolean getAdvancedBoolean(ConfigString string, boolean defVal) {
		if(string.getLocation().contains("advanced")) {
			if(ConfigString.ADVANCED_OPTIONS.getBoolean()) {
				return string.getBoolean();
			}
			return defVal;
		} else {
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
		}
	}

	public static int getAdvancedInt(ConfigString string, int defVal) {
		if(string.getLocation().contains("advanced")) {
			if(ConfigString.ADVANCED_OPTIONS.getBoolean()) {
				return string.getInt();
			}
			return defVal;
		} else {
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
		}
	}

	public static double getAdvancedDouble(ConfigString string, double defVal) {
		if(string.getLocation().contains("advanced")) {
			if(ConfigString.ADVANCED_OPTIONS.getBoolean()) {
				return string.getDouble();
			}
			return defVal;
		} else {
			throw new IllegalArgumentException("ConfigString must be an advanced option!");
		}
	}

	public static File getTempFile(String resource) {
		return new ConfigUtils().getFile(resource);
	}

	public static File getTempFile(Class<?> clazz, String resource) {
		return new ConfigUtils().getFile(clazz, resource);
	}

	public File getFile(String resource) {
		return getFile(getClass(), resource);
	}

	public File getFile(Class<?> clazz, String resource) {
		File file = null;
		URL res = clazz.getResource(resource);
		if (res.getProtocol().equals("jar")) {
			InputStream input = null;
			OutputStream out = null;
			try {
				input = clazz.getResourceAsStream(resource);
				file = File.createTempFile("/tempfile", ".tmp");
				out = new FileOutputStream(file);
				int read;
				byte[] bytes = new byte[1024];

				while ((read = input.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			// this will probably work in your IDE, but not from a JAR
			file = new File(res.getFile());
		}

		if (file != null && !file.exists()) {
			throw new RuntimeException("Error: File " + file + " not found!");
		}
		file.deleteOnExit();
		return file;
	}
}
