package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ItemDamageEvent extends ESPlayerEvent {

	private boolean cancelled;
	private final ItemStack item;
	private final int oldDamage;
	private int newDamage;

	public ItemDamageEvent(Player who, EnchantmentLevel enchantment, ItemStack item, int oldDamage, int newDamage) {
		super(who, enchantment);
		this.item = item;
		this.oldDamage = oldDamage;
		setNewDamage(newDamage);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
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
