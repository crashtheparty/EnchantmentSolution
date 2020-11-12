package org.ctp.enchantmentsolution.events.blocks;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class MultiBlockEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private List<Location> blocks;
	private final Player player;
	private boolean cancelled;
	private final EnchantmentLevel enchantment;

	public MultiBlockEvent(List<Location> blocks, Player player, EnchantmentLevel enchantment) {
		this.blocks = blocks;
		this.player = player;
		this.enchantment = enchantment;
	}

	public List<Location> getBlocks() {
		return blocks;
	}

	public Player getPlayer() {
		return player;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
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
