package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESDamageEntityEvent;

public class DrownDamageEvent extends ESDamageEntityEvent {

	public DrownDamageEvent(LivingEntity damaged, int level, double damage, double newDamage) {
		super(damaged, new EnchantmentLevel(CERegister.DROWNED, level), damage, newDamage);
	}

}
