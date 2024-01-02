package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SmelteryEvent extends ESBlockDropOverrideItemEvent {

	public SmelteryEvent(Block block, BlockData blockData, Player player, List<ItemStack> newDrops, List<ItemStack> oldDrops, boolean override, int exp) {
		super(block, blockData, new EnchantmentLevel(CERegister.SMELTERY, 1), player, newDrops, oldDrops, override, exp);
	}
}
