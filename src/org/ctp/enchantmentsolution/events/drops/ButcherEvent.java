package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ButcherEvent extends AddDropsEvent {

	public ButcherEvent(LivingEntity killer, int level, Collection<ItemStack> populated, Collection<ItemStack> original) {
		super(killer, new EnchantmentLevel(CERegister.BUTCHER, level), populated, original);
	}

}
