package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ESDamageEntityEvent extends ESEntityEvent {

	private double damage, newDamage;

	public ESDamageEntityEvent(LivingEntity damaged, EnchantmentLevel enchantment, double damage, double newDamage) {
		super(damaged, enchantment);
		this.damage = damage;
		setNewDamage(newDamage);
	}

	public double getDamage() {
		return damage;
	}

	public double getNewDamage() {
		return newDamage;
	}

	public void setNewDamage(double newDamage) {
		this.newDamage = newDamage;
	}
}
