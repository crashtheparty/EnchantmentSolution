package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.BonusDropsEvent;

public class SmelteryBonusDropsEvent extends BonusDropsEvent {

	public SmelteryBonusDropsEvent(Player who, Collection<ItemStack> drops, int multiplyAmount) {
		super(who, drops, multiplyAmount);
	}

}
