package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.TakeDamageCondition;

public abstract class EntityTakeDamageEffect extends EnchantmentEffect {

	private final TakeDamageCondition[] conditions;

	public EntityTakeDamageEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	TakeDamageCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity damaged, EntityDamageEvent event) {
		for(TakeDamageCondition condition: conditions)
			if (!condition.metCondition(damaged, event)) return false;
		return true;
	}

	public abstract EffectResult run(Entity damaged, ItemStack[] items, EntityDamageEvent event);

}
