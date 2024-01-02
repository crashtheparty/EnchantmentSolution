package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public interface ItemBreakCondition extends Condition {
	public boolean metCondition(Player player, ItemStack item, PlayerItemBreakEvent event);

}
