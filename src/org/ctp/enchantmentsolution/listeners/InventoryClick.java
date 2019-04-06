package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.inventory.Grindstone;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.inventory.LegacyAnvil;
import org.ctp.enchantmentsolution.utils.InventoryClickUtils;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		Player player = null;
		if (event.getWhoClicked() instanceof Player) {
			player = (Player) event.getWhoClicked();
		} else {
			return;
		}
		if (inv == null) {
			return;
		}
		InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
		if(invData != null) {
			if(invData instanceof LegacyAnvil) {
				return;
			}
			event.setCancelled(true);
			if(invData instanceof EnchantmentTable) {
				EnchantmentTable table = (EnchantmentTable) invData;
				
				InventoryClickUtils.setEnchantmentTableDetails(table, player, inv, event.getClickedInventory(), event.getSlot());
			} else if(invData instanceof Anvil) {
				Anvil anvil = (Anvil) invData;
				
				InventoryClickUtils.setAnvilDetails(anvil, player, inv, event.getClickedInventory(), event.getSlot());
			} else if (invData instanceof Grindstone) {
				Grindstone stone = (Grindstone) invData;
				
				InventoryClickUtils.setGrindstoneDetails(stone, player, inv, event.getClickedInventory(), event.getSlot());
			} else if (invData instanceof ConfigInventory) {
				ConfigInventory configInv = (ConfigInventory) invData;

				InventoryClickUtils.setConfigInventoryDetails(configInv, player, inv, event.getClickedInventory(), event.getSlot(), event.getClick());
			}
		}
	}
}
