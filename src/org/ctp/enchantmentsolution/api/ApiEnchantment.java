package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;

public abstract class ApiEnchantment extends CustomEnchantment{
	
	/**
	 * Constructor for ApiEnchantment
	 * @param displayName - the name that shows on items for the enchantment
	 * @param fiftyConstant - the enchantability constant at level 50
	 * @param thirtyConstant - the enchantability constant at level 30
	 * @param fiftyModifier - the enchantability modifier at level 50
	 * @param thirtyModifier - the enchantability modifier at level 30
	 * @param fiftyMaxConstant - the enchantability increase from min to max at level 50
	 * @param thirtyMaxConstant - the enchantability increase from min to max at level 50
	 * @param fiftyStartLevel - the start level at level 50
	 * @param thirtyStartLevel - the start level at level 30
	 * @param fiftyMaxLevel - the max level at level 50
	 * @param thirtyMaxLevel- the max level at level 30
	 * @param weight - the weight of getting the enchantment
	 * @param description - the description (in english) of the enchantment
	 * 
	 * Set curses using the setCurse() method, and set if max level is one using the setMaxLevelOne() method
	 */
	public ApiEnchantment(String displayName, int fiftyConstant, int thirtyConstant, int fiftyModifier, int thirtyModifier, 
			int fiftyMaxConstant, int thirtyMaxConstant, int fiftyStartLevel, int thirtyStartLevel, int fiftyMaxLevel, int thirtyMaxLevel, 
			Weight weight, String description) {
		setDefaultDisplayName(displayName);
		setDefaultFiftyConstant(fiftyConstant);
		setDefaultThirtyConstant(thirtyConstant);
		setDefaultFiftyModifier(fiftyModifier);
		setDefaultThirtyModifier(thirtyModifier);
		setDefaultFiftyMaxConstant(fiftyMaxConstant);
		setDefaultThirtyMaxConstant(thirtyMaxConstant);
		setDefaultFiftyStartLevel(fiftyStartLevel);
		setDefaultThirtyStartLevel(thirtyStartLevel);
		setDefaultFiftyMaxLevel(fiftyMaxLevel);
		setDefaultThirtyMaxLevel(thirtyMaxLevel);
		setDefaultWeight(weight);
		setDefaultDescription(description);
	}
	
}
