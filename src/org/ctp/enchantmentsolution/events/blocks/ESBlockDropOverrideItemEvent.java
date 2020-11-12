package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropOverrideItemEvent extends ESBlockDropItemEvent {

	private final List<ItemStack> originalItems;
	private boolean override = true;

	public ESBlockDropOverrideItemEvent(Block theBlock, BlockData blockData, EnchantmentLevel enchantment, Player player, List<ItemStack> items,
	List<ItemStack> originalItems) {
		super(theBlock, blockData, enchantment, player, items);
		this.originalItems = originalItems;
	}

	public List<ItemStack> getOriginalItems() {
		return originalItems;
	}

	public boolean willOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}
