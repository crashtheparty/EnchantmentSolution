package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class KnockUpEvent extends ESEntityDamageEntityEvent {

	private double knockUp;

	public KnockUpEvent(LivingEntity damaged, LivingEntity damager, double damage, double knockUp) {
		super(damaged, damager, damage, damage);
		setKnockUp(knockUp);
	}

	public double getKnockUp() {
		return knockUp;
	}

	public void setKnockUp(double knockUp) {
		this.knockUp = knockUp;
	}
}
