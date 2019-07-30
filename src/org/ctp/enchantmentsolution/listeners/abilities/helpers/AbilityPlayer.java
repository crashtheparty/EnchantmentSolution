package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbilityPlayer {

	private Player player;
	private ItemStack item, previousItem;
	private Enchantment enchantment;
	private boolean alwaysRefresh;
	
	AbilityPlayer(Player player, ItemStack item, Enchantment enchantment) {
		this.player = player;
		this.enchantment = enchantment;
		this.setItem(item);
		alwaysRefresh = false;
	}
	
	AbilityPlayer(Player player, ItemStack item, Enchantment enchantment, boolean alwaysRefresh) {
		this.player = player;
		this.enchantment = enchantment;
		this.setItem(item);
		this.setAlwaysRefresh(alwaysRefresh);
	}

	private void setAlwaysRefresh(boolean alwaysRefresh) {
		this.alwaysRefresh = alwaysRefresh;
	}
	
	public boolean willAlwaysRefresh() {
		return alwaysRefresh;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.previousItem = this.item;
		if(item == null && previousItem != null){
			doUnequip(previousItem);
		}else if(item != null && previousItem == null){
			doEquip(item);
		}else if(previousItem != null && item != null){
			if (!item.toString().equalsIgnoreCase(
					previousItem.toString()) || willAlwaysRefresh()) {
				doUnequip(previousItem);
				doEquip(item);
			}
		}
		this.item = item;
	}

	public ItemStack getPreviousItem() {
		return previousItem;
	}

	public void setPreviousItem(ItemStack previousItem) {
		this.previousItem = previousItem;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}
	
	protected abstract void doEquip(ItemStack item);
	
	protected abstract void doUnequip(ItemStack item);
}
