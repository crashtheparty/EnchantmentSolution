package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class WalkerDamageBlockEvent extends ESBlockEvent {

	private final DamageState damage;

	public WalkerDamageBlockEvent(Block theBlock, EnchantmentLevel enchantment, DamageState damage) {
		super(theBlock, enchantment);
		this.damage = damage;
	}

	public DamageState getDamage() {
		return damage;
	}

}
