package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ItemAddEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private final ItemStack item;
	
	public ItemAddEvent(Player who, ItemStack item) {
		super(who);
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}
}
