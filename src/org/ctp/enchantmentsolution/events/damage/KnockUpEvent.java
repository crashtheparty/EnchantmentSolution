package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class KnockUpEvent extends ESEntityDamageEntityEvent {

	private double knockUp;

	public KnockUpEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double knockUp) {
		super(damaged, new EnchantmentLevel(CERegister.KNOCKUP, level), damager, damage, damage);
		setKnockUp(knockUp);
	}

	public double getKnockUp() {
		return knockUp;
	}

	public void setKnockUp(double knockUp) {
		this.knockUp = knockUp;
	}
}
