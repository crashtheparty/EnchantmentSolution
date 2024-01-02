package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class AnimalIsTameableCondition implements DamageCondition {
	
	private final boolean opposite;
	
	public AnimalIsTameableCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damaged instanceof Tameable) {
			Tameable tameable = (Tameable) damaged;
			if (tameable.getOwner() == null) return !opposite;
		}
		return opposite;
	}

}
