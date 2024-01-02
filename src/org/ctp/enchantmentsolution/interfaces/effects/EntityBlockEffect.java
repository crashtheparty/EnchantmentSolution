package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EntityBlockCondition;

public abstract class EntityBlockEffect extends EnchantmentEffect {

	private final EntityBlockCondition[] conditions;

	public EntityBlockEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EntityBlockCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity entity, Block block, EntityChangeBlockEvent event) {
		for(EntityBlockCondition condition: conditions)
			if (!condition.metCondition(entity, block, event)) return false;
		return true;
	}

	public abstract EffectResult run(Entity entity, Block block, ItemStack[] items, EntityChangeBlockEvent event);

}
