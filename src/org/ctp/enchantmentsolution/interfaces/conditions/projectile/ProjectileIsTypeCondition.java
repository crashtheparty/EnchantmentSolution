package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class ProjectileIsTypeCondition implements ProjectileHitCondition {

	private final boolean opposite;
	private final MobData[] entities;

	public ProjectileIsTypeCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
		this.entities = entities;
	}

	@Override
	public boolean metCondition(LivingEntity entity, LivingEntity shooter, ProjectileHitEvent event) {
		for(MobData data: entities)
			if (data.getEntity() == event.getEntity().getType()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public MobData[] getEntities() {
		return entities;
	}

}
