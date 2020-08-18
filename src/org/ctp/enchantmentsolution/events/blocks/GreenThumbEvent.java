package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GreenThumbEvent extends ESBlockDropOverrideItemEvent {

	private final ItemStack seed;
	
	public GreenThumbEvent(Block theBlock, Player player, Collection<ItemStack> items, Collection<ItemStack> overrideItems, ItemStack seed) {
		super(theBlock, new EnchantmentLevel(CERegister.GREEN_THUMB, 1), player, items, overrideItems);
		this.seed = seed;
	}

	public ItemStack getSeed() {
		return seed;
	}

}
