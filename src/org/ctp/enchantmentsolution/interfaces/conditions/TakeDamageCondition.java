package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public interface TakeDamageCondition extends Condition {
	public boolean metCondition(Entity damaged, EntityDamageEvent event);

}
