package org.ctp.enchantmentsolution.events;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class TelepathyDropEvent extends PlayerEvent implements NaturalDropable{
	
	private static final HandlerList handlers = new HandlerList();
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private boolean drop, dropNaturally;
    private Collection<ItemStack> drops;
    private int experience;
    private Location dropLocation;
    
	public TelepathyDropEvent(Player who, Collection<ItemStack> drops, int experience, Location dropLocation) {
		super(who);
		this.drops = drops;
		this.experience = experience;
		this.dropLocation = dropLocation;
		this.dropNaturally = ConfigUtils.dropNaturally();
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}

	public boolean willDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}

	public boolean willDropNaturally() {
		return dropNaturally;
	}

	public void setDropNaturally(boolean dropNaturally) {
		this.dropNaturally = dropNaturally;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public Location getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(Location dropLocation) {
		this.dropLocation = dropLocation;
	}
}
