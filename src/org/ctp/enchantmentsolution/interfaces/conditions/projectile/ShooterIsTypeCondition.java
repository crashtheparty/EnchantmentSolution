package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class ShooterIsTypeCondition implements ProjectileHitCondition {

	private final boolean opposite;
	private final MobData[] entities;

	public ShooterIsTypeCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
		this.entities = entities;
	}

	@Override
	public boolean metCondition(LivingEntity shooter, LivingEntity damaged, ProjectileHitEvent event) {
		if (shooter == null) return opposite;
		for(MobData data: entities)
			if (data.getEntity() == shooter.getType()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public MobData[] getEntities() {
		return entities;
	}

}