package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class SacrificeEvent extends ESEntityDamageEntityEvent {

	public SacrificeEvent(LivingEntity damaged, Player damager, double damage) {
		super(damaged, damager, 0, damage);
	}

}
