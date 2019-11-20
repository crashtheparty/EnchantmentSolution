package org.ctp.enchantmentsolution.events.soul;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.SoulEvent;

public class SoulboundEvent extends SoulEvent {

	private final List<ItemStack> savedItems;

	public SoulboundEvent(Player who, List<ItemStack> savedItems) {
		super(who);

		this.savedItems = savedItems;
	}

	public List<ItemStack> getSavedItems() {
		return savedItems;
	}
}
