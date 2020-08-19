package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropAddItemEvent extends ESBlockDropItemEvent {

	public ESBlockDropAddItemEvent(Block theBlock, EnchantmentLevel enchantment, Player player, Collection<ItemStack> items) {
		super(theBlock, enchantment, player, items);
	}

}
