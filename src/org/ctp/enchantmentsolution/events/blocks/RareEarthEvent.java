package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class RareEarthEvent extends ESBlockDropOverrideItemEvent {

	public RareEarthEvent(Block block, BlockData blockData, Player player, int level, List<ItemStack> rareEarthItems, List<ItemStack> originalItems, boolean override, int exp) {
		super(block, blockData, new EnchantmentLevel(CERegister.RARE_EARTH, level), player, rareEarthItems, originalItems, override, exp);
	}
}
