package org.ctp.enchantmentsolution.events.soul;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.SoulEvent;

public class SoulboundEvent extends SoulEvent {

	private final List<ItemStack> savedItems;

	public SoulboundEvent(Player who, List<ItemStack> savedItems) {
		super(who, new EnchantmentLevel(CERegister.SOULBOUND, 1));

		this.savedItems = savedItems;
	}

	public List<ItemStack> getSavedItems() {
		return savedItems;
	}
}
