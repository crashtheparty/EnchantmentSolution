package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;

public class MagmaWalkerDamageBlockEvent extends WalkerDamageBlockEvent {

	public MagmaWalkerDamageBlockEvent(Block theBlock, DamageState damage) {
		super(theBlock, damage);
	}

}
