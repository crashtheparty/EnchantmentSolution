package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class LassoDamageEvent extends ESEntityDamageEntityEvent {

	public LassoDamageEvent(LivingEntity damaged, Player damager) {
		super(damaged, damager, 0, 0);
	}

}
