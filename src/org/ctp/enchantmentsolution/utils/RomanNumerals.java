package org.ctp.enchantmentsolution.utils;

import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;

public class RomanNumerals {
	
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
}
