package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class StreakDeathEvent extends ESEntityDeathEvent {

	private final LivingEntity killer;

	public StreakDeathEvent(LivingEntity who, LivingEntity killer) {
		super(who, new EnchantmentLevel(CERegister.STREAK, 1));
		this.killer = killer;
	}

	public LivingEntity getKiller() {
		return killer;
	}

	@Override
	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}

}
