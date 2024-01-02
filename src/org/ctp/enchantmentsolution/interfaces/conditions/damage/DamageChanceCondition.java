package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamageChanceCondition implements DamageCondition {

	private final double chance;

	public DamageChanceCondition(double chance) {
		this.chance = chance;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		return chance > Math.random();
	}

	public double getChance() {
		return chance;
	}

}
