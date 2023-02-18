package org.ctp.enchantmentsolution.events.generic;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.events.blocks.WalkerDamageBlockEvent;

public class GenericWalkerDamageBlockEvent extends WalkerDamageBlockEvent {

	public GenericWalkerDamageBlockEvent(Block theBlock, EnchantmentLevel enchantment, DamageState damage) {
		super(theBlock, enchantment, damage);
	}

}
