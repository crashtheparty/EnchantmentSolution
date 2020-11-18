package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESBlockDropItemEvent extends ESBlockEvent {

	private final List<ItemStack> items;
	private final Player player;
	private final BlockData blockData;

	public ESBlockDropItemEvent(Block theBlock, BlockData blockData, EnchantmentLevel enchantment, Player player, List<ItemStack> items) {
		super(theBlock, enchantment);
		this.blockData = blockData;
		this.items = items;
		this.player = player;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public Player getPlayer() {
		return player;
	}

	public BlockData getBlockData() {
		return blockData;
	}

}
