package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagedIsTypeCondition implements DamageCondition {

	private final boolean opposite;
	private final MobData[] entities;

	public DamagedIsTypeCondition(boolean opposite, MobData... entities) {
		this.opposite = opposite;
		this.entities = entities;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		for(MobData entity: entities)
			if (entity.getEntity() == damaged.getType()) return !opposite;
		return opposite;
	}

	public boolean getOpposite() {
		return opposite;
	}

	public MobData[] getEntities() {
		return entities;
	}

}
