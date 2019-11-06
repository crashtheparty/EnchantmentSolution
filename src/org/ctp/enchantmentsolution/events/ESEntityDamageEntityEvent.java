package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.LivingEntity;

public abstract class ESEntityDamageEntityEvent extends ESDamageEntityEvent {

	private LivingEntity damager;

	public ESEntityDamageEntityEvent(LivingEntity damaged, LivingEntity damager, double damage, double newDamage) {
		super(damaged, damage, newDamage);
		this.damager = damager;
	}

	public LivingEntity getDamager() {
		return damager;
	}

}
