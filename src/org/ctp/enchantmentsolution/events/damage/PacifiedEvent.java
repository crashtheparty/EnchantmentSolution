package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class PacifiedEvent extends ESEntityDamageEntityEvent {

	public PacifiedEvent(LivingEntity damaged, Player damager, int level, double damage, double newDamage) {
		super(damaged, new EnchantmentLevel(CERegister.PACIFIED, level), damager, damage, newDamage);
	}
}
