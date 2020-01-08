package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ItemDamageEvent extends ESPlayerEvent {

	private final ItemStack item;
	private final int oldDamage;
	private int newDamage;

	public ItemDamageEvent(Player who, EnchantmentLevel enchantment, ItemStack item, int oldDamage, int newDamage) {
		super(who, enchantment);
		this.item = item;
		this.oldDamage = oldDamage;
		setNewDamage(newDamage);
	}

	public ItemStack getItem() {
		return item;
	}

	public int getOldDamage() {
		return oldDamage;
	}

	public int getNewDamage() {
		return newDamage;
	}

	public void setNewDamage(int newDamage) {
		this.newDamage = newDamage;
	}

}
