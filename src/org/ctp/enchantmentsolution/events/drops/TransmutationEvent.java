package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.DropEvent;

public class TransmutationEvent extends DropEvent {

	public TransmutationEvent(Player who, List<ItemStack> newDrops, List<ItemStack> originalDrops, boolean override) {
		super(who, newDrops, originalDrops, override);
	}

}
