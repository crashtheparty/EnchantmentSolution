package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.Collection;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public abstract class SaveContentsEffect extends EntityKilledEffect {

	public SaveContentsEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public SaveContentsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		return new SaveContentsResult(getLevel(items));
	}

	public class SaveContentsResult extends EffectResult {

		public SaveContentsResult(int level) {
			super(level);
		}
	}

}
