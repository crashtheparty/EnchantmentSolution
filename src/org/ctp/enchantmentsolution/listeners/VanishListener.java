package org.ctp.enchantmentsolution.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.inventory.snapshot.VanishInventory;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class VanishListener implements Listener {

	private static Map<UUID, VanishInventory> VANISH_INVENTORIES = new HashMap<UUID, VanishInventory>();

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
			if (inv.getLocation() == null) return; // it should not remove enchantments from custom inventories ever
			for(int i = 0; i < inv.getSize(); i++) {
				ItemStack item = inv.getItem(i);
				if (item != null && item.hasItemMeta() && (item.getType() == Material.ENCHANTED_BOOK && ((EnchantmentStorageMeta) item.getItemMeta()).hasStoredEnchants() || item.getItemMeta().hasEnchants())) removeEnchants(player, item);
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
			if (item == null || MatData.isAir(item.getType()) || !item.hasItemMeta()) return;
			removeEnchants(player, item);
		}
	}

	private static void removePlayerInv(Player player) {
		PlayerInventory inv = player.getInventory();
		VanishInventory vanish;
		if (VANISH_INVENTORIES.containsKey(player.getUniqueId())) vanish = VANISH_INVENTORIES.get(player.getUniqueId());
		else
			vanish = new VanishInventory(player);
		vanish.setInventory(); // update the inventory
		ItemStack[] items = vanish.getItems();

		for(int i = 0; i < 36; i++) {
			ItemStack item = inv.getItem(i);
			if (item == null || item.equals(items[i])) continue;
			removeEnchants(player, item);
		}
		ItemStack helmet = inv.getHelmet();
		ItemStack chest = inv.getChestplate();
		ItemStack legs = inv.getLeggings();
		ItemStack boots = inv.getBoots();
		ItemStack offhand = inv.getItemInOffHand();

		if (helmet != null && !helmet.equals(items[37])) removeEnchants(player, helmet);
		if (chest != null && !chest.equals(items[38])) removeEnchants(player, chest);
		if (legs != null && !legs.equals(items[39])) removeEnchants(player, legs);
		if (boots != null && !boots.equals(items[40])) removeEnchants(player, boots);
		if (offhand != null && !offhand.equals(items[36])) removeEnchants(player, offhand);
		VANISH_INVENTORIES.put(player.getUniqueId(), vanish);
	}

	public static void reload() {
		for(Player player: Bukkit.getOnlinePlayers())
			if (hasPermission(player)) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
				if (player.getOpenInventory() != null) {
					Inventory inv = player.getOpenInventory().getTopInventory();
					InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
					if (invData == null && inv.getLocation() != null) for(int i = 0; i < inv.getSize(); i++) {
						ItemStack item = inv.getItem(i);
						if (item != null && item.hasItemMeta() && (item.getType() == Material.ENCHANTED_BOOK && ((EnchantmentStorageMeta) item.getItemMeta()).hasStoredEnchants() || item.getItemMeta().hasEnchants())) removeEnchants(player, item);
					}
				}
				removePlayerInv(player);
			}, 1l);
	}

	private static ItemStack removeEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return item;
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (!enchant.isEnabled()) item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
			if (EnchantmentUtils.hasEnchantment(item, enchant.getRelativeEnchantment())) {
				boolean lower = false;
				int maxLevel = enchant.getMaxLevel();
				if (player != null) {
					lower = player.hasPermission("enchantmentsolution.enchantments.lower-levels");
					maxLevel = enchant.getMaxLevel(player);
				}
				if (lower && maxLevel < EnchantmentUtils.getLevel(item, enchant.getRelativeEnchantment())) {
					if (maxLevel == 0) item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
					else
						item = EnchantmentUtils.addEnchantmentToItem(item, enchant, maxLevel);
				} else { /* placeholder */ }
			}
		}
		return item;
	}

	private static boolean hasPermission(Player player) {
		return player.hasPermission("enchantmentsolution.enchantments.lower-levels") || ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish");
	}

}
