package org.ctp.enchantmentsolution.events;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class BonusDropsEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private boolean cancelled;
	private Collection<ItemStack> drops;
	private int multiplyAmount;

	public BonusDropsEvent(Player who, Collection<ItemStack> drops, int multiplyAmount) {
		super(who);
		setDrops(drops);
		setMultiplyAmount(multiplyAmount);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public void setDrops(Collection<ItemStack> drops) {
		this.drops = drops;
	}

	public int getMultiplyAmount() {
		return multiplyAmount;
	}

	public void setMultiplyAmount(int multiplyAmount) {
		this.multiplyAmount = multiplyAmount;
	}

}
