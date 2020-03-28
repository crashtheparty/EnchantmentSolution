package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public abstract class GenerateEnchantments {

	private OfflinePlayer player;
	private ItemStack item;
	private EnchantmentLocation location;

	public GenerateEnchantments(OfflinePlayer player, ItemStack item, EnchantmentLocation location) {
		this.player = player;
		this.item = item;
		this.location = location;
	}

	public OfflinePlayer getPlayer() {
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

	public EnchantmentLocation getLocation() {
		return location;
	}

	public void setLocation(EnchantmentLocation location) {
		this.location = location;
	}

}