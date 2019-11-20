package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class MagmaWalkerBlockEvent extends WalkerBlockEvent {

	public MagmaWalkerBlockEvent(Block placedBlock, BlockState replacedBlockState, Player thePlayer) {
		super(placedBlock, replacedBlockState, thePlayer);
	}

}
