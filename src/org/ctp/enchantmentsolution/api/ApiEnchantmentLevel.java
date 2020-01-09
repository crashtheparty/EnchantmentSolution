package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ApiEnchantmentLevel extends EnchantmentLevel {

	/**
	 * Constructor for ApiEnchantmentLevel
	 * 
	 * @param enchant
	 *            - the custom enchantment
	 * @param level
	 *            - the level of the enchantment
	 */
	public ApiEnchantmentLevel(ApiEnchantment enchant, int level) {
		super(enchant, level);
	}

}
