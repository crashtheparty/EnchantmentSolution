package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESCollectBlockDropEvent extends ESBlockEvent {

	private final Player player;
	private final List<ItemStack> items;
	private final BlockData blockData;

	public ESCollectBlockDropEvent(Block theBlock, BlockData blockData, EnchantmentLevel enchantment, Player player, List<ItemStack> items) {
		super(theBlock, enchantment);
		this.player = player;
		this.items = items;
		this.blockData = blockData;
	}

	public Player getPlayer() {
		return player;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public BlockData getBlockData() {
		return blockData;
	}

}
