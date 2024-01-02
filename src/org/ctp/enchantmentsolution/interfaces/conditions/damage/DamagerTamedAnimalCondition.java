package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagerTamedAnimalCondition implements DamageCondition {

	private final boolean opposite;

	public DamagerTamedAnimalCondition(boolean opposite) {
		this.opposite = opposite;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damaged instanceof Tameable && ((Tameable) damaged).getOwner() == damager) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

}
