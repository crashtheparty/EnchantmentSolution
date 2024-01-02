package org.ctp.enchantmentsolution.interfaces.effects.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.items.LassoUtils;

public class LassoEntityEffect extends EntityDamageEffect {

	public LassoEntityEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public LassoEntityResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		ItemStack i = null;
		for(ItemStack item: items) {
			if (item == null) continue;
			int level = EnchantmentUtils.getLevel(item, getEnchantment());
			int inItem = LassoUtils.getNumEntities(item, getEnchantment());
			if (level > inItem) {
				i = item;
				break;
			}
		}
		if (i != null) return new LassoEntityResult(EnchantmentUtils.getLevel(i, getEnchantment()), damaged, i);

		return null;
	}

	public class LassoEntityResult extends EffectResult {

		private final Entity entity;
		private final ItemStack item;

		public LassoEntityResult(int level, Entity entity, ItemStack item) {
			super(level);
			this.entity = entity;
			this.item = item;
		}

		public Entity getEntity() {
			return entity;
		}

		public ItemStack getItem() {
			return item;
		}
	}

}
