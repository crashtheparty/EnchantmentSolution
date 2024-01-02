package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.crashapi.entity.MobInterface;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagedHasInterfaceCondition implements DamageCondition {

	private final boolean opposite;
	private final MobInterface[] interfaces;

	public DamagedHasInterfaceCondition(boolean opposite, MobInterface... interfaces) {
		this.opposite = opposite;
		this.interfaces = interfaces;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		for(MobInterface inter: interfaces)
			if (inter.hasInterface(damaged)) return !opposite;
		return opposite;
	}

}
