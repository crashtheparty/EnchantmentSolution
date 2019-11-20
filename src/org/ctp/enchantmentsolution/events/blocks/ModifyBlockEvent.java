package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ModifyBlockEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	public ModifyBlockEvent(Player who) {
		super(who);
	}
}
