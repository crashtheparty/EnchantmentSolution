package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class TelepathyBlockEvent extends ESCollectBlockDropEvent {

	public TelepathyBlockEvent(Block block, BlockData data, Player player, List<ItemStack> drops) {
		super(block, data, new EnchantmentLevel(CERegister.TELEPATHY, 1), player, drops);
	}

}
