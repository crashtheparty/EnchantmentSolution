package org.ctp.enchantmentsolution.interfaces.conditions.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;

public class HitEntityIsTypeCondition implements ProjectileHitCondition {
	
	private final boolean opposite;
	private final MobData[] entities;

	public HitEntityIsTypeCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
		this.entities = entities;
	}
	
	@Override
	public boolean metCondition(LivingEntity damaged, LivingEntity shooter, ProjectileHitEvent event) {
		if (damaged != null) for(MobData e: entities)
			if (e.getEntity() == damaged.getType()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public MobData[] getEntities() {
		return entities;
	}

}
