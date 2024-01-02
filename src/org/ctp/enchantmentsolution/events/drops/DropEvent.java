package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class DropEvent extends ESEntityEvent {

	private Collection<ItemStack> drops;

	public DropEvent(LivingEntity who, EnchantmentLevel enchantment, Collection<ItemStack> drops) {
		super(who, enchantment);
		this.drops = drops;
	}

	public Collection<ItemStack> getDrops() {
		return drops;
	}
}
