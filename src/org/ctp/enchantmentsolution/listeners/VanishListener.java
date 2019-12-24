package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
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
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
			Player player = event.getPlayer();

			PlayerInventory inv = player.getInventory();

			for(int i = 0; i < 36; i++) {
				ItemStack item = inv.getItem(i);
				inv.setItem(i, removeEnchants(item));
			}
			ItemStack helmet = inv.getHelmet();
			ItemStack chest = inv.getChestplate();
			ItemStack legs = inv.getLeggings();
			ItemStack boots = inv.getBoots();
			ItemStack offhand = inv.getItemInOffHand();

			if (helmet != null) inv.setHelmet(removeEnchants(helmet));
			if (chest != null) inv.setChestplate(removeEnchants(chest));
			if (legs != null) inv.setLeggings(removeEnchants(legs));
			if (boots != null) inv.setBoots(removeEnchants(boots));
			if (offhand != null) inv.setItemInOffHand(removeEnchants(offhand));
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
			boolean shouldCheck = true;
			if (event.getPlayer() instanceof Player) {
				Player player = (Player) event.getPlayer();
				InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
				if (invData != null) shouldCheck = false;
			}
			if (shouldCheck) {
				Inventory inv = event.getInventory();
				for(int i = 0; i < inv.getSize(); i++) {
					ItemStack item = inv.getItem(i);
					inv.setItem(i, removeEnchants(item));
				}
			}
		}
	}

	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
			ItemStack item = event.getItem().getItemStack();
			event.getItem().setItemStack(removeEnchants(item));
		}
	}

	public static void reload() {
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) for(Player player: Bukkit.getOnlinePlayers())
			Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
				if (player.getOpenInventory() != null) {
					Inventory inv1 = player.getOpenInventory().getTopInventory();
					InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
					if (invData == null) for(int i1 = 0; i1 < inv1.getSize(); i1++) {
						ItemStack item1 = inv1.getItem(i1);
						inv1.setItem(i1, removeEnchants(item1));
					}
				}
				PlayerInventory inv2 = player.getInventory();

				for(int i2 = 0; i2 < 36; i2++) {
					ItemStack item2 = inv2.getItem(i2);
					inv2.setItem(i2, removeEnchants(item2));
				}
				ItemStack helmet = inv2.getHelmet();
				ItemStack chest = inv2.getChestplate();
				ItemStack legs = inv2.getLeggings();
				ItemStack boots = inv2.getBoots();
				ItemStack offhand = inv2.getItemInOffHand();

				if (helmet != null) inv2.setHelmet(removeEnchants(helmet));
				if (chest != null) inv2.setChestplate(removeEnchants(chest));
				if (legs != null) inv2.setLeggings(removeEnchants(legs));
				if (boots != null) inv2.setBoots(removeEnchants(boots));
				if (offhand != null) inv2.setItemInOffHand(removeEnchants(offhand));
			}, 1l);
	}

	private static ItemStack removeEnchants(ItemStack item) {
		if (item == null || item.getItemMeta() == null) return item;
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments())
			if (!enchant.isEnabled()) item = ItemUtils.removeEnchantmentFromItem(item, enchant);
		return item;
	}

}
