package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MagmaWalkerDamageBlockEvent extends WalkerDamageBlockEvent {

	public MagmaWalkerDamageBlockEvent(Block theBlock, DamageState damage) {
		super(theBlock, new EnchantmentLevel(CERegister.MAGMA_WALKER, 1), damage);
	}

}
