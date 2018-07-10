package org.ctp.enchantmentsolution.utils;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;

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
}
