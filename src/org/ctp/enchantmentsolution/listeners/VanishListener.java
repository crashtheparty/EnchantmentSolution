package org.ctp.enchantmentsolution.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.ItemAddEvent;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onItemAdd(ItemAddEvent event) {
		ItemStack item = event.getItem();
		item = checkEnchants(event.getPlayer(), item);
		event.setItem(item);
	}

	public static ItemStack checkEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return item;
		item = PersistenceNMS.checkItem(item);
		if (ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) for(CustomEnchantment enchant: RegisterEnchantments.getDisabledEnchantments())
			item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
		if (player != null && player.hasPermission("enchantmentsolution.enchantments.lower-levels") && EnchantmentUtils.getEnchantmentLevels(item).size() > 0) {
			List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);
			for(EnchantmentLevel level: levels) {
				CustomEnchantment enchant = level.getEnchant();
				int maxLevel = enchant.getMaxLevel(player);
				if (maxLevel < level.getLevel()) {
					if (maxLevel == 0) item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
					else
						item = EnchantmentUtils.addEnchantmentToItem(item, enchant, maxLevel);
				} else { /* placeholder */ }
			}
		}
		return item;
	}

}
