package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public class ESEntityDeathEvent extends ESEntityEvent {

	public ESEntityDeathEvent(Entity who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}

}
