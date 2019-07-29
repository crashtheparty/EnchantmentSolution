package org.ctp.enchantmentsolution.listeners.vanilla;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class EnchantmentListener implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPrepareEnchant(PrepareItemEnchantEvent event) {
		Player player = event.getEnchanter();
		int bookshelves = Enchantments.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		PlayerLevels levels = PlayerLevels.getPlayerLevels(bookshelves, player, item.getType());
		if(levels == null) {
			levels = new PlayerLevels(bookshelves, player, item.getType());
		}
		if(event.getOffers()[0] == null) return;
		for(int i = 0; i < event.getOffers().length; i++) {
			for(EnchantmentLevel ench : levels.getEnchants().get(i)) {
				event.getOffers()[i].setCost(PlayerLevels.getIntList(player, bookshelves).get(i));
				if(!(ench.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
					event.getOffers()[i].setEnchantment(ench.getEnchant().getRelativeEnchantment());
					event.getOffers()[i].setEnchantmentLevel(ench.getLevel());
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEnchantItem(EnchantItemEvent event) {
		Player player = event.getEnchanter();
		int bookshelves = Enchantments.getBookshelves(event.getEnchantBlock().getLocation());
		ItemStack item = event.getItem();
		PlayerLevels levels = PlayerLevels.getPlayerLevels(bookshelves, player, item.getType());
		for(int i = 0; i < PlayerLevels.getIntList(player, bookshelves).size(); i++){
			Integer integer = PlayerLevels.getIntList(player, bookshelves).get(i);
			if(integer == event.getExpLevelCost()) {
				List<EnchantmentLevel> enchantments = levels.getEnchants().get(i);
				event.getEnchantsToAdd().clear();
				item = Enchantments.addEnchantmentsToItem(item, enchantments);
				if(item.getType() == Material.BOOK && ConfigUtils.getEnchantedBook()) {
					item = Enchantments.convertToEnchantedBook(item);
				}
				event.getInventory().setItem(0, item);
				event.getInventory().removeItem(new ItemStack(Material.LAPIS_LAZULI, i + 1));
				break;
			}
		}
		PlayerLevels.removePlayerLevels(player);
	}
	
}
