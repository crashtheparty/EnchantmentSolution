package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.*;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.inventory.rpg.RPGInventory;
import org.ctp.enchantmentsolution.utils.InventoryClickUtils;
import org.ctp.enchantmentsolution.utils.MinigameUtils;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		Player player = null;
		if (event.getWhoClicked() instanceof Player) player = (Player) event.getWhoClicked();
		else
			return;
		if (inv == null) return;
		InventoryData invData = EnchantmentSolution.getPlugin().getInventory(player);
		if (invData != null) {
			if (invData instanceof LegacyAnvil) return;
			event.setCancelled(true);
			if (invData instanceof EnchantmentTable) {
				EnchantmentTable table = (EnchantmentTable) invData;

				InventoryClickUtils.setEnchantmentTableDetails(table, player, inv, event.getClickedInventory(), event.getSlot());
			} else if (invData instanceof Anvil) {
				Anvil anvil = (Anvil) invData;

				InventoryClickUtils.setAnvilDetails(anvil, player, inv, event.getClickedInventory(), event.getSlot());
			} else if (invData instanceof Grindstone) {
				Grindstone stone = (Grindstone) invData;

				InventoryClickUtils.setGrindstoneDetails(stone, player, inv, event.getClickedInventory(), event.getSlot());
			} else if (invData instanceof ConfigInventory) {
				ConfigInventory configInv = (ConfigInventory) invData;

				InventoryClickUtils.setConfigInventoryDetails(configInv, player, inv, event.getClickedInventory(), event.getSlot(), event.getClick());
			} else if (invData instanceof EnchantabilityCalc) {
				EnchantabilityCalc enchantabilityCalc = (EnchantabilityCalc) invData;

				InventoryClickUtils.setEnchantabilityCalc(enchantabilityCalc, player, inv, event.getClickedInventory(), event.getSlot(), event.getClick());
			} else if (invData instanceof RPGInventory) {
				RPGInventory rpgInventory = (RPGInventory) invData;

				InventoryClickUtils.setRPGInventory(rpgInventory, player, inv, event.getClickedInventory(), event.getSlot(), event.getClick());
			} else if (invData instanceof Minigame) {
				Minigame minigameInventory = (Minigame) invData;

				InventoryClickUtils.setMinigameInventory(minigameInventory, player, inv, event.getClickedInventory(), event.getSlot(), event.getClick());
			}
		}

		if (MinigameUtils.isEnabled() && MinigameUtils.quickAnvil()) MinigameUtils.setAnvil(event);
	}
}
