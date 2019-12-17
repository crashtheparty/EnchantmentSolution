package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class GungHoEvent extends ESEntityDamageEntityEvent {

	public GungHoEvent(LivingEntity damaged, Player damager, double damage, double newDamage) {
		super(damaged, new EnchantmentLevel(CERegister.GUNG_HO, 1), damager, damage, newDamage);
	}
}
