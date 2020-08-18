package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropOverrideItemEvent extends ESBlockDropItemEvent {

	private final Collection<ItemStack> originalItems;
	private boolean override = true;
	
	public ESBlockDropOverrideItemEvent(Block theBlock, EnchantmentLevel enchantment, Player player, Collection<ItemStack> items, Collection<ItemStack> originalItems) {
		super(theBlock, enchantment, player, items);
		this.originalItems = originalItems;
	}

	public Collection<ItemStack> getOriginalItems() {
		return originalItems;
	}

	public boolean willOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}
