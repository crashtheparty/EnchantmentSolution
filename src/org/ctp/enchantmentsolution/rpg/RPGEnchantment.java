package org.ctp.enchantmentsolution.rpg;

import org.bukkit.enchantments.Enchantment;

public class RPGEnchantment {

	private final Enchantment enchantment;
	private final int level;
	
	public RPGEnchantment(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public int getLevel() {
		return level;
	}
	
}
