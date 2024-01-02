package org.ctp.enchantmentsolution.interfaces;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public abstract class EnchantmentEffect {

	private final EnchantmentMultipleType type;
	private final EnchantmentItemLocation location;
	private final EnchantmentWrapper enchantment;
	private final EventPriority priority;

	public EnchantmentEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority) {
		this.type = type;
		this.location = location;
		this.enchantment = enchantment;
		this.priority = priority;
	}

	public EnchantmentMultipleType getType() {
		return type;
	}

	public EnchantmentItemLocation getLocation() {
		return location;
	}

	public EnchantmentWrapper getEnchantment() {
		return enchantment;
	}

	public EventPriority getPriority() {
		return priority;
	}

	public int getLevel(ItemStack[] items) {
		int level = 0;
		for(ItemStack i: items) {
			if (i == null) continue;
			int newLevel = EnchantmentUtils.getLevel(i, getEnchantment());
			if (getType() == EnchantmentMultipleType.HIGHEST && newLevel > level) level = newLevel;
			else if (newLevel > 0) level += newLevel;
		}
		return level;
	}
	
	public int getLevel(ItemStack[] items, Enchantment enchantment) {
		EnchantmentWrapper wrapper = RegisterEnchantments.getByKey(enchantment.getKey());
		return getLevel(items, wrapper);
	}

	public int getLevel(ItemStack[] items, EnchantmentWrapper enchantment) {
		int level = 0;
		for(ItemStack i: items) {
			if (i == null) continue;
			int newLevel = EnchantmentUtils.getLevel(i, enchantment);
			if (getType() == EnchantmentMultipleType.HIGHEST && newLevel > level) level = newLevel;
			else if (newLevel > 0) level += newLevel;
		}
		return level;
	}

	public abstract class EffectResult {

		private final int level;

		public EffectResult(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}
	}
}
