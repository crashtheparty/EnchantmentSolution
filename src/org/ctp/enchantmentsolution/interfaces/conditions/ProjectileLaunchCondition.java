package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface ProjectileLaunchCondition extends ProjectileCondition {

	public boolean metCondition(LivingEntity entity, ProjectileLaunchEvent event);
}
