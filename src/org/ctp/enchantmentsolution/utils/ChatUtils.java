package org.ctp.enchantmentsolution.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class ChatUtils {

	public static void sendMessage(Player player, String message) {
		if(message != null && !message.trim().equals("")) {
			player.sendMessage(getStarter() + message);
		}
	}
	
	public static void sendMessage(Player player, String message, String url) {
		Bukkit.getServer().dispatchCommand(
		        Bukkit.getConsoleSender(),
		        "tellraw " + player.getName() + 
		        " [{\"text\":\"" + getStarter() + message + "\"},{\"text\":\"" + url + "\", \"italic\": true, \"color\": \"green\", \"clickEvent\":{\"action\":\"open_url\",\"value\":\"" +
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
		String starter = ChatColor.translateAlternateColorCodes('&', EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("starter"));
		if(starter != null) {
			return starter + ChatColor.WHITE + " ";
		}
		return "";
	}
	
	public static void sendToConsole(Level level, String message) {
		EnchantmentSolution.getPlugin().getLogger().log(level, message);
	}
	
	public static void sendWarning(String message) {
		sendToConsole(Level.WARNING, message);
	}
	
	public static void sendInfo(String message) {
		sendToConsole(Level.INFO, message);
	}
	
	public static void sendSevere(String message) {
		sendToConsole(Level.SEVERE, message);
	}
	
	public static String getMessage(HashMap<String, Object> codes, String location) {
		String s = "";
		try {
			s = translateCodes(codes, ChatColor.translateAlternateColorCodes('&', EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getString(location)));
		} catch (Exception e) {
			
		}
		if(s.isEmpty()) return location + " must be a string.";
		return s;
	}
	
	public static List<String> getMessages(HashMap<String, Object> codes, String location) {
		List<String> messages = EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getStringList(location);
		if(messages == null) {
			messages = new ArrayList<String>();
			messages.add(EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getString(location));
		}
		for(int i = 0; i < messages.size(); i++) {
			messages.set(i, translateCodes(codes, ChatColor.translateAlternateColorCodes('&', messages.get(i))));
		}
		if(messages.size() == 0) messages.add(location + " must be a list or a string.");
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
	
    /**
     * Hides text in color codes
     *
     * @param text The text to hide
     * @return The hidden text
     */
    @Nonnull
    public static String hideText(@Nonnull String text) {
        Objects.requireNonNull(text, "text can not be null!");

        StringBuilder output = new StringBuilder();

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String hex = Hex.encodeHexString(bytes);

        for (char c : hex.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }

    /**
     * Reveals the text hidden in color codes
     *
     * @param text The hidden text
     * @throws IllegalArgumentException if an error occurred while decoding.
     * @return The revealed text
     */
    @Nonnull
    public static String revealText(@Nonnull String text) {
        Objects.requireNonNull(text, "text can not be null!");

        if (text.isEmpty()) {
            return text;
        }

        char[] chars = text.toCharArray();

        char[] hexChars = new char[chars.length / 2];

        IntStream.range(0, chars.length)
                .filter(value -> value % 2 != 0)
                .forEach(value -> hexChars[value / 2] = chars[value]);

        try {
            return new String(Hex.decodeHex(hexChars), StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't decode text", e);
        }
    }
}
