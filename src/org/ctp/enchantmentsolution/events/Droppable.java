package org.ctp.enchantmentsolution.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface Droppable {
	
	public void setDropLocation(Location loc);
	
	public Location getDropLocation();
	
	public List<ItemStack> getDrops();
	
	public int getExp();
	
	public void setExp(int exp);

}
