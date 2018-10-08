package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.ItemUtils;

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
		if (inv == null)
			return;
		InventoryData invData = EnchantmentSolution.getInventory(player);
		if(invData != null) {
			event.setCancelled(true);
			if(invData instanceof EnchantmentTable) {
				EnchantmentTable table = (EnchantmentTable) invData;
				
				if (!(inv.getType().equals(InventoryType.CHEST))) {
					int slot = event.getSlot();
					ItemStack item = event.getClickedInventory().getItem(slot);
					if (Enchantments.isEnchantable(item)) {
						ItemStack replace = new ItemStack(Material.AIR);
						if(item.getAmount() > 1){
							replace = item.clone();
							replace.setAmount(replace.getAmount() - 1);
							item.setAmount(1);
						}
						if (table.addItem(item)) {
							table.setInventory();
							player.getInventory().setItem(slot, replace);
						}
					}
				} else {
					int slot = event.getSlot();
					ItemStack item = event.getClickedInventory().getItem(slot);
					if (table.getItems().contains(item)) {
						if (table.removeItem(item, slot)) {
							table.setInventory();
							ItemUtils.giveItemToPlayer(player, item, player.getLocation());
						}
					}else if(slot > 17 && slot % 9 >= 3 && slot % 9 <= 8 && item != null && item.getType() != Material.RED_STAINED_GLASS_PANE && item.getType() != Material.BLACK_STAINED_GLASS_PANE){
						int itemSlot = (slot - 18) / 9;
						int itemLevel = (slot % 9) - 3;
						table.enchantItem(itemSlot, itemLevel);
					}
				}
			} else if(invData instanceof Anvil) {
				Anvil anvil = (Anvil) invData;

				if (!(inv.getType().equals(InventoryType.CHEST))) {
					if(inv.getType().equals(InventoryType.ANVIL)) return;
					int slot = event.getSlot();
					ItemStack item = event.getClickedInventory().getItem(slot);
					if(item == null || item.getType().equals(Material.AIR)) {
						return;
					}
					ItemStack replace = new ItemStack(Material.AIR);
					if(item.getAmount() > 1 && item.getType() == Material.BOOK){
						replace = item.clone();
						replace.setAmount(replace.getAmount() - 1);
						item.setAmount(1);
					}
					if (anvil.addItem(item)) {
						anvil.setInventory();
						player.getInventory().setItem(slot, replace);
					}
				} else {
					int slot = event.getSlot();
					ItemStack item = event.getClickedInventory().getItem(slot);
					if(slot == 4) {
						if(item.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
							anvil.setOpeningNew(true);
							Anvil_GUI_NMS.createAnvil(player, anvil);
						}
					}else if(slot == 16) {
						anvil.combine();
						anvil.setInventory();
					}else if (anvil.getItems().contains(item)) {
						if (anvil.removeItem(slot)) {
							anvil.setInventory();
							ItemUtils.giveItemToPlayer(player, item, player.getLocation());
						}
					}
				}
			}
		}
	}
}
