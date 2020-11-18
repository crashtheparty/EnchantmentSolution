package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.ItemAddEvent;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onItemAdd(ItemAddEvent event) {
		ItemStack item = event.getItem();
		checkEnchants(event.getPlayer(), item);
		event.setItem(item);
	}

	public static int checkEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return 0;
		int changed = 0;
		if (PersistenceNMS.checkItem(item)) changed = 1;
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (!enchant.isEnabled() && ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
				changed = 1;
				item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
			}
			if (EnchantmentUtils.hasEnchantment(item, enchant.getRelativeEnchantment())) {
				boolean lower = false;
				int maxLevel = enchant.getMaxLevel();
				if (player != null) {
					lower = player.hasPermission("enchantmentsolution.enchantments.lower-levels");
					maxLevel = enchant.getMaxLevel(player);
				}
				if (lower && maxLevel < EnchantmentUtils.getLevel(item, enchant.getRelativeEnchantment())) {
					if (maxLevel == 0) item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
					else
						item = EnchantmentUtils.addEnchantmentToItem(item, enchant, maxLevel);
					changed = 1;
				} else { /* placeholder */ }
			}
		}
		return changed;
	}

}
