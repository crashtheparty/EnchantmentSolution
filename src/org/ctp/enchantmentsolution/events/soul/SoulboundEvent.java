package org.ctp.enchantmentsolution.events.soul;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class SoulboundEvent extends SoulEvent {

	private final List<ItemStack> savedItems;
	private final Entity killer;

	public SoulboundEvent(Player who, Entity killer, List<ItemStack> savedItems) {
		super(who, new EnchantmentLevel(CERegister.SOULBOUND, 1));

		this.killer = killer;
		this.savedItems = savedItems;
	}

	public List<ItemStack> getSavedItems() {
		return savedItems;
	}

	public Entity getKiller() {
		return killer;
	}
}
