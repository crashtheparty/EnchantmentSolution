package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class ESBlockEvent extends BlockEvent {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public ESBlockEvent(Block theBlock) {
		super(theBlock);
	}

}
