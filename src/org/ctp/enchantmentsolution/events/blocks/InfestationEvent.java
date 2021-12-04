package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class InfestationEvent extends ESBlockDropAddItemEvent {

	public InfestationEvent(Player player, Block theBlock, BlockData blockData, List<ItemStack> infestationItems) {
		super(theBlock, blockData, new EnchantmentLevel(CERegister.CURSE_OF_INFESTATION, 1), player, infestationItems);
	}
}
