package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GenerateEnchantments {

	private Player player;
	private ItemStack item;
	private boolean treasure;
	
	public GenerateEnchantments(Player player, ItemStack item, boolean treasure) {
		this.player = player;
		this.item = item;
		this.treasure = treasure;
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
		this.item = item;
	}

	public boolean isTreasure() {
		return treasure;
	}

	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}

}