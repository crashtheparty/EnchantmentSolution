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
		checkEnchants(event.getPlayer(), event.getItem());
	}

	public static ItemStack checkEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return item;
		item = PersistenceNMS.checkItem(item);
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (!enchant.isEnabled() && ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
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
				} else { /* placeholder */ }
			}
		}
		return item;
	}

}
