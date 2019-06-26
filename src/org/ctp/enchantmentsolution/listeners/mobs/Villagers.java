package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class Villagers implements Listener{
	
	@EventHandler
	public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
		MerchantRecipe current = event.getRecipe();
		ItemStack result = current.getResult();
		if(result.hasItemMeta() && (result.getItemMeta().hasEnchants() || result.getType() == Material.ENCHANTED_BOOK)) {
			if(ConfigUtils.getVillagerTrades()) {
				MerchantRecipe newRecipe = PlayerLevels.generateVillagerTrade(current);
				if(newRecipe != null) {
					event.setRecipe(newRecipe);
				}
			}
		}
	}

}
