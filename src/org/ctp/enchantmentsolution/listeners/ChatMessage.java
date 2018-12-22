package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class ChatMessage implements Listener{
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		InventoryData inv = EnchantmentSolution.getInventory(player);
		if(inv != null && inv instanceof ConfigInventory) {
			ConfigInventory configInv = (ConfigInventory) inv;
			if(configInv.isChat()) {
				event.setCancelled(true);
				String chat = event.getMessage();
				configInv.addToList(chat);
				configInv.setChat(false);
				if(configInv.getType().equals("list")) {
					configInv.listDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage());
				} else {
					configInv.listConfigDetails(configInv.getConfig(), configInv.getLevel(), configInv.getPage());
				}
			} else {
				EnchantmentSolution.removeInventory(configInv);
			}
		}
	}

}
