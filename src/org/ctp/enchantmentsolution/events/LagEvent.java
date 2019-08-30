package org.ctp.enchantmentsolution.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LagEvent extends PlayerEvent implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private List<LagParticle> particles = new ArrayList<LagParticle>();
    private Location location;
    private Sound sound;
    private boolean cancelled;
    
	public LagEvent(Player who, List<LagParticle> particles, Location location, Sound sound) {
		super(who);
		this.particles = particles;
		setLocation(location);
		setSound(sound);
	}

	public List<LagParticle> getParticles() {
		return particles;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
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
