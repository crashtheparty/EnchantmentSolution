package org.ctp.enchantmentsolution.events;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class GoldDiggerEvent extends PlayerEvent implements Cancellable, NaturalDropable{

	private static final HandlerList handlers = new HandlerList();
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private boolean cancelled, dropNaturally;
    private int exp;
    private Collection<ItemStack> drops;
    private Location location;
	
	public GoldDiggerEvent(Player who, int exp, Collection<ItemStack> drops, Location location) {
		super(who);
		this.exp = exp;
		this.drops = drops;
		this.location = location;
		this.dropNaturally = ConfigUtils.dropNaturally();
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public boolean willDropNaturally() {
		return dropNaturally;
	}

	public void setDropNaturally(boolean dropNaturally) {
		this.dropNaturally = dropNaturally;
	}
}
