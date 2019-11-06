package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class ChatMessage implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
		if (inv != null && inv instanceof ConfigInventory) {
			ConfigInventory configInv = (ConfigInventory) inv;
			if (configInv.isChat()) {
				event.setCancelled(true);
				String chat = event.getMessage();
				if (configInv.getType().equals("list")) {
					configInv.addToList(chat);
				} else {
					configInv.setPath(configInv.getLevel(), chat);
				}
				configInv.setChat(false);
				Bukkit.getScheduler().runTask(EnchantmentSolution.getPlugin(), new Runnable() {
					@Override
					public void run() {
						configInv.reopenFromAnvil(true);
					}

				});
			} else {
				EnchantmentSolution.getPlugin().removeInventory(configInv);
			}
		}
	}

}
