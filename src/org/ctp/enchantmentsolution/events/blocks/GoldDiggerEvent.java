package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GoldDiggerEvent extends ESBlockDropAddItemEvent {

	private int expToDrop;

	public GoldDiggerEvent(Player player, int level, Block theBlock, Collection<ItemStack> goldItems, int expToDrop) {
		super(theBlock, new EnchantmentLevel(CERegister.GOLD_DIGGER, level), player, goldItems);
		setExpToDrop(expToDrop);
	}

	public int getExpToDrop() {
		return expToDrop;
	}

	public void setExpToDrop(int expToDrop) {
		this.expToDrop = expToDrop;
	}
}
