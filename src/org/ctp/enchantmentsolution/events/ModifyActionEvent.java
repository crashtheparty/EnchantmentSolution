package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class ModifyActionEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

    @Override
	public final HandlerList getHandlers(){
		return handlers;
	}
    
    public final static HandlerList getHandlerList(){
		return handlers;
	}
    
    private boolean cancelled;
    
	public ModifyActionEvent(Player who) {
		super(who);
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
