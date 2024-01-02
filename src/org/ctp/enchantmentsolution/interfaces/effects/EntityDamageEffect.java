package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public abstract class EntityDamageEffect extends EnchantmentEffect {

	private final DamageCondition[] conditions;

	public EntityDamageEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DamageCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		for(DamageCondition condition: conditions)
			if (!condition.metCondition(damager, damaged, event)) return false;
		return true;
	}

	public abstract EffectResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event);

}
