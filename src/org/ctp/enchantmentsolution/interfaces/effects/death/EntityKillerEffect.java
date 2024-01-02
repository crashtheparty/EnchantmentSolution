package org.ctp.enchantmentsolution.interfaces.effects.death;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.effects.DeathEffect;

public abstract class EntityKillerEffect extends DeathEffect {

	public EntityKillerEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

}
