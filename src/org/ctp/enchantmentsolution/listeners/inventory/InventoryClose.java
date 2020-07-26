package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.EnchantabilityCalc;

public class InventoryClose implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
		if (inv != null) if (inv instanceof ConfigInventory) {
			if (!((ConfigInventory) inv).isChat() && inv.getInventory() != null && inv.getInventory().equals(event.getInventory())) inv.close(true);
		} else if (inv instanceof EnchantabilityCalc) {
			if (!((EnchantabilityCalc) inv).isChat() && inv.getInventory() != null && inv.getInventory().equals(event.getInventory())) inv.close(true);
		} else if (inv.getInventory() != null && inv.getInventory().equals(event.getInventory())) inv.close(true);
	}

}
