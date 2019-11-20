package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESDamageEntityEvent;

public class DrownDamageEvent extends ESDamageEntityEvent {

	public DrownDamageEvent(LivingEntity damager, double damage, double newDamage) {
		super(damager, damage, newDamage);
	}

}
