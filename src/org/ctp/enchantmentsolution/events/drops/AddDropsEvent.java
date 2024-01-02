package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class AddDropsEvent extends DropEvent {

	private final Collection<ItemStack> original;

	public AddDropsEvent(LivingEntity killer, EnchantmentLevel enchantment, Collection<ItemStack> populated, Collection<ItemStack> original) {
		super(killer, enchantment, populated);
		this.original = original;
	}

	public Collection<ItemStack> getOriginal() {
		return original;
	}

}
