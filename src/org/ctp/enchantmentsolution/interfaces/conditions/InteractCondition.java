package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface InteractCondition extends Condition {
	public boolean metCondition(Player player, PlayerInteractEvent event);
}