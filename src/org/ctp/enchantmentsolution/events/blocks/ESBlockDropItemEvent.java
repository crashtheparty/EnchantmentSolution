package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropItemEvent extends ESBlockEvent {

	private final Collection<ItemStack> items;
	private final Player player;
	
	public ESBlockDropItemEvent(Block theBlock, EnchantmentLevel enchantment, Player player, Collection<ItemStack> items) {
		super(theBlock, enchantment);
		this.items = items;
		this.player = player;
	}

	public Collection<ItemStack> getItems() {
		return items;
	}

	public Player getPlayer() {
		return player;
	}

}
