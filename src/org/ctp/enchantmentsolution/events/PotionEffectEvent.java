package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.potion.PotionEffectType;

public abstract class PotionEffectEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

    @Override
	public final HandlerList getHandlers(){
		return handlers;
	}
    
    public final static HandlerList getHandlerList(){
		return handlers;
	}
    
    private boolean cancelled;
    private PotionEffectType type;
    
	public PotionEffectEvent(Player who, PotionEffectType type) {
		super(who);
		this.setType(type);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public PotionEffectType getType() {
		return type;
	}

	public void setType(PotionEffectType type) {
		this.type = type;
	}

}
