package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GoldDiggerEvent extends ESBlockDropOverrideItemEvent {

	public GoldDiggerEvent(Block block, BlockData blockData, Player player, int level, List<ItemStack> newDrops, List<ItemStack> oldDrops, boolean override,
	int exp) {
		super(block, blockData, new EnchantmentLevel(CERegister.SMELTERY, level), player, newDrops, oldDrops, override, exp);
	}

}
