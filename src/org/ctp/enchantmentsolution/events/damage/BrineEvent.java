package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class BrineEvent extends ESEntityDamageEntityEvent {

	public BrineEvent(LivingEntity damaged, Player damager, double damage, double newDamage) {
		super(damaged, damager, damage, newDamage);
	}
}
