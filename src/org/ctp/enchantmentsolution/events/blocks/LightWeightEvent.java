package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class LightWeightEvent extends ESBlockEvent {

	private final Player player;

	public LightWeightEvent(Block theBlock, Player player) {
		super(theBlock, new EnchantmentLevel(CERegister.LIGHT_WEIGHT, 1));
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
