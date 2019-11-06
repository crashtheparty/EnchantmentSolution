package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.ModifyBlockEvent;

public class GoldDiggerEvent extends ModifyBlockEvent implements Cancellable {

	private Block block;
	private ItemStack goldItem;
	private int expToDrop;
	private boolean cancelled;

	public GoldDiggerEvent(Player player, Block block, ItemStack goldItem, int expToDrop) {
		super(player);
		setBlock(block);
		setGoldItem(goldItem);
		setExpToDrop(expToDrop);
	}

	public ItemStack getGoldItem() {
		return goldItem;
	}

	public void setGoldItem(ItemStack goldItem) {
		this.goldItem = goldItem;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public int getExpToDrop() {
		return expToDrop;
	}

	public void setExpToDrop(int expToDrop) {
		this.expToDrop = expToDrop;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
