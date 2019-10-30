package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class GungHoEvent extends ESEntityDamageEntityEvent {

	public GungHoEvent(LivingEntity damaged, Player damager, double damage, double newDamage) {
		super(damaged, damager, damage, newDamage);
	}
}
