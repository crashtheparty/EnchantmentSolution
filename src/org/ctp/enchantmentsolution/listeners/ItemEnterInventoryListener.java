package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.ArmorEquipEvent;
import org.ctp.crashapi.events.ItemAddEvent;
import org.ctp.crashapi.events.ItemEquipEvent;
import org.ctp.crashapi.item.MatData;

public class ItemEnterInventoryListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		LivingEntity e = event.getEntity();
		if (e instanceof Player) {
			Player player = (Player) e;
			Item item = event.getItem();
			ItemAddEvent itemAdd = new ItemAddEvent(player, item.getItemStack());
			Bukkit.getPluginManager().callEvent(itemAdd);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemEquip(ItemEquipEvent event) {
		if (event.getNewItem() != null) {
			Player player = event.getPlayer();
			ItemAddEvent itemAdd = new ItemAddEvent(player, event.getNewItem());
			Bukkit.getPluginManager().callEvent(itemAdd);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onArmorEquip(ArmorEquipEvent event) {
		if (event.getNewArmorPiece() != null) {
			Player player = event.getPlayer();
			ItemAddEvent itemAdd = new ItemAddEvent(player, event.getNewArmorPiece());
			Bukkit.getPluginManager().callEvent(itemAdd);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		for(int i = 0; i < 36; i++) {
			if (player.getInventory().getHeldItemSlot() == i) continue; // already gotten from ItemEquipEvent
			ItemStack item = player.getInventory().getItem(i);
			if (item == null || MatData.isAir(item.getType())) continue;
			ItemAddEvent itemAdd = new ItemAddEvent(player, item);
			Bukkit.getPluginManager().callEvent(itemAdd);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(InventoryClickEvent event) {
		boolean shift = false, numberkey = false, dropping = false;

		if (event.isCancelled() || !(event.getWhoClicked() instanceof Player) || event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

		if (!event.getInventory().getType().equals(InventoryType.CRAFTING) && !event.getInventory().getType().equals(InventoryType.WORKBENCH)) return;

		if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) shift = true;

		if (event.getClick().equals(ClickType.NUMBER_KEY)) numberkey = true;

		if (event.getClick().equals(ClickType.DROP) || event.getClick().equals(ClickType.CONTROL_DROP)) dropping = true;

		ItemStack currentItem = event.getCurrentItem();
		ItemStack cursorItem = event.getCursor();

		// consider AIR as null
		if (isAirOrNull(currentItem)) currentItem = null;
		if (isAirOrNull(cursorItem)) cursorItem = null;

		if (currentItem == null && cursorItem == null) return;

		int holdslot = event.getWhoClicked().getInventory().getHeldItemSlot();
		int clicked = event.getRawSlot();
		Player player = (Player) event.getWhoClicked();

		if (dropping) {
			if (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR) && event.getSlot() == holdslot && currentItem.getAmount() == 1) {
				// handled by ItemEquipEvent
			} else if (clicked == 45 && currentItem.getAmount() == 1) {
				// handled by ItemEquipEvent
			} else {
				ItemAddEvent itemAdd = new ItemAddEvent(player, currentItem);
				Bukkit.getPluginManager().callEvent(itemAdd);
			}
		} else if (shift) {
			if (clicked - 36 == holdslot) {
				// handled by ItemEquipEvent
			} else {
				if (clicked == 45) // handled by ItemEquipEvent
					return;
				else if (currentItem != null && currentItem.getType() == Material.SHIELD && isAirOrNull(player.getInventory().getItemInOffHand())) // handled by
																																					// ItemEquipEvent
					return;
				int empty = -1;

				if (event.getSlotType() == InventoryType.SlotType.QUICKBAR) {
					ItemAddEvent itemAdd = new ItemAddEvent(player, currentItem);
					Bukkit.getPluginManager().callEvent(itemAdd);
					return;
				}

				if (event.getSlotType() == InventoryType.SlotType.RESULT) {
					ItemAddEvent itemAdd = new ItemAddEvent(player, currentItem);
					Bukkit.getPluginManager().callEvent(itemAdd);
					return;
				} else
					for(int i = 0; i <= 8; i++) {
						ItemStack item = player.getInventory().getItem(i);

						if (isAirOrNull(item)) {
							empty = i;
							break;
						}
					}

				if (empty != holdslot) {
					ItemAddEvent itemAdd = new ItemAddEvent(player, currentItem);
					Bukkit.getPluginManager().callEvent(itemAdd);
				}
			}
		} else if (numberkey) {} else if (event.getSlot() == holdslot) {} else if (clicked == 45) {} else {
			ItemAddEvent itemAdd = new ItemAddEvent(player, cursorItem);
			Bukkit.getPluginManager().callEvent(itemAdd);
		}
	}

	private boolean isAirOrNull(ItemStack item) {
		return item == null || MatData.isAir(item.getType());
	}

}
