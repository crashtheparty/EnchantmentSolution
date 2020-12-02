package org.ctp.enchantmentsolution.utils.debug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DebugUtils {

	private static List<String> DEBUG = new ArrayList<String>();
	
	public static void addDebug(String s, Level log) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String full = "[" + dtf.format(now) + " ] Level " + log.getLocalizedName() + ": " + s;
		DEBUG.add(full);
	}

	public static List<String> getMessages() {
		return DEBUG;
	}
}
