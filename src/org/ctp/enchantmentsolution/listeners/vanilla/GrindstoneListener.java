package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.generate.GrindstoneEnchantments;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;

public class GrindstoneListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() != null && event.getInventory().getType() == InventoryType.GRINDSTONE) {
			GrindstoneInventory inv = (GrindstoneInventory) event.getInventory();
			ItemStack first = inv.getItem(0);
			ItemStack second = inv.getItem(1);
			if (event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				GrindstoneEnchantments ench = GrindstoneEnchantments.getGrindstoneEnchantments(player, first, second);
				if (ench.canCombine() && event.getSlot() == 2 && (event.getCursor() == null || MatData.isAir(event.getCursor().getType()))) {
					event.setCancelled(true);
					combine(ench, event.getClick(), inv);
				} else if (ench.canTakeEnchantments() && event.getSlot() == 2 && (event.getCursor() == null || MatData.isAir(event.getCursor().getType()))) {
					event.setCancelled(true);
					combine(ench, event.getClick(), inv);
				} else
					inv.setItem(2, ench.getCombinedItem());
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					ItemStack newFirst = inv.getItem(0);
					ItemStack newSecond = inv.getItem(1);
					GrindstoneEnchantments gEnch = GrindstoneEnchantments.getGrindstoneEnchantments(player, newFirst, newSecond);

					inv.setItem(2, gEnch.getCombinedItem());
				}, 2l);
			}
		}
	}

	private void combine(GrindstoneEnchantments ench, ClickType click, GrindstoneInventory inv) {
		Player player = ench.getPlayer().getPlayer();
		if (player == null) return;
		switch (click) {
			case LEFT:
			case RIGHT:
			case SHIFT_RIGHT:
				inv.getLocation().getWorld().playSound(inv.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1, 1);
				player.setItemOnCursor(ench.getCombinedItem());
				inv.setContents(new ItemStack[3]);
				AbilityUtils.dropExperience(inv.getLocation().clone().add(new Location(inv.getLocation().getWorld(), 0.5, 0.5, 0.5)), ench.getExperience());
				break;
			case SHIFT_LEFT:
				inv.getLocation().getWorld().playSound(inv.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1, 1);
				HashMap<Integer, ItemStack> items = player.getInventory().addItem(ench.getCombinedItem());
				if (!items.isEmpty()) return;
				inv.setContents(new ItemStack[3]);
				AbilityUtils.dropExperience(inv.getLocation().clone().add(new Location(inv.getLocation().getWorld(), 0.5, 0.5, 0.5)), ench.getExperience());
				break;
			default:
				break;
		}
	}
}
