package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class LassoDamageEvent extends ESEntityDamageEntityEvent {

	public LassoDamageEvent(LivingEntity damaged, int level, Player damager) {
		super(damaged, new EnchantmentLevel(CERegister.IRENES_LASSO, level), damager, 0, 0);
	}

}
