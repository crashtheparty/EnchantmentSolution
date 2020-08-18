package org.ctp.enchantmentsolution.events.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChangeCoordsEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private final Location fromLoc, toLoc;
	private boolean cancelled;

	public PlayerChangeCoordsEvent(Player who, Location fromLoc, Location toLoc) {
		super(who);
		this.fromLoc = fromLoc;
		this.toLoc = toLoc;
	}

	public Location getFrom() {
		return fromLoc;
	}

	public Location getTo() {
		return toLoc;
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
