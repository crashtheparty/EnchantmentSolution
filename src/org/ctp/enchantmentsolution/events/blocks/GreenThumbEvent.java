package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GreenThumbEvent extends ESBlockDropOverrideItemEvent {

	private final ItemStack seed;

	public GreenThumbEvent(Block theBlock, BlockData blockData, Player player, List<ItemStack> items, List<ItemStack> overrideItems, ItemStack seed) {
		super(theBlock, blockData, new EnchantmentLevel(CERegister.GREEN_THUMB, 1), player, items, overrideItems);
		this.seed = seed;
	}

	public ItemStack getSeed() {
		return seed;
	}

}
