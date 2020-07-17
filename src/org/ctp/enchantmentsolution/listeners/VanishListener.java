package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.MatData;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (hasPermission(player)) removePlayerInv(player);
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		boolean shouldCheck = true;
		Player player = null;
		if (event.getPlayer() instanceof Player) {
			player = (Player) event.getPlayer();
			InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
			if (invData != null) shouldCheck = false;
			if (hasPermission(player)) removePlayerInv(player);
		}
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish") && shouldCheck) {
			Inventory inv = event.getInventory();
			for(int i = 0; i < inv.getSize(); i++) {
				ItemStack item = inv.getItem(i);
				inv.setItem(i, removeEnchants(player, item));
			}
		}
	}

	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		LivingEntity e = event.getEntity();
		Player player = null;
		if (e instanceof Player) {
			player = (Player) e;
			if (hasPermission(player)) removePlayerInv(player);
		}

		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
			ItemStack item = event.getItem().getItemStack();
			if (item == null || MatData.isAir(item.getType())) return;
			event.getItem().setItemStack(removeEnchants(player, item));
		}
	}

	private static void removePlayerInv(Player player) {
		PlayerInventory inv = player.getInventory();

		for(int i = 0; i < 36; i++) {
			ItemStack item = inv.getItem(i);
			inv.setItem(i, removeEnchants(player, item));
		}
		ItemStack helmet = inv.getHelmet();
		ItemStack chest = inv.getChestplate();
		ItemStack legs = inv.getLeggings();
		ItemStack boots = inv.getBoots();
		ItemStack offhand = inv.getItemInOffHand();

		if (helmet != null) inv.setHelmet(removeEnchants(player, helmet));
		if (chest != null) inv.setChestplate(removeEnchants(player, chest));
		if (legs != null) inv.setLeggings(removeEnchants(player, legs));
		if (boots != null) inv.setBoots(removeEnchants(player, boots));
		if (offhand != null) inv.setItemInOffHand(removeEnchants(player, offhand));
	}

	public static void reload() {
		for(Player player: Bukkit.getOnlinePlayers())
			if (hasPermission(player)) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
				if (player.getOpenInventory() != null) {
					Inventory inv = player.getOpenInventory().getTopInventory();
					InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
					if (invData == null) for(int i = 0; i < inv.getSize(); i++) {
						ItemStack item = inv.getItem(i);
						inv.setItem(i, removeEnchants(player, item));
					}
				}
				removePlayerInv(player);
			}, 1l);
	}

	private static ItemStack removeEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return item;
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (!enchant.isEnabled()) item = ItemUtils.removeEnchantmentFromItem(item, enchant);
			if (ItemUtils.hasEnchantment(item, enchant.getRelativeEnchantment())) {
				boolean lower = false;
				int maxLevel = enchant.getMaxLevel();
				if (player != null) {
					lower = player.hasPermission("enchantmentsolution.enchantments.lower-levels");
					maxLevel = enchant.getMaxLevel(player);
				}
				if (lower && maxLevel < ItemUtils.getLevel(item, enchant.getRelativeEnchantment())) item = ItemUtils.addEnchantmentToItem(item, enchant, maxLevel);
			}
		}
		return item;
	}
	
	private static boolean hasPermission(Player player) {
		return player.hasPermission("enchantmentsolution.enchantments.lower-levels") || ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish");
	}

}
