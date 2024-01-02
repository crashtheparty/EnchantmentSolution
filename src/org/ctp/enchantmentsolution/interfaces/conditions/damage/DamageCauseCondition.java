package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamageCauseCondition implements DamageCondition {

	private final boolean opposite;
	private final DamageCause[] causes;

	public DamageCauseCondition(boolean opposite, DamageCause... causes) {
		this.opposite = opposite;
		this.causes = causes;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		for(DamageCause cause: causes)
			if (event.getCause() == cause) return !opposite;
		return opposite;
	}

}
