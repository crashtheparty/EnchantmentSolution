package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.GrindstoneUtils;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

public class GrindstoneListener implements Listener{
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.GRINDSTONE) {
			GrindstoneInventory inv = (GrindstoneInventory) event.getInventory();
			ItemStack first = inv.getItem(0);
			ItemStack second = inv.getItem(1);
			if(event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				if(first != null && second != null) {
					if(GrindstoneUtils.canCombineItems(first, second) && event.getSlot() == 2 && (event.getCursor() == null 
							|| event.getCursor().getType() == Material.AIR)) {
						event.setCancelled(true);
						combine(player, first, second, event.getClick(), inv);
					}
				} else if (first != null || second != null) {
					ItemStack item = first;
					if(item == null) {
						item = second;
					}
					if((item.getItemMeta().hasEnchants() || (item.getType() == Material.ENCHANTED_BOOK)) && event.getSlot() == 2 
							&& (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) {
						event.setCancelled(true);
						combine(player, first, second, event.getClick(), inv);
					}
				}
				prepareGrindstone(player, inv);
			}
		}
	}
	
	private void prepareGrindstone(Player player, GrindstoneInventory inv) {
		ItemStack first = inv.getItem(0);
		ItemStack second = inv.getItem(1);
		ItemStack combinedItem = null;
		boolean takeEnchantments = false;
		
		if(first != null && second != null && GrindstoneUtils.canCombineItems(first, second)) {
			combinedItem = GrindstoneUtils.combineItems(player, first, second);
		} else if(ConfigUtils.grindstoneTakeEnchantments() && first != null && second != null && GrindstoneUtils.canTakeEnchantments(first, second)) {
			combinedItem = GrindstoneUtils.takeEnchantments(player, first, second);
			takeEnchantments = true;
		} else if (first != null && second != null){
		} else if (first != null || second != null) {
			ItemStack item = first;
			if(item == null) {
				item = second;
			}
			if(item.getItemMeta().hasEnchants() || (item.getType() == Material.ENCHANTED_BOOK)) {
				combinedItem = GrindstoneUtils.combineItems(player, item);
			}
		}
		
		if(combinedItem != null) {
			if (!takeEnchantments) {
				combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
			} else {
				if(ConfigUtils.grindstoneTakeRepairCost()) {
					combinedItem = AnvilNMS.setRepairCost(combinedItem, AnvilNMS.getRepairCost(first));
				} else {
					combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
				}
			}
		}
		
		inv.setItem(2, combinedItem);
	}
	
	private void combine(Player player, ItemStack first, ItemStack second, ClickType click, GrindstoneInventory inv) {
		ItemStack combinedItem = GrindstoneUtils.combineItems(player, first);
		if(second != null) {
			combinedItem = GrindstoneUtils.combineItems(player, first, second);
		}
		switch(click) {
		case LEFT:
		case RIGHT:
		case SHIFT_RIGHT:
			player.setItemOnCursor(combinedItem);
			inv.setContents(new ItemStack[3]);
			AbilityUtils.dropExperience(inv.getLocation().clone().add(new Location(inv.getLocation().getWorld(), 0.5, 0.5, 0.5)), 
					GrindstoneUtils.getExperience(first, second));
			break;
		case SHIFT_LEFT:
			HashMap<Integer, ItemStack> items = player.getInventory().addItem(combinedItem);
			if(!items.isEmpty()) {
				return;
			}
			inv.setContents(new ItemStack[3]);
			AbilityUtils.dropExperience(inv.getLocation().clone().add(new Location(inv.getLocation().getWorld(), 0.5, 0.5, 0.5)), 
					GrindstoneUtils.getExperience(first, second));
			break;
		default:
			break;
		}
	}
}
