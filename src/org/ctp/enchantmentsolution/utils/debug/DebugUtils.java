package org.ctp.enchantmentsolution.utils.debug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.config.DataFile;
import org.ctp.enchantmentsolution.utils.Configurations;

public class DebugUtils {

	private static List<String> DEBUG = new ArrayList<String>();

	public static void addDebug(JavaPlugin plugin, String s, Level log) {
		DataFile file = Configurations.getConfigurations().getDebugFile();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String full = "{" + plugin.getName() + "} [" + dtf.format(now) + "] Level " + log.getLocalizedName() + ": " + s;
		if (file == null) DEBUG.add(full);
		else {
			int i = 0;
			while (file.getString("debug.message." + i + "_message") != null)
				i++;
			if (DEBUG.size() > 0) {
				for(String str: DEBUG) {
					file.getConfig().set("debug.message." + i + "_message", str);
					i++;
				}
				DEBUG = new ArrayList<String>();
			}
			file.getConfig().set("debug.message." + i + "_message", full);
		}
	}
}
