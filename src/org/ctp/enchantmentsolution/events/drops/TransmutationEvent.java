package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class TransmutationEvent extends OverrideDropsEvent {

	private final LivingEntity killed;

	public TransmutationEvent(LivingEntity who, LivingEntity killed, Collection<ItemStack> drops, Collection<ItemStack> originalDrops) {
		super(who, new EnchantmentLevel(CERegister.TRANSMUTATION, 1), drops, originalDrops);
		this.killed = killed;
	}

	public LivingEntity getKilled() {
		return killed;
	}

}
