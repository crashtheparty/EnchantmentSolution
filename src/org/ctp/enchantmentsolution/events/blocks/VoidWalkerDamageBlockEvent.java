package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class VoidWalkerDamageBlockEvent extends WalkerDamageBlockEvent {

	public VoidWalkerDamageBlockEvent(Block theBlock, DamageState damage) {
		super(theBlock, new EnchantmentLevel(CERegister.VOID_WALKER, 1), damage);
	}

}
