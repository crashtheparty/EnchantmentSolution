package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class RareEarthEvent extends ESBlockDropAddItemEvent {

	public RareEarthEvent(Player player, int level, Block theBlock, BlockData blockData, List<ItemStack> rareEarthItems) {
		super(theBlock, blockData, new EnchantmentLevel(CERegister.RARE_EARTH, level), player, rareEarthItems);
	}
}
