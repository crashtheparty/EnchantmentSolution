package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class DrownedEvent extends ESEntityDamageEntityEvent {

	private boolean applyEffect;
	private int ticks;

	public DrownedEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double newDamage,
	boolean applyEffect, int ticks) {
		super(damaged, new EnchantmentLevel(CERegister.DROWNED, level), damager, damage, newDamage);
		setApplyEffect(applyEffect);
		setTicks(ticks);
	}

	public boolean willApplyEffect() {
		return applyEffect;
	}

	public void setApplyEffect(boolean applyEffect) {
		this.applyEffect = applyEffect;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
}
