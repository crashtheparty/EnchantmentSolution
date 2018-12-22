package org.ctp.enchantmentsolution.inventory;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryData {

	public Player getPlayer();
	
	public Block getBlock();
		
	public void close(boolean external);
	
	public Inventory getInventory();

	public void setInventory(List<ItemStack> items);

	public void setItemName(String name);
	
}
