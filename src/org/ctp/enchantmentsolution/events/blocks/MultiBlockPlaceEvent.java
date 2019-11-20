package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class MultiBlockPlaceEvent extends MultiBlockEvent {

	public MultiBlockPlaceEvent(Collection<Block> blocks, Player player) {
		super(blocks, player);
	}

}
