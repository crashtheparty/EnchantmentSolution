package org.ctp.enchantmentsolution.version;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class BukkitVersion {
	
	private String version = getBukkitVersion();
	private boolean versionAllowed = allowedBukkitVersion();
	private int versionNumber;

	private String getBukkitVersion() {
		String a = Bukkit.getVersion();
		String version = a.substring(a.lastIndexOf(':') + 1, a.lastIndexOf(')')).trim();

		return version;
	}
	
	private boolean allowedBukkitVersion() {
		versionNumber = 0;
		
		// BukkitVersion
		ChatUtils.sendToConsole(Level.INFO, "Bukkit Version:  " + version);

		// Check
		switch(version) {
		case "1.13":
			versionNumber = 1;
			return true;
		case "1.13.1":
			versionNumber = 2;
			return true;
		case "1.13.2":
			versionNumber = 3;
			return true;
		}
		return false;
	}

	public boolean isVersionAllowed() {
		return versionAllowed;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public String getVersion() {
		return version;
	}
}
