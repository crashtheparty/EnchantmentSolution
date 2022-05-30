package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class WhippedTameEvent extends ESEntityDamageEntityEvent {

	public WhippedTameEvent(LivingEntity damaged, int level, LivingEntity damager, double damage) {
		super(damaged, new EnchantmentLevel(CERegister.WHIPPED, level), damager, damage, 0);
	}

}
