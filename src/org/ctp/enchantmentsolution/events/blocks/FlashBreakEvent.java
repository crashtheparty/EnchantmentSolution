package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FlashBreakEvent extends ESBlockBreakEvent {

	public FlashBreakEvent(Block block, Player player) {
		super(block, player, new EnchantmentLevel(CERegister.FLASH, 1));
	}

}
