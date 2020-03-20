package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.nms.EnchantItemCriterion;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class EnchantmentListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPrepareEnchant(PrepareItemEnchantEvent event) {
		Player player = event.getEnchanter();
		int bookshelves = LocationUtils.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		TableEnchantments table = TableEnchantments.getTableEnchantments(player, item, bookshelves, false);
		if (event.getOffers()[0] == null) return;
		for(int i = 0; i < event.getOffers().length; i++)
			for(EnchantmentLevel ench: table.getEnchantments(new ItemData(item))[i].getEnchantments()) {
				event.getOffers()[i].setCost(table.getLevelList().getList()[i].getLevel());
				if (!(ench.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
					event.getOffers()[i].setEnchantment(ench.getEnchant().getRelativeEnchantment());
					event.getOffers()[i].setEnchantmentLevel(ench.getLevel());
				}
			}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchantItem(EnchantItemEvent event) {
		Player player = event.getEnchanter();
		int bookshelves = LocationUtils.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		TableEnchantments table = TableEnchantments.getTableEnchantments(player, item, bookshelves, false);
		for(int i = 0; i < table.getLevelList().getList().length; i++) {
			Integer integer = table.getLevelList().getList()[i].getLevel();
			if (integer == event.getExpLevelCost()) {
				List<EnchantmentLevel> enchantments = table.getEnchantments(new ItemData(item))[i].getEnchantments();
				event.getEnchantsToAdd().clear();
				item = ItemUtils.addEnchantmentsToItem(item, enchantments);
				if (item.getType() == Material.BOOK && ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) item = ItemUtils.convertToEnchantedBook(item);
				if (player.getGameMode() != GameMode.CREATIVE) player.setLevel(player.getLevel() - i - 1);
				event.getInventory().setItem(0, item);
				event.getInventory().removeItem(new ItemStack(Material.LAPIS_LAZULI, i + 1));
				player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
				player.setStatistic(Statistic.ITEM_ENCHANTED, player.getStatistic(Statistic.ITEM_ENCHANTED) + 1);
				EnchantItemCriterion.enchantItemTrigger(player, item);
				if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendEnchantAction(player, item, item, enchantments);
				break;
			}
		}
		TableEnchantments.removeTableEnchantments(player);
	}

}
