package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockPhysicsEvent extends ESBlockEvent {

	private final BlockData changed;
	private final Player player;

	public ESBlockPhysicsEvent(Block theBlock, BlockData changed, Player player, EnchantmentLevel enchantment) {
		super(theBlock, enchantment);
		this.changed = changed;
		this.player = player;
	}

	public BlockData getChanged() {
		return changed;
	}

	public Player getPlayer() {
		return player;
	}

}
