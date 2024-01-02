package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class OverrideDropsEvent extends DropEvent {

	private final Collection<ItemStack> originalDrops;

	public OverrideDropsEvent(LivingEntity who, EnchantmentLevel enchantment, Collection<ItemStack> drops, Collection<ItemStack> originalDrops) {
		super(who, enchantment, drops);
		this.originalDrops = originalDrops;
	}

	public Collection<ItemStack> getOriginalDrops() {
		return originalDrops;
	}

}
