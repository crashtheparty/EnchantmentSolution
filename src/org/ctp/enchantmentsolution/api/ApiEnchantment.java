package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;

public abstract class ApiEnchantment extends CustomEnchantment{
	
	public ApiEnchantment(String displayName, int fiftyConstant, int thirtyConstant, int fiftyModifier, int thirtyModifier, 
			int fiftyMaxConstant, int thirtyMaxConstant, int fiftyStartLevel, int thirtyStartLevel, int fiftyMaxLevel, int thirtyMaxLevel, Weight weight) {
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
	}
	
}
