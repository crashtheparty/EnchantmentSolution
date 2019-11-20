package org.ctp.enchantmentsolution.events.blocks;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class MultiBlockEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private Collection<Block> blocks;
	private final Player player;
	private boolean cancelled;

	public MultiBlockEvent(Collection<Block> blocks, Player player) {
		this.blocks = blocks;
		this.player = player;
	}

	public Collection<Block> getBlocks() {
		return blocks;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
