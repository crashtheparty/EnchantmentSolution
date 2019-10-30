package org.ctp.enchantmentsolution.events.soul;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.SoulEvent;

public class SoulReaperEvent extends SoulEvent {

	private final List<ItemStack> reapedItems;
	
	public SoulReaperEvent(Player who, List<ItemStack> reapedItems) {
		super(who);
		
		this.reapedItems = reapedItems;
	}

	public List<ItemStack> getReapedItems() {
		return reapedItems;
	}
}
