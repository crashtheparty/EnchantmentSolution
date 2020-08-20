package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropAddItemEvent extends ESBlockDropItemEvent {

	public ESBlockDropAddItemEvent(Block theBlock, BlockData blockData, EnchantmentLevel enchantment, Player player, List<ItemStack> items) {
		super(theBlock, blockData, enchantment, player, items);
	}

}
