package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class LightWeightEvent extends ESBlockEvent {

	private final HumanEntity player;

	public LightWeightEvent(Block theBlock, HumanEntity player) {
		super(theBlock, new EnchantmentLevel(CERegister.LIGHT_WEIGHT, 1));
		this.player = player;
	}

	public HumanEntity getPlayer() {
		return player;
	}

}
