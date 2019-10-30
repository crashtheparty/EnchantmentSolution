package org.ctp.enchantmentsolution.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.utils.config.*;

public class ConfigUtils {
		
	private ConfigUtils() {
		
	}

	public static int getMaxEnchantments() {
		return Configurations.getConfig().getInt("max_enchantments");
	}

	public static boolean isLevel50() {
		return Configurations.getConfig()
				.getString("enchanting_table.enchanting_type").contains("50");
	}

	public static boolean useESGUI() {
		return Configurations.getConfig()
				.getString("enchanting_table.enchanting_type").contains("enhanced");
	}

	public static boolean useAdvancedFile() {
		return Configurations.getConfig()
				.getString("enchanting_table.enchanting_type").contains("custom");
	}
	
	public static boolean useLegacyGrindstone() {
		if (VersionUtils.getBukkitVersionNumber() < 4) {
			return Configurations.getConfig().getBoolean("grindstone.use_legacy");
		}
		return false;
	}
	
	public static boolean isRepairable(CustomEnchantment enchant) {
		if (Configurations.getConfig().getString("disable_enchant_method")
				.equals("repairable")) {
			return true;
		}

		if (enchant.isEnabled()) {
			return true;
		}

		return false;
	}
	
	public static boolean getBoolean(Class<? extends Configuration> clazz, String s) {
		Configuration config = getConfiguration(clazz);
		if(config != null) {
			return config.getBoolean(s);
		}
		return false;
	}
	
	public static int getInt(Class<? extends Configuration> clazz, String s) {
		Configuration config = getConfiguration(clazz);
		if(config != null) {
			return config.getInt(s);
		}
		return 0;
	}
	
	public static double getDouble(Class<? extends Configuration> clazz, String s) {
		Configuration config = getConfiguration(clazz);
		if(config != null) {
			return config.getDouble(s);
		}
		return 0;
	}
	
	public static String getString(Class<? extends Configuration> clazz, String s) {
		Configuration config = getConfiguration(clazz);
		if(config != null) {
			return config.getString(s);
		}
		return null;
	}
	
	private static Configuration getConfiguration(Class<? extends Configuration> clazz) {
		String clazzName = clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
		switch(clazzName) {
			case "MainConfiguration":
				return Configurations.getConfig();
			case "FishingConfiguration":
				return Configurations.getFishing();
			case "LanguageConfiguration":
				return Configurations.getLanguage();
			case "EnchantmentsConfiguration":
				return Configurations.getEnchantments();
		}
		return null;
	}
	
	public static Language getLanguage() {
		return Configurations.getLanguage().getLanguage();
	}

	public static List<String> getStringList(Class<? extends Configuration> clazz, String s) {
		Configuration config = getConfiguration(clazz);
		if(config != null) {
			return config.getStringList(s);
		}
		return null;
	}
	
	public static boolean isAdvancementActive(String string) {
		return Configurations.getConfig()
				.getBoolean("advancements." + string + ".enable");
	}

	public static boolean toastAdvancement(String string) {
		return Configurations.getConfig()
				.getBoolean("advancements." + string + ".toast");
	}

	public static boolean announceAdvancement(String string) {
		return Configurations.getConfig()
				.getBoolean("advancements." + string + ".announce");
	}

	public static String getAdvancementName(String string) {
		return Configurations.getLanguage()
				.getString("advancements." + string + ".name");
	}

	public static String getAdvancementDescription(String string) {
		return Configurations.getLanguage()
				.getString("advancements." + string + ".description");
	}
	
	public static File getTempFile(String resource) {
		return new ConfigUtils().getFile(resource);
	}
	
	public File getFile(String resource) {
		File file = null;
	    URL res = getClass().getResource(resource);
	    if (res.getProtocol().equals("jar")) {
	    	InputStream input = null;
	    	OutputStream out = null;
	        try {
	            input = getClass().getResourceAsStream(resource);
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
	        //this will probably work in your IDE, but not from a JAR
	        file = new File(res.getFile());
	    }

	    if (file != null && !file.exists()) {
	        throw new RuntimeException("Error: File " + file + " not found!");
	    }
	    file.deleteOnExit();
	    return file;
	}
}
