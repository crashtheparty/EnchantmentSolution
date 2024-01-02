package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Entity;

public interface EquipCondition extends Condition {
	public boolean metCondition(Entity entity);
}
