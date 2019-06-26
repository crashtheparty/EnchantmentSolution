package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AnvilListener implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPrepareAnvil(PrepareAnvilEvent event) {
		ItemStack first = event.getInventory().getItem(0);
		ItemStack second = event.getInventory().getItem(1);
		if(first != null && second != null) {
			if(AnvilUtils.canCombineItems(first, second)) {
				if (event.getViewers().get(0) instanceof Player) {
					event.setResult(ItemUtils.combineItems((Player) event.getViewers().get(0), first, second));
				}
			}
		}
		if(first != null && second == null) {
			ItemStack newFirst = first.clone();
			ItemMeta firstMeta = newFirst.getItemMeta();
			if((firstMeta.hasDisplayName() && !firstMeta.getDisplayName().equals(event.getInventory().getRenameText()))
					|| (!firstMeta.hasDisplayName() && event.getInventory().getRenameText() != null && !event.getInventory().getRenameText().equals(""))) {
				firstMeta.setDisplayName(event.getInventory().getRenameText());
				newFirst.setItemMeta(firstMeta);
				event.setResult(newFirst);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.ANVIL) {
			AnvilInventory inv = (AnvilInventory) event.getInventory();
			ItemStack first = inv.getItem(0);
			ItemStack second = inv.getItem(1);
			if(event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				if(first != null && second != null) {
					if(AnvilUtils.canCombineItems(first, second) && event.getSlot() == 2 && (event.getCursor() == null 
							|| event.getCursor().getType() == Material.AIR)) {
						event.setCancelled(true);
						int cost = AnvilUtils.getRepairCost(player, first, second);
						if(cost > ConfigUtils.getMaxRepairLevel()) {
							HashMap<String, Object> loreCodes = ChatUtils.getCodes();
							loreCodes.put("%repairCost%", cost);
							ChatUtils.sendMessage(player, ChatUtils.getMessage(loreCodes, "anvil.cannot-repair"));
							return;
						}
						ItemStack combinedItem = ItemUtils.combineItems(player, first, second);
						int itemOneRepair = AnvilNMS.getRepairCost(first);
						int itemTwoRepair = AnvilNMS.getRepairCost(second);
						if(itemOneRepair > itemTwoRepair) {
							combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
						}else {
							combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
						}
						if(player.getLevel() >= cost) {
							switch(event.getClick()) {
							case LEFT:
							case RIGHT:
							case SHIFT_RIGHT:
								player.setItemOnCursor(combinedItem);
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							case SHIFT_LEFT:
								HashMap<Integer, ItemStack> items = player.getInventory().addItem(combinedItem);
								if(!items.isEmpty()) {
									return;
								}
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							default:
								break;
							
							}
						} else {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", cost);
							ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "anvil.message-cannot-combine-cost"));
						}
					}
				}

				if(first != null && second == null && event.getSlot() == 2 && (event.getCursor() == null 
						|| event.getCursor().getType() == Material.AIR)) {
					ItemMeta firstMeta = first.getItemMeta();
					if((firstMeta.hasDisplayName() && !firstMeta.getDisplayName().equals(inv.getRenameText()))
							|| (!firstMeta.hasDisplayName() && inv.getRenameText() != null && !inv.getRenameText().equals(""))) {
						event.setCancelled(true);
						firstMeta.setDisplayName(inv.getRenameText());
						first.setItemMeta(firstMeta);
						int cost = 1;
						if(player.getLevel() >= cost) {
							switch(event.getClick()) {
							case LEFT:
							case RIGHT:
							case SHIFT_RIGHT:
								player.setItemOnCursor(first);
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							case SHIFT_LEFT:
								HashMap<Integer, ItemStack> items = player.getInventory().addItem(ItemUtils.combineItems(player, first, second));
								if(!items.isEmpty()) {
									return;
								}
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							default:
								break;
							
							}
						} else {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", cost);
							ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "anvil.message-cannot-combine-cost"));
						}
					}
				}
			}
		}
	}
}
