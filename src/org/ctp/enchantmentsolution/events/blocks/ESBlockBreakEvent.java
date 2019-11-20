package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class ESBlockBreakEvent extends ESBlockEvent {

	private final Player player;

	public ESBlockBreakEvent(Block block, Player player) {
		super(block);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
