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
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.generate.AnvilEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.AnvilEnchantments.RepairType;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class AnvilListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPrepareAnvil(PrepareAnvilEvent event) {
		ItemStack first = event.getInventory().getItem(0);
		ItemStack second = event.getInventory().getItem(1);
		if (event.getView().getPlayer() instanceof Player) {
			ItemStack result = event.getResult();
			String name = null;
			if (result != null && result.hasItemMeta() && first != null && first.hasItemMeta() && !first.getItemMeta().getDisplayName().equals(name)) name = result.getItemMeta().getDisplayName();
			AnvilEnchantments anvil = AnvilEnchantments.getAnvilEnchantments((Player) event.getView().getPlayer(), first, second, name);
			ItemStack combineItem = null;
			if ((anvil.getRepairType() == RepairType.COMBINE || anvil.getRepairType() == RepairType.REPAIR) && anvil.canCombine()) combineItem = anvil.getCombinedItem();
			else if (anvil.getRepairType() == RepairType.RENAME) {
				ItemStack newFirst = anvil.getCombinedItem();
				if (newFirst == null) return;
				ItemMeta firstMeta = newFirst.getItemMeta();
				if (firstMeta.hasDisplayName() && !firstMeta.getDisplayName().equals(event.getInventory().getRenameText()) || !firstMeta.hasDisplayName() && event.getInventory().getRenameText() != null && !event.getInventory().getRenameText().equals("")) {
					firstMeta.setDisplayName(event.getInventory().getRenameText());
					newFirst.setItemMeta(firstMeta);
					combineItem = newFirst;
				}
			}
			if (combineItem != null) {
				event.setResult(combineItem);
				EnchantmentSolution.getPlugin().getServer().getScheduler().runTask(EnchantmentSolution.getPlugin(), () -> event.getInventory().setRepairCost(anvil.getRepairCost()));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.ANVIL) {
			AnvilInventory inv = (AnvilInventory) event.getInventory();
			ItemStack first = inv.getItem(0);
			ItemStack second = inv.getItem(1);
			if (event.getWhoClicked() instanceof Player) {
				Player player = (Player) event.getWhoClicked();
				ItemStack result = inv.getItem(2);
				String name = null;
				if (result != null && result.hasItemMeta() && first != null && first.hasItemMeta() && !first.getItemMeta().getDisplayName().equals(name)) name = result.getItemMeta().getDisplayName();
				if (EnchantmentSolution.getPlugin().getInventory(player) != null) return;
				AnvilEnchantments anvil = AnvilEnchantments.getAnvilEnchantments(player, first, second, name);
				if ((anvil.getRepairType() == RepairType.COMBINE || anvil.getRepairType() == RepairType.REPAIR) && anvil.canCombine() && event.getSlot() == 2 && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) {
					event.setCancelled(true);
					ItemStack combinedItem = anvil.getCombinedItem();
					if (combinedItem != null) {
						int cost = anvil.getRepairCost();
						if (cost > ConfigString.MAX_REPAIR_LEVEL.getInt()) {
							HashMap<String, Object> loreCodes = ChatUtils.getCodes();
							loreCodes.put("%repairCost%", cost);
							Chatable.get().sendMessage(player, Chatable.get().getMessage(loreCodes, "anvil.cannot-repair"));
							return;
						}
						if (player.getLevel() >= cost) switch (event.getClick()) {
							case LEFT:
							case RIGHT:
							case SHIFT_RIGHT:
								player.setItemOnCursor(combinedItem);
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								if (anvil.getRepairType() == RepairType.REPAIR || anvil.getRepairType() == RepairType.STICKY_REPAIR) inv.setItem(1, anvil.getItemLeftover());
								if (anvil.getRepairType() == RepairType.STICKY_REPAIR) AdvancementUtils.awardCriteria(player, ESAdvancement.SIMPLE_REPAIR, "repair");
								break;
							case SHIFT_LEFT:
								HashMap<Integer, ItemStack> items = player.getInventory().addItem(combinedItem);
								if (!items.isEmpty()) return;
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								if (anvil.getRepairType() == RepairType.REPAIR || anvil.getRepairType() == RepairType.STICKY_REPAIR) inv.setItem(1, anvil.getItemLeftover());
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								if (anvil.getRepairType() == RepairType.STICKY_REPAIR) AdvancementUtils.awardCriteria(player, ESAdvancement.SIMPLE_REPAIR, "repair");
								break;
							default:
								break;

						}
						else {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", cost);
							Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "anvil.message-cannot-combine-cost"));
						}
					}
				}

				if (anvil.getRepairType() == RepairType.RENAME && event.getSlot() == 2 && (event.getCursor() == null || MatData.isAir(event.getCursor().getType()))) {
					if (first == null) return;
					ItemMeta firstMeta = first.getItemMeta();
					if (firstMeta.hasDisplayName() && !firstMeta.getDisplayName().equals(inv.getRenameText()) || !firstMeta.hasDisplayName() && inv.getRenameText() != null && !inv.getRenameText().equals("")) {
						event.setCancelled(true);
						firstMeta.setDisplayName(inv.getRenameText());
						first.setItemMeta(firstMeta);
						int cost = 1;
						if (player.getLevel() >= cost) switch (event.getClick()) {
							case LEFT:
							case RIGHT:
							case SHIFT_RIGHT:
								player.setItemOnCursor(first);
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							case SHIFT_LEFT:
								HashMap<Integer, ItemStack> items = player.getInventory().addItem(first);
								if (!items.isEmpty()) return;
								player.setLevel(player.getLevel() - cost);
								inv.setContents(new ItemStack[3]);
								AnvilUtils.checkAnvilBreak(player, inv.getLocation().getBlock(), null);
								break;
							default:
								break;

						}
						else {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", cost);
							Chatable.get().sendMessage(player, Chatable.get().getMessage(codes, "anvil.message-cannot-combine-cost"));
						}
					}
				}
			}
		}
	}
}
