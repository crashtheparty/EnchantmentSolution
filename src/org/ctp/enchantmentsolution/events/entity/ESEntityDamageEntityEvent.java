package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ESEntityDamageEntityEvent extends ESDamageEntityEvent {

	private final LivingEntity damager;

	public ESEntityDamageEntityEvent(LivingEntity damaged, EnchantmentLevel enchantment, LivingEntity damager,
	double damage, double newDamage) {
		super(damaged, enchantment, damage, newDamage);
		this.damager = damager;
	}

	public LivingEntity getDamager() {
		return damager;
	}

}
