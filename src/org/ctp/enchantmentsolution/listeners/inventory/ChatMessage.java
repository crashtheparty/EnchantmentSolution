package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.EnchantabilityCalc;

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
				if (configInv.getType().equals("list")) configInv.addToList(chat);
				else
					configInv.setPath(configInv.getLevel(), chat);
				configInv.setChat(false);
				Bukkit.getScheduler().runTask(EnchantmentSolution.getPlugin(), (Runnable) () -> configInv.reopenFromAnvil(true));
			} else
				EnchantmentSolution.getPlugin().removeInventory(configInv);
		} else if (inv != null && inv instanceof EnchantabilityCalc) {
			EnchantabilityCalc enchantabilityCalc = (EnchantabilityCalc) inv;
			if (enchantabilityCalc.isChat()) {
				event.setCancelled(true);
				String chat = event.getMessage();
				enchantabilityCalc.setItemName(chat);
				enchantabilityCalc.setChat(false);
				Bukkit.getScheduler().runTask(EnchantmentSolution.getPlugin(), (Runnable) () -> enchantabilityCalc.setInventory());
			} else
				EnchantmentSolution.getPlugin().removeInventory(enchantabilityCalc);
		}
	}

}
