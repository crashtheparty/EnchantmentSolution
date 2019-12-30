package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class GoldDiggerEvent extends ModifyBlockEvent {

	private ItemStack goldItem;
	private int expToDrop;

	public GoldDiggerEvent(Player player, int level, Block block, ItemStack goldItem, int expToDrop) {
		super(player, new EnchantmentLevel(CERegister.GOLD_DIGGER, level), block);
		setGoldItem(goldItem);
		setExpToDrop(expToDrop);
	}

	public ItemStack getGoldItem() {
		return goldItem;
	}

	public void setGoldItem(ItemStack goldItem) {
		this.goldItem = goldItem;
	}

	public int getExpToDrop() {
		return expToDrop;
	}

	public void setExpToDrop(int expToDrop) {
		this.expToDrop = expToDrop;
	}
}
