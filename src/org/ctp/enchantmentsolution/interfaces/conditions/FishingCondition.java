package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;

public interface FishingCondition extends Condition {
	public boolean metCondition(Player player, PlayerFishEvent event);
}
