package org.ctp.enchantmentsolution.utils;

import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;

public class RomanNumerals {

	
	private static String convertToRomanNumeral(int num){
		switch(num){
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		}
		return null;
	}
	
	private static int convertToNumber(String numeral) {
		switch(numeral){
		case "I":
			return 1;
		case "II":
			return 2;
		case "III":
			return 3;
		case "IV":
			return 4;
		case "V":
			return 5;
		case "VI":
			return 6;
		case "VII":
			return 7;
		case "VIII":
			return 8;
		case "IX":
			return 9;
		case "X":
			return 10;
		}
		return 0;
	}
	
	public static String returnEnchantmentName(CustomEnchantment ench, int enchLevel){
		if(enchLevel == 1 && ench.getMaxLevel() == 1){
			return ench.getDisplayName();
		}
		String roman = convertToRomanNumeral(enchLevel);
		if(roman == null){
			return ench.getDisplayName() + " enchantment.level." + enchLevel;
		}
		
		return ench.getDisplayName() + " " + roman;
	}
	
	public static EnchantmentLevel returnEnchantmentLevel(String s, ItemMeta meta) {
		String[] pieces = s.split(" ");
		int level = 0;
		int repair = 0;
		if(pieces[pieces.length - 1].contains("enchantment.level")) {
			String[] enchLevel = pieces[pieces.length - 1].split(".");
			level = Integer.parseInt(enchLevel[enchLevel.length - 1]);
			repair = pieces.length - 1;
		}else {
			level = convertToNumber(pieces[pieces.length - 1]);
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
		for(CustomEnchantment ench : DefaultEnchantments.getAddedEnchantments()) {
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
