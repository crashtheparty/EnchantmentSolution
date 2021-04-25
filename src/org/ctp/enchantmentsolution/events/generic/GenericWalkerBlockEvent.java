package org.ctp.enchantmentsolution.events.generic;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.blocks.WalkerBlockEvent;

public class GenericWalkerBlockEvent extends WalkerBlockEvent {

	public GenericWalkerBlockEvent(Block placedBlock, BlockState replacedBlockState, Player player, EnchantmentLevel enchantment) {
		super(placedBlock, replacedBlockState, player, enchantment);
	}

}
