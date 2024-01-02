package org.ctp.enchantmentsolution.events.modify.entity;

import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ModifyEntityActionEvent extends ESEntityEvent {

	public ModifyEntityActionEvent(Entity entity, EnchantmentLevel enchantment) {
		super(entity, enchantment);
	}

}
