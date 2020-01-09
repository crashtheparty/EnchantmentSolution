package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockBreakEvent extends ESBlockEvent {

	private final Player player;

	public ESBlockBreakEvent(Block block, Player player, EnchantmentLevel enchantment) {
		super(block, enchantment);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
