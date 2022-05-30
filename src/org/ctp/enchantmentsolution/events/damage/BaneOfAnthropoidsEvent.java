package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class BaneOfAnthropoidsEvent extends ESEntityDamageEntityEvent {

	public BaneOfAnthropoidsEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double newDamage) {
		super(damaged, new EnchantmentLevel(CERegister.BANE_OF_ANTHROPOIDS, level), damager, damage, newDamage);
	}

}
