package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class StringUtils {
	
	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel){
		String displayName = ench.getDisplayName();
		if(ench.isCurse()) {
			displayName = ChatColor.RED + displayName;
		}
	    if(enchLevel == 1 && ench.getMaxLevel() == 1){
	        return displayName;
	    }
	    if(enchLevel > 10 || enchLevel <= 0){
	        return displayName + " enchantment.level." + enchLevel;
	    }
	 
	    return displayName + " " + NUMERALS[enchLevel- 1];
	}
	
	public static EnchantmentLevel returnEnchantmentLevel(String s, ItemMeta meta) {
		String[] pieces = s.split(" ");
		int level = 0;
		int repair = 0;
		if (pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(".");
			level = Integer.parseInt(enchLevel[enchLevel.length - 1]);
			repair = pieces.length - 1;
		} else {
			for(int i = 0; i < NUMERALS.length; i++) {
				if(pieces[pieces.length - 1].equals(NUMERALS[i])) {
					level = i + 1;
					break;
				}
			}
			if(level == 0) {
				level = 1;
				repair = pieces.length;
			}else {
				repair = pieces.length - 1;
			}
		}
		String repaired = pieces[0];
		for(int i = 1; i < repair; i++) {
			repaired += " " + pieces[i];
		}
		CustomEnchantment match = null;
		for(CustomEnchantment ench : DefaultEnchantments.getEnchantments()) {
			if(ench.getDisplayName().equals(repaired)) {
				if(meta.hasEnchant(ench.getRelativeEnchantment())) {
					// already has the enchantment, so do nothing about it
					return null;
				}
				match = ench;
			}
		}
		
		if(match == null) {
			// not an enchantment, so don't add one
			return null;
		}
		
		return new EnchantmentLevel(match, level);
	}
	
	public static boolean isEnchantment(String s) {
		if(s.startsWith(ChatUtils.hideText("solution") + "" + ChatColor.GRAY)) return true;
		s = ChatColor.stripColor(s);
		String[] pieces = s.split(" ");
		int level = 0;
		int repair = 0;
		if (pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(".");
			level = Integer.parseInt(enchLevel[enchLevel.length - 1]);
			repair = pieces.length - 1;
		} else {
			for(int i = 0; i < NUMERALS.length; i++) {
				if(pieces[pieces.length - 1].equals(NUMERALS[i])) {
					level = i + 1;
					break;
				}
			}
			if(level == 0) {
				level = 1;
				repair = pieces.length;
			}else {
				repair = pieces.length - 1;
			}
		}
		String repaired = pieces[0];
		for(int i = 1; i < repair; i++) {
			repaired += " " + pieces[i];
		}
		for(CustomEnchantment ench : DefaultEnchantments.getEnchantments()) {
			if(ench.getDisplayName().equals(repaired)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addAnimal(ItemStack item, int entityID) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		lore.add(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static List<Integer> getAnimalIDsFromItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		List<Integer> ids = new ArrayList<Integer>();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		for(String l : lore) {
			if(l.startsWith(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE)) {
				try {
					ids.add(Integer.parseInt(l.substring(l.indexOf("Entity ID: ") + "Entity ID: ".length())));
				} catch (Exception ex) {}
			}
		}
		return ids;
	}
	
	public static void removeAnimal(ItemStack item, int entityID) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		while(lore.contains(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID)) {
			lore.remove(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + "" + ChatColor.BLUE + "Entity ID: " + entityID);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public static List<String> removeEnchantment(CustomEnchantment enchantment, int level, List<String> lore){
		String legacyEnchName = ChatColor.RESET + "" + ChatColor.GRAY + returnEnchantmentName(enchantment, level);
		String enchName = ChatUtils.hideText("solution") + "" + ChatColor.GRAY + returnEnchantmentName(enchantment, level);
		while(lore.contains(legacyEnchName)) {
			lore.remove(legacyEnchName);
		}
		while(lore.contains(enchName)) {
			lore.remove(enchName);
		}
		return lore;
	}
	
	public static String encodeString(String st) {
		String regex = "\\n";

		return st.replaceAll(regex, "\\\\n");
	}
	
	public static String decodeString(String st) {
		if(st == null) return st;
		StringBuilder sb = new StringBuilder(st.length());

	    for (int i = 0; i < st.length(); i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st
	                    .charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            }
	            i++;
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}
	
	public static String join(List<String> strings, String divider) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if(i < strings.size() - 1) {
				sb.append(divider);
			}
		}
		
		return sb.toString();
	}
}
