package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamageEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private boolean cancelled;
	private final ItemStack item;
	private final int oldDamage;
	private int newDamage;

	public ItemDamageEvent(Player who, ItemStack item, int oldDamage, int newDamage) {
		super(who);
		this.item = item;
		this.oldDamage = oldDamage;
		setNewDamage(newDamage);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getOldDamage() {
		return oldDamage;
	}

	public int getNewDamage() {
		return newDamage;
	}

	public void setNewDamage(int newDamage) {
		this.newDamage = newDamage;
	}

}
