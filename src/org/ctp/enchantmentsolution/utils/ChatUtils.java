package org.ctp.enchantmentsolution.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class ChatUtils {

	public static void sendMessage(Player player, String message) {
		player.sendMessage(getStarter() + message);
	}
	
	public static void sendMessage(Player player, String[] messages) {
		for(String s : messages) {
			sendMessage(player, s);
		}
	}
	
	private static String getStarter() {
		String starter = ChatColor.translateAlternateColorCodes('&', ConfigFiles.MAIN_CONFIG.getString("starter"));
		if(starter != null) {
			return starter + ChatColor.WHITE + " ";
		}
		return "";
	}
}
