package org.ctp.enchantmentsolution.utils;

import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class StringUtils {
	
	private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel){
	    if(enchLevel == 1 && ench.getMaxLevel() == 1){
	        return ench.getDisplayName();
	    }
	    if(enchLevel > 10 || enchLevel <= 0){
	        return ench.getDisplayName() + " enchantment.level." + enchLevel;
	    }
	 
	    return ench.getDisplayName() + " " + NUMERALS[enchLevel- 1];
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
}
