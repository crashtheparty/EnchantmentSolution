package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public abstract class AddDropsEffect extends EntityKillerEffect {

	private ItemStack[] extraDrops;

	public AddDropsEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public AddDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		int level = getLevel(items);
		Collection<ItemStack> populated = new ArrayList<ItemStack>();
		populated.addAll(drops);
		populated.addAll(Arrays.asList(extraDrops));

		return new AddDropsResult(level, populated);
	}

	public void setExtraDrops(ItemStack... items) {
		extraDrops = items;
	}

	public class AddDropsResult extends EffectResult {

		private final Collection<ItemStack> drops;

		public AddDropsResult(int level, Collection<ItemStack> drops) {
			super(level);
			this.drops = drops;
		}

		public Collection<ItemStack> getDrops() {
			return drops;
		}
	}

}
