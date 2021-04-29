package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.*;
import org.ctp.enchantmentsolution.events.ESEnchantItemEvent;
import org.ctp.enchantmentsolution.nms.EnchantItemCriterion;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class EnchantmentListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPrepareEnchant(PrepareItemEnchantEvent event) {
		Player player = event.getEnchanter();
		int bookshelves = EnchantmentUtils.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		TableEnchantments table = TableEnchantments.getTableEnchantments(player, item, bookshelves);
		if (event.getOffers()[0] == null) return;
		EnchantmentList[] lists = table.getEnchantments(new ItemData(item));
		for(int i = 0; i < event.getOffers().length; i++) {
			EnchantmentOffer offer = event.getOffers()[i];
			if (offer == null) continue;
			EnchantmentList list = lists[i];
			if (list == null) continue;
			for(EnchantmentLevel ench: list.getEnchantments()) {
				if (ench == null || ench.getEnchant() == null) continue;
				LevelList levelList = table.getLevelList();
				if (levelList == null) continue;
				Level level = levelList.getList()[i];
				if (level == null) continue;
				offer.setCost(level.getLevel());
				if (!(ench.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
					offer.setEnchantment(ench.getEnchant().getRelativeEnchantment());
					offer.setEnchantmentLevel(ench.getLevel());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchantItem(EnchantItemEvent event) {
		if (event instanceof ESEnchantItemEvent) return;
		Player player = event.getEnchanter();
		int bookshelves = EnchantmentUtils.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		TableEnchantments table = TableEnchantments.getTableEnchantments(player, item, bookshelves);
		for(int i = 0; i < table.getLevelList().getList().length; i++) {
			Integer integer = table.getLevelList().getList()[i].getLevel();
			if (integer == event.getExpLevelCost()) {
				List<EnchantmentLevel> enchantments = table.getEnchantments(new ItemData(item))[i].getEnchantments();
				event.getEnchantsToAdd().clear();
				Map<Enchantment, Integer> defaultLevels = new HashMap<Enchantment, Integer>();
				for(EnchantmentLevel l: enchantments)
					defaultLevels.put(l.getEnchant().getRelativeEnchantment(), l.getLevel());
				ESEnchantItemEvent enchantEvent = new ESEnchantItemEvent(player, player.getOpenInventory(), event.getEnchantBlock(), item, i, defaultLevels, i);
				try {
					Bukkit.getPluginManager().callEvent(enchantEvent);
				} catch (Exception ex) {
					Chatable.sendDebug("An issue occurred with calling an EnchantItemEvent (Vanilla GUI): " + ex.getMessage(), java.util.logging.Level.SEVERE);
				}
				try {
					if (item.getType() == Material.BOOK && ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) item = EnchantmentUtils.convertToEnchantedBook(item);
					player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
					if (player.getGameMode() != GameMode.CREATIVE) player.setLevel(player.getLevel() - i - 1);
					ItemStack lapis = event.getInventory().getItem(1).clone();
					lapis.setAmount(i + 1);
					event.getInventory().removeItem(lapis);
					item = EnchantmentUtils.addEnchantmentsToItem(item, enchantments);
					event.getInventory().setItem(0, item);
					player.setStatistic(Statistic.ITEM_ENCHANTED, player.getStatistic(Statistic.ITEM_ENCHANTED) + 1);
					EnchantItemCriterion.enchantItemTrigger(player, item);
					if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendEnchantAction(player, item, item, enchantments);
				} catch (Exception ex) {
					Chatable.sendDebug("An issue occurred with enchanting items (Vanilla GUI): " + ex.getMessage(), java.util.logging.Level.SEVERE);

				}
				TableEnchantments.removeTableEnchantments(player);
				return;
			}
		}
	}

}
