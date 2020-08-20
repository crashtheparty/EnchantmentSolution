package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;

public class BlockDropItemAddEvent extends BlockDropItemEvent {

	public BlockDropItemAddEvent(Block block, BlockState blockState, Player player, List<Item> items) {
		super(block, blockState, player, items);
	}

}
