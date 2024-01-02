package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class DamagerThrewEntityCondition implements DamageCondition {

	private final MobData[] data;

	public DamagerThrewEntityCondition(MobData... data) {
		this.data = data;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		EntityType type = event.getDamager().getType();
		for(MobData d: data)
			if (type == d.getEntity()) return true;
		return false;
	}

	public MobData[] getData() {
		return data;
	}

}
