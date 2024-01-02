package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class PreventCauseEvent extends ESEntityEvent {

	private final DamageCause cause;
	
	public PreventCauseEvent(LivingEntity who, EnchantmentLevel enchantment, DamageCause cause) {
		super(who, enchantment);
		this.cause = cause;
	}

	public DamageCause getCause() {
		return cause;
	}

}
