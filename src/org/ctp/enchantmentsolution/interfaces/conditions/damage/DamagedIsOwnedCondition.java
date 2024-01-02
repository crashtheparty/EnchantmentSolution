package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagedIsOwnedCondition implements DamageCondition {

	private final boolean opposite, allowUnowned;

	public DamagedIsOwnedCondition(boolean opposite, boolean allowUnowned) {
		this.opposite = opposite;
		this.allowUnowned = allowUnowned;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damaged instanceof Tameable) {
			Tameable tameable = (Tameable) damaged;
			if (tameable.getOwner() == damager) return !opposite;
			else
				return allowUnowned;
		}
		return opposite;
	}

}
