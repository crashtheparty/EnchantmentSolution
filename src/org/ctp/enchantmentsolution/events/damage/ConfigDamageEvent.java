package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.config.ConfigEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class ConfigDamageEvent extends ESEntityDamageEntityEvent {

	public ConfigDamageEvent(LivingEntity damaged, ConfigEnchantment enchantment, int level, LivingEntity damager, double damage, double newDamage) {
		super(damaged, new EnchantmentLevel(enchantment, level), damager, damage, newDamage);
	}

}
