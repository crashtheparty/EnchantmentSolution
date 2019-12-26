package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class VoidWalkerBlockEvent extends WalkerBlockEvent {

	public VoidWalkerBlockEvent(Block placedBlock, BlockState replacedBlockState, Player thePlayer, int level) {
		super(placedBlock, replacedBlockState, thePlayer, new EnchantmentLevel(CERegister.VOID_WALKER, level));
	}

}
