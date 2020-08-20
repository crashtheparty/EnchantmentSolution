package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GoldDiggerEvent extends ESBlockDropAddItemEvent {

	private int expToDrop;

	public GoldDiggerEvent(Player player, int level, Block theBlock, BlockData blockData, List<ItemStack> goldItems, int expToDrop) {
		super(theBlock, blockData, new EnchantmentLevel(CERegister.GOLD_DIGGER, level), player, goldItems);
		setExpToDrop(expToDrop);
	}

	public int getExpToDrop() {
		return expToDrop;
	}

	public void setExpToDrop(int expToDrop) {
		this.expToDrop = expToDrop;
	}
}
