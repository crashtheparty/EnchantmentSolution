package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public interface EntityBlockCondition extends Condition {
	public boolean metCondition(Entity entity, Block block, EntityChangeBlockEvent event);

}
