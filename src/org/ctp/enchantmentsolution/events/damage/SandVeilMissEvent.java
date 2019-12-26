package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class SandVeilMissEvent extends ESEntityDamageEntityEvent {

	private boolean particles;
	private double accuracy;

	public SandVeilMissEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, boolean particles,
	double accuracy) {
		super(damaged, new EnchantmentLevel(CERegister.SAND_VEIL, level), damager, damage, damage);
		setParticles(particles);
		setAccuracy(accuracy);
	}

	public boolean isParticles() {
		return particles;
	}

	public void setParticles(boolean particles) {
		this.particles = particles;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

}
