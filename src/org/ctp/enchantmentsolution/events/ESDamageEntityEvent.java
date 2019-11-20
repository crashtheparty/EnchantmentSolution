package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public abstract class ESDamageEntityEvent extends EntityEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private double damage, newDamage;
	private boolean cancelled;

	public ESDamageEntityEvent(LivingEntity damaged, double damage, double newDamage) {
		super(damaged);
		this.damage = damage;
		setNewDamage(newDamage);
	}

	public double getDamage() {
		return damage;
	}

	public double getNewDamage() {
		return newDamage;
	}

	public void setNewDamage(double newDamage) {
		this.newDamage = newDamage;
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
