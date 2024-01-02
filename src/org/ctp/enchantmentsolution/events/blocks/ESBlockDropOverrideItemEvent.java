package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropOverrideItemEvent extends ESBlockDropItemEvent {

	private final List<ItemStack> originalItems;
	private boolean override;
	private int exp;

	public ESBlockDropOverrideItemEvent(Block theBlock, BlockData blockData, EnchantmentLevel enchantment, Player player, List<ItemStack> items,
	List<ItemStack> originalItems, boolean override, int exp) {
		super(theBlock, blockData, enchantment, player, items);
		this.originalItems = originalItems;
		this.override = override;
		this.exp = exp;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

}
