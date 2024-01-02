package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;

public abstract class EntityEquipEffect extends EnchantmentEffect {

	private final EquipCondition[] conditions;

	public EntityEquipEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EquipCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity entity) {
		for(EquipCondition condition: conditions)
			if (!condition.metCondition(entity)) return false;
		return true;
	}

	public abstract EffectResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event);

}
