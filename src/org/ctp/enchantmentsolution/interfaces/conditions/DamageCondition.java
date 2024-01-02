package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface DamageCondition extends Condition {
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event);
}
