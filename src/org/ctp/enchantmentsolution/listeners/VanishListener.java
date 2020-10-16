package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ItemAddEvent;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onItemAdd(ItemAddEvent event) {
		checkEnchants(event.getPlayer(), event.getItem());
	}

	public static int checkEnchants(Player player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return 0;
		int changed = 0;
		if (PersistenceNMS.checkItem(item, null)) changed = 1;
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (!enchant.isEnabled() && ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
				changed = 1;
				item = ItemUtils.removeEnchantmentFromItem(item, enchant);
			}
			if (ItemUtils.hasEnchantment(item, enchant.getRelativeEnchantment())) {
				boolean lower = false;
				int maxLevel = enchant.getMaxLevel();
				if (player != null) {
					lower = player.hasPermission("enchantmentsolution.enchantments.lower-levels");
					maxLevel = enchant.getMaxLevel(player);
				}
				if (lower && maxLevel < ItemUtils.getLevel(item, enchant.getRelativeEnchantment())) {
					if (maxLevel == 0) item = ItemUtils.removeEnchantmentFromItem(item, enchant);
					else
						item = ItemUtils.addEnchantmentToItem(item, enchant, maxLevel);
					changed = 1;
				} else { /* placeholder */ }
			}
		}
		return changed;
	}

}
