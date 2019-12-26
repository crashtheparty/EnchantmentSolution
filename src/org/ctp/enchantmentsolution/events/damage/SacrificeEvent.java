package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class SacrificeEvent extends ESEntityDamageEntityEvent {

	public SacrificeEvent(LivingEntity damaged, Player damager, double damage) {
		super(damaged, new EnchantmentLevel(CERegister.SACRIFICE, 1), damager, 0, damage);
	}

}
