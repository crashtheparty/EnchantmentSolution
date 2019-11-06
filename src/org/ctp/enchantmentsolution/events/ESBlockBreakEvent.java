package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class ESBlockBreakEvent extends BlockBreakEvent {

	public ESBlockBreakEvent(Block theBlock, Player player) {
		super(theBlock, player);
	}

}
