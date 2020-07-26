package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class InventoryPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		InventoryData data = EnchantmentSolution.getPlugin().getInventory(player);
		if (data != null) {
			event.getDrops().addAll(data.getItems());
			data.close(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		InventoryData data = EnchantmentSolution.getPlugin().getInventory(player);
		if (data != null) data.close(true);
	}
}
