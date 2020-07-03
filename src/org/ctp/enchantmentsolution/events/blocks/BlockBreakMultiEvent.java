package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakMultiEvent extends BlockBreakEvent {

	public BlockBreakMultiEvent(Block theBlock, Player player) {
		super(theBlock, player);
	}

}
