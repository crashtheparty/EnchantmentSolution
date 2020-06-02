package org.ctp.enchantmentsolution.events.drops;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class TransmutationEvent extends OverrideDropsEvent {

	private final LivingEntity killed;

	public TransmutationEvent(Player who, LivingEntity killed, List<ItemStack> newDrops, List<ItemStack> originalDrops,
	boolean override) {
		super(who, new EnchantmentLevel(CERegister.TRANSMUTATION, 1), newDrops, originalDrops, override);
		this.killed = killed;
	}

	public LivingEntity getKilled() {
		return killed;
	}

}
