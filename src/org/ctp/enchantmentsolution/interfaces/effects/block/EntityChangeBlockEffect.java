package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EntityBlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityBlockEffect;

public abstract class EntityChangeBlockEffect extends EntityBlockEffect {

	public EntityChangeBlockEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EntityBlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public EntityChangeBlockResult run(Entity entity, Block block, ItemStack[] items, EntityChangeBlockEvent event) {
		int level = getLevel(items);
		
		return new EntityChangeBlockResult(level);
	}

	public class EntityChangeBlockResult extends EffectResult {

		public EntityChangeBlockResult(int level) {
			super(level);
		}
	}

}
