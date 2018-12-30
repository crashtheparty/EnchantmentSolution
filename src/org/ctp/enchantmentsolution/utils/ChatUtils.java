package org.ctp.enchantmentsolution.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class ChatUtils {

	public static void sendMessage(Player player, String message) {
		player.sendMessage(getStarter() + message);
	}
	
	public static void sendMessage(Player player, String message, String url) {
		Bukkit.getServer().dispatchCommand(
		        Bukkit.getConsoleSender(),
		        "tellraw " + player.getName() + 
		        " [{\"text\":\"" + getStarter() + message + "\"},{\"text\":\"" + url + "\", \"underlined\": true, \"clickEvent\":{\"action\":\"open_url\",\"value\":\"" +
		        url + "\"}}]");
	}
	
	public static void sendMessage(Player player, String[] messages) {
		for(String s : messages) {
			sendMessage(player, s);
		}
	}
	
	public static void sendRawMessage(Player player, String json) {
		Bukkit.getServer().dispatchCommand(
		        Bukkit.getConsoleSender(),
		        "tellraw " + player.getName() + " " + json);
	}
	
	private static String getStarter() {
		String starter = ChatColor.translateAlternateColorCodes('&', ConfigFiles.getDefaultConfig().getString("starter"));
		if(starter != null) {
			return starter + ChatColor.WHITE + " ";
		}
		return "";
	}
	
	public static void sendToConsole(Level level, String message) {
		EnchantmentSolution.PLUGIN.getLogger().log(level, message);
	}
	
	public static String getMessage(HashMap<String, Object> codes, String location) {
		String s = "";
		try {
			s = translateCodes(codes, ChatColor.translateAlternateColorCodes('&', ConfigFiles.getLanguageFile().getString(location)));
		} catch (Exception e) {
			
		}
		if(s.isEmpty()) return location + " must be a string.";
		return s;
	}
	
	public static List<String> getMessages(HashMap<String, Object> codes, String location) {
		List<String> messages = ConfigFiles.getLanguageFile().getStringList(location);
		for(int i = 0; i < messages.size(); i++) {
			messages.set(i, translateCodes(codes, ChatColor.translateAlternateColorCodes('&', messages.get(i))));
		}
		if(messages.size() == 0) messages.add(location + " must be a list.");
		return messages;
	}
	
	private static String translateCodes(HashMap<String, Object> codes, String str) {
		for (Iterator<java.util.Map.Entry<String, Object>> it = codes.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, Object> e = it.next();
			if(e.getValue() != null) {
				str = str.replaceAll(e.getKey(), e.getValue().toString());
			}
		}
		return str;
	}
	
	public static HashMap<String, Object> getCodes(){
		return new HashMap<String, Object>();
	}
}
