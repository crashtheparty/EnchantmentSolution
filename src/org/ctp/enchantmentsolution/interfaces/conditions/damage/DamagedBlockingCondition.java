package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagedBlockingCondition implements DamageCondition {

	private final boolean opposite;

	public DamagedBlockingCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damaged instanceof HumanEntity) {
			HumanEntity player = (HumanEntity) damaged;
			if (player.isBlocking()) return !opposite;
		}
		return opposite;
	}

}
