package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class HeightWidthEvent extends MultiBlockBreakEvent {

	public HeightWidthEvent(Collection<Block> blocks, Player player) {
		super(blocks, player);
	}

}
