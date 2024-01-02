package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.enchantments.generate.VillagerEnchantments;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class Villagers implements Listener {

	@EventHandler
	public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
		MerchantRecipe current = event.getRecipe();
		ItemStack result = current.getResult();
		if (result.hasItemMeta() && (result.getItemMeta().hasEnchants() || result.getType() == Material.ENCHANTED_BOOK) && ConfigString.USE_ALL_VILLAGER_TRADES.getBoolean()) {
			VillagerEnchantments enchantments = GenerateUtils.generateVillagerEnchantments(result, current);
			if (enchantments == null) return;
			MerchantRecipe newRecipe = enchantments.getMerchantRecipe();
			if (newRecipe != null) event.setRecipe(newRecipe);
		}
	}

}
