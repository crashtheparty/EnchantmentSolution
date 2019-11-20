package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public abstract class WalkerBlockEvent extends ESBlockPlaceEvent {

	public WalkerBlockEvent(Block placedBlock, BlockState replacedBlockState, Player thePlayer) {
		super(placedBlock, replacedBlockState, null, null, thePlayer, true, EquipmentSlot.FEET);
	}

}
