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
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class VanishListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(EnchantmentSolution.getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("vanish")) {
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
			
			if(helmet != null) {
				inv.setHelmet(removeEnchants(helmet));
			}
			if(chest != null) {
				inv.setChestplate(removeEnchants(chest));
			}
			if(legs != null) {
				inv.setLeggings(removeEnchants(legs));
			}
			if(boots != null) {
				inv.setBoots(removeEnchants(boots));
			}
			if(offhand != null) {
				inv.setItemInOffHand(removeEnchants(offhand));
			}
		}
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if(EnchantmentSolution.getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("vanish")) {
			boolean shouldCheck = true;
			if(event.getPlayer() instanceof Player) {
				Player player = (Player) event.getPlayer();
				InventoryData invData = EnchantmentSolution.getInventory(player);
				if(invData != null) {
					shouldCheck = false;
				}
			}
			if(shouldCheck) {
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
		if(EnchantmentSolution.getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("vanish")) {
			ItemStack item = event.getItem().getItemStack();
			event.getItem().setItemStack(removeEnchants(item));
		}
	}
	
	public static void reload() {
		if(EnchantmentSolution.getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("vanish")) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.PLUGIN, new Runnable(){
					@Override
					public void run() {
						if(player.getOpenInventory() != null) {
							Inventory inv = player.getOpenInventory().getTopInventory();
							InventoryData invData = EnchantmentSolution.getInventory(player);
							if(invData == null) {
								for(int i = 0; i < inv.getSize(); i++) {
									ItemStack item = inv.getItem(i);
									inv.setItem(i, removeEnchants(item));
								}
							}
						}
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
						
						if(helmet != null) {
							inv.setHelmet(removeEnchants(helmet));
						}
						if(chest != null) {
							inv.setChestplate(removeEnchants(chest));
						}
						if(legs != null) {
							inv.setLeggings(removeEnchants(legs));
						}
						if(boots != null) {
							inv.setBoots(removeEnchants(boots));
						}
						if(offhand != null) {
							inv.setItemInOffHand(removeEnchants(offhand));
						}
					}
				}, 1l);
			}
		}
	}
	
	private static ItemStack removeEnchants(ItemStack item) {
		if(item == null || item.getItemMeta() == null) return item;
		for(CustomEnchantment enchant : DefaultEnchantments.getEnchantments()) {
			if(!enchant.isEnabled()) {
				item = Enchantments.removeEnchantmentFromItem(item, enchant);
			}
		}
		return item;
	}

}
