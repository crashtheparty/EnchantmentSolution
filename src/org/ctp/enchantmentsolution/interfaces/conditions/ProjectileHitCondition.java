package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface ProjectileHitCondition extends ProjectileCondition {

	public boolean metCondition(LivingEntity damaged, LivingEntity shooter, ProjectileHitEvent event);
}
