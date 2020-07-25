package org.ctp.enchantmentsolution.crashapi.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfig;

public class ChatUtils {
	
	private static List<ChatUtils> utils = new ArrayList<ChatUtils>();
	private final CrashAPIPlugin plugin;
	
	public static ChatUtils getUtils(CrashAPIPlugin plugin) {
		for (ChatUtils cu : utils)
			if (cu.getPlugin().getName().equals(plugin.getName())) return cu;
		ChatUtils cu = new ChatUtils(plugin);
		utils.add(cu);
		return cu;
	}
	
	private ChatUtils(CrashAPIPlugin plugin) {
		this.plugin = plugin;
	}

	public void sendMessage(Player player, String message) {
		sendMessage(getStarter(), player, message);
	}

	public void sendMessage(String starter, Player player, String message) {
		if (message != null && !message.trim().equals("")) player.sendMessage(starter + message);
	}

	public void sendMessage(Player player, String message, String url) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"" + getStarter() + message + "\"},{\"text\":\"" + url + "\", \"italic\": true, \"color\": \"green\", \"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + url + "\"}}]");
	}

	public void sendMessage(Player player, String[] messages) {
		for(String s: messages)
			sendMessage(player, s);
	}

	public void sendMessage(CommandSender sender, Player player, String message, Level level) {
		if (player == null) sendToConsole(level, message);
		else
			sendMessage(player, message);
	}

	public void sendRawMessage(Player player, String json) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + json);
	}

	public String getStarter() {
		String config = plugin.getStarter();
		String starter = null;
		if (config != null) starter = ChatColor.translateAlternateColorCodes('&', config);
		if (starter != null) return starter + ChatColor.WHITE + " ";
		return "";
	}

	public void sendToConsole(Level level, String message) {
		sendToConsole(plugin, level, message);
	}

	public void sendToConsole(JavaPlugin plugin, Level level, String message) {
		plugin.getLogger().log(level, message);
	}

	public void sendWarning(String message) {
		sendToConsole(Level.WARNING, message);
	}

	public void sendInfo(String message) {
		sendToConsole(Level.INFO, message);
	}

	public void sendSevere(String message) {
		sendToConsole(Level.SEVERE, message);
	}

	public void sendWarning(JavaPlugin plugin, String message) {
		sendToConsole(plugin, Level.WARNING, message);
	}

	public void sendInfo(JavaPlugin plugin, String message) {
		sendToConsole(plugin, Level.INFO, message);
	}

	public void sendSevere(JavaPlugin plugin, String message) {
		sendToConsole(plugin, Level.SEVERE, message);
	}

	public String getMessage(HashMap<String, Object> codes, String location) {
		if (plugin.getLanguageFile() != null) return getMessage(codes, location, plugin.getLanguageFile());
		return "No valid language file!";
	}

	public String getMessage(HashMap<String, Object> codes, String location, YamlConfig config) {
		String s = "";
		try {
			s = translateCodes(codes, ChatColor.translateAlternateColorCodes('&', config.getString(location)));
		} catch (Exception e) {

		}
		if (s.isEmpty()) return location + " must be a string.";
		return s;
	}

	public String getMessage(String message, HashMap<String, Object> codes) {
		String s = "";
		try {
			s = translateCodes(codes, ChatColor.translateAlternateColorCodes('&', message));
		} catch (Exception e) {

		}
		return s;
	}

	public List<String> getMessages(HashMap<String, Object> codes, String location) {
		if (plugin.getLanguageFile() != null) return getMessages(codes, location, plugin.getLanguageFile());
		return Arrays.asList("No valid language file!");
	}

	public List<String> getMessages(HashMap<String, Object> codes, String location, YamlConfig config) {
		List<String> messages = config.getStringList(location);
		if (messages == null) {
			messages = new ArrayList<String>();
			messages.add(config.getString(location));
		}
		for(int i = 0; i < messages.size(); i++)
			if (messages.get(i) != null) messages.set(i, translateCodes(codes, ChatColor.translateAlternateColorCodes('&', messages.get(i))));
		if (messages.size() == 0) messages.add(location + " must be a list or a string.");
		return messages;
	}

	private String translateCodes(HashMap<String, Object> codes, String str) {
		for(Iterator<Entry<String, Object>> it = codes.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() != null) str = str.replaceAll(e.getKey(), e.getValue().toString());
		}
		return str;
	}

	public static HashMap<String, Object> getCodes() {
		return new HashMap<String, Object>();
	}

	@Nonnull
	public String hideText(@Nonnull String text) {
		Objects.requireNonNull(text, "text can not be null!");

		StringBuilder output = new StringBuilder();

		String hex = asciiToHex(text);

		for(char c: hex.toCharArray())
			output.append(ChatColor.COLOR_CHAR).append(c);

		return output.toString();
	}

	@Nonnull
	public String revealText(@Nonnull String text) {
		Objects.requireNonNull(text, "text can not be null!");

		if (text.isEmpty()) return text;

		char[] chars = text.toCharArray();

		char[] hexChars = new char[chars.length / 2];

		IntStream.range(0, chars.length).filter(value -> value % 2 != 0).forEach(value -> hexChars[value / 2] = chars[value]);

		String newChars = "";
		for(char c: hexChars)
			newChars += c;

		return new String(hexToASCII(newChars));
	}

	private String asciiToHex(String asciiValue) {
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for(int i = 0; i < chars.length; i++)
			hex.append(Integer.toHexString(chars[i]));
		return hex.toString();
	}

	private String hexToASCII(String hexValue) {
		StringBuilder output = new StringBuilder("");
		for(int i = 0; i < hexValue.length(); i += 2) {
			String str = hexValue.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public CrashAPIPlugin getPlugin() {
		return plugin;
	}
}
