package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;

public class WalkerDamageBlockEvent extends ESBlockEvent {

	private final DamageState damage;

	public WalkerDamageBlockEvent(Block theBlock, DamageState damage) {
		super(theBlock);
		this.damage = damage;
	}

	public DamageState getDamage() {
		return damage;
	}

}
