package org.ctp.enchantmentsolution.crashapi.item;

import org.bukkit.inventory.ItemStack;

public class ItemSlot {

	private final ItemStack item;
	private final ItemSlotType type;
	
	public ItemSlot(ItemStack item, ItemSlotType type) {
		this.item = item;
		this.type = type;
	}

	public ItemStack getItem() {
		return item;
	}

	public ItemSlotType getType() {
		return type;
	}
	
}
