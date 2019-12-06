package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.enchantments.generate.VillagerEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class Villagers implements Listener{

	@EventHandler
	public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
		MerchantRecipe current = event.getRecipe();
		ItemStack result = current.getResult();
		if(result.hasItemMeta() && (result.getItemMeta().hasEnchants() || result.getType() == Material.ENCHANTED_BOOK)) {
			if(ConfigString.VILLAGER_TRADES.getBoolean()) {
				MerchantRecipe newRecipe = VillagerEnchantments.getVillagerEnchantments(result, current).getMerchantRecipe();
				if(newRecipe != null) {
					event.setRecipe(newRecipe);
				}
			}
		}
	}

}
