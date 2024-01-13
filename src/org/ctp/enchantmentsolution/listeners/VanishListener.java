package org.ctp.enchantmentsolution.listeners;

import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class VanishListener implements Listener {

	@EventHandler
	public void onItemEquip(EquipEvent event) {
		ItemStack item = event.getNewItem();
		checkEnchants(event.getEntity(), item);
	}

	public static int checkEnchants(HumanEntity player, ItemStack item) {
		if (item == null || item.getItemMeta() == null) return 0;
		int changed = 0;
		if (PersistenceUtils.checkItem(item)) changed = 1;
		List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);

		for(EnchantmentLevel level: levels) {
			CustomEnchantment enchant = level.getEnchant();
			if (enchant == null) continue;
			if (!enchant.isEnabled() && ConfigString.DISABLE_ENCHANT_METHOD.getString().equals("vanish")) {
				changed = 1;
				item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
			}
			if (!EnchantmentUtils.hasEnchantment(item, enchant.getRelativeEnchantment())) {
				item = EnchantmentUtils.removeEnchantmentFromItem(item, enchant);
				item = EnchantmentUtils.addEnchantmentToItem(item, enchant, level.getLevel());
			}
		}
		return changed;
	}

}
