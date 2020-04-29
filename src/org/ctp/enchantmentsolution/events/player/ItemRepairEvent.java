package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ItemRepairEvent extends ESPlayerEvent {

	private final ItemStack item;
	private int repair;
	
	public ItemRepairEvent(Player who, EnchantmentLevel enchantment, ItemStack item, int repair) {
		super(who, enchantment);
		this.item = item;
		this.setRepair(repair);
	}

	public ItemStack getItem() {
		return item;
	}

	public int getRepair() {
		return repair;
	}

	public void setRepair(int repair) {
		this.repair = repair;
	}

}
