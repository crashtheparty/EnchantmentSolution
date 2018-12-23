package org.ctp.enchantmentsolution.nms;

import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Version {
	
	public static String VERSION = getVersion();
	public static boolean VERSION_ALLOWED = allowedVersion();
	public static int VERSION_NUMBER;

	private static String getVersion() {
		String a = Bukkit.getVersion();
		String version = a.substring(a.lastIndexOf(':') + 1, a.lastIndexOf(')')).trim();

		return version;
	}
	
	private static boolean allowedVersion() {
		
		VERSION_NUMBER = 0;
		
		// Version
		Bukkit.getLogger().log(Level.INFO, "[EnchantmentSolution] Version:  " + VERSION);

		// Check
		switch(VERSION) {
		case "1.13":
			VERSION_NUMBER = 1;
			return true;
		case "1.13.1":
			VERSION_NUMBER = 2;
			return true;
		case "1.13.2":
			VERSION_NUMBER = 3;
			return true;
		}
		return false;
	}
}
