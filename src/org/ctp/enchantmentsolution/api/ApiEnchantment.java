package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;

public abstract class ApiEnchantment extends CustomEnchantment{
	
	/**
	 * Constructor for ApiEnchantment
	 * @param englishUSDisplayName - the name that shows on items for the enchantment - default language of Language.US
	 * @param fiftyConstant - the enchantability constant at level 50
	 * @param thirtyConstant - the enchantability constant at level 30
	 * @param fiftyModifier - the enchantability modifier at level 50
	 * @param thirtyModifier - the enchantability modifier at level 30
	 * @param fiftyStartLevel - the start level at level 50
	 * @param thirtyStartLevel - the start level at level 30
	 * @param fiftyMaxLevel - the max level at level 50
	 * @param thirtyMaxLevel- the max level at level 30
	 * @param weight - the weight of getting the enchantment
	 * @param englishUSDescription - the description of the enchantment - default language of Language.US
	 * 
	 * Set curses using the setCurse() method, and set if max level is 1 using the setMaxLevelOne() method
	 */
	public ApiEnchantment(String englishUSDisplayName, int fiftyConstant, int thirtyConstant, int fiftyModifier, int thirtyModifier, 
			int fiftyStartLevel, int thirtyStartLevel, int fiftyMaxLevel, int thirtyMaxLevel, 
			Weight weight, String englishUSDescription) {
		addDefaultDisplayName(englishUSDisplayName);
		setDefaultFiftyConstant(fiftyConstant);
		setDefaultThirtyConstant(thirtyConstant);
		setDefaultFiftyModifier(fiftyModifier);
		setDefaultThirtyModifier(thirtyModifier);
		setDefaultFiftyStartLevel(fiftyStartLevel);
		setDefaultThirtyStartLevel(thirtyStartLevel);
		setDefaultFiftyMaxLevel(fiftyMaxLevel);
		setDefaultThirtyMaxLevel(thirtyMaxLevel);
		setDefaultWeight(weight);
		addDefaultDescription(englishUSDescription);
	}
	
}
