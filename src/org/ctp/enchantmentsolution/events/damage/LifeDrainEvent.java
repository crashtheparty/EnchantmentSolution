package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class LifeDrainEvent extends ESEntityDamageEntityEvent {

	private double healthBack;

	public LifeDrainEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double newDamage, double healthBack) {
		super(damaged, new EnchantmentLevel(CERegister.LIFE_DRAIN, level), damager, damage, newDamage);
		setHealthBack(healthBack);
	}

	public double getHealthBack() {
		return healthBack;
	}

	public void setHealthBack(double healthBack) {
		this.healthBack = healthBack;
	}

}
