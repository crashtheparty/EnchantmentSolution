package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESPlayerEvent extends PlayerEvent implements Cancellable {

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

	public ESPlayerEvent(Player who, EnchantmentLevel enchantment) {
		super(who);
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
