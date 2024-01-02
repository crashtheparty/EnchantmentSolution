package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public interface McMMOCondition extends Condition {
	public boolean metCondition(Player player, ItemStack item, Event event);
}
