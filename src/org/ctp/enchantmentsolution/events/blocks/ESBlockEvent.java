package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ESBlockEvent extends BlockEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	private boolean cancelled;
	private final EnchantmentLevel enchantment;

	public ESBlockEvent(Block theBlock, EnchantmentLevel enchantment) {
		super(theBlock);
		this.enchantment = enchantment;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}
