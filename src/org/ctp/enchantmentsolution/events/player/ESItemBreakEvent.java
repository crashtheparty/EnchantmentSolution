package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ESItemBreakEvent extends ESPlayerEvent {

	private final ItemStack item;
	
	public ESItemBreakEvent(Player who, EnchantmentLevel enchantment, ItemStack item) {
		super(who, enchantment);
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

}
