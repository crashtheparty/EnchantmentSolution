package org.ctp.enchantmentsolution.events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class DropEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private final List<ItemStack> originalDrops;
	private List<ItemStack> newDrops;
	private boolean override, cancelled;

	public DropEvent(Player who, List<ItemStack> newDrops, List<ItemStack> originalDrops, boolean override) {
		super(who);
		this.originalDrops = originalDrops;
		setNewDrops(newDrops);
		setOverride(override);
	}

	public List<ItemStack> getOriginalDrops() {
		return originalDrops;
	}

	public List<ItemStack> getNewDrops() {
		return newDrops;
	}

	public void setNewDrops(List<ItemStack> newDrops) {
		this.newDrops = newDrops;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
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
