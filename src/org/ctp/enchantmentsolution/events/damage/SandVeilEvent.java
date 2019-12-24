package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class SandVeilEvent extends ESEntityDamageEntityEvent {

	private int ticks;
	private double accuracy;

	public SandVeilEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double newDamage,
	int ticks, double accuracy) {
		super(damaged, new EnchantmentLevel(CERegister.SAND_VEIL, level), damager, damage, newDamage);
		setTicks(ticks);
		setAccuracy(accuracy);
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

}
