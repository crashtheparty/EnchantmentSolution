package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class PiglinTrade implements Listener {

	@EventHandler
	public void onPiglinTrade(EntityDropItemEvent event) {
		if (event.getEntityType() == new MobData("piglin").getEntity() && ConfigString.USE_PIGLIN_TRADES.getBoolean()) {
			ItemStack item = event.getItemDrop().getItemStack();
			if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
				// already checked if using piglin trades, so don't check chest loot use
				ItemStack generate = GenerateUtils.generateChestLoot(null, item.clone(), "piglin_trade", EnchantmentLocation.PIGLIN, false);
				event.getItemDrop().setItemStack(generate);
			}
		}
	}
}