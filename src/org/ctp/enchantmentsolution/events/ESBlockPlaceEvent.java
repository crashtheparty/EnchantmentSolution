package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public abstract class ESBlockPlaceEvent extends BlockPlaceEvent {

	public ESBlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst,
			ItemStack itemInHand, Player thePlayer, boolean canBuild, EquipmentSlot hand) {
		super(placedBlock, replacedBlockState, placedAgainst, itemInHand, thePlayer, canBuild, hand);
	}

}
