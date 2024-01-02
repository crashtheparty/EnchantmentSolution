package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class HitEntityExistsCondition implements ProjectileHitCondition {

	private final boolean opposite;

	public HitEntityExistsCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(LivingEntity damaged, LivingEntity shooter, ProjectileHitEvent event) {
		if (damaged != null) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

}
