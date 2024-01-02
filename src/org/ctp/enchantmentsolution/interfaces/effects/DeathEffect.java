package org.ctp.enchantmentsolution.interfaces.effects;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public abstract class DeathEffect extends EnchantmentEffect {

	private final DeathCondition[] conditions;

	public DeathEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DeathCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity killer, Entity killed, Collection<ItemStack> drops) {
		for(DeathCondition condition: conditions)
			if (!condition.metCondition(killer, killed, drops)) return false;
		return true;
	}

	public abstract EffectResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event);

}
