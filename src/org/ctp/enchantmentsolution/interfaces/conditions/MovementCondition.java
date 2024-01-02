package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;

public interface MovementCondition extends Condition {
	public boolean metCondition(Player player, PlayerChangeCoordsEvent event);
}