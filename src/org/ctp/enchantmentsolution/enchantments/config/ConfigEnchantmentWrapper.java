package org.ctp.enchantmentsolution.enchantments.config;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;

public class ConfigEnchantmentWrapper extends CustomEnchantmentWrapper {

	ConfigEnchantmentWrapper(String namespace, String name) {
		super(EnchantmentSolution.getPlugin(), namespace, name);
	}

}