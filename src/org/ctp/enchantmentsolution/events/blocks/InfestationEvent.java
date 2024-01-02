package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class InfestationEvent extends ESBlockDropOverrideItemEvent {

	public InfestationEvent(Block block, BlockData brokenData, Player player, List<ItemStack> infestationItems, List<ItemStack> originalItems, boolean override, int exp) {
		super(block, brokenData, new EnchantmentLevel(CERegister.CURSE_OF_INFESTATION, 1), player, infestationItems, originalItems, override, exp);
	}
}
