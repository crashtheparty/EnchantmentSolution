package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;

public interface BlockCondition extends Condition {
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event);
}
