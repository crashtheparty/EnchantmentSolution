package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ExpShareEvent extends PlayerEvent implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private boolean cancelled;
    private int originalExp, newExp;
	
	public ExpShareEvent(Player who, int originalExp, int newExp) {
		super(who);
		this.originalExp = originalExp;
		this.newExp = newExp;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getOriginalExp() {
		return originalExp;
	}

	public int getNewExp() {
		return newExp;
	}

	public void setNewExp(int newExp) {
		this.newExp = newExp;
	}
}
