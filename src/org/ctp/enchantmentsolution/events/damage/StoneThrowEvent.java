package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class StoneThrowEvent extends ESEntityDamageEntityEvent {

	public StoneThrowEvent(LivingEntity damaged, LivingEntity damager, double damage, double newDamage) {
		super(damaged, damager, damage, newDamage);
	}

}
