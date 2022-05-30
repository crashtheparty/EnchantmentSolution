package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class PiglinTrade implements Listener {

	@EventHandler
	public void onPiglinTrade(EntityDropItemEvent event) {
		if (event.getEntityType() == new MobData("piglin").getEntity() && ConfigString.PIGLIN_TRADES.getBoolean()) {
			ItemStack item = event.getItemDrop().getItemStack();
			if (item.hasItemMeta() && EnchantmentUtils.hasEnchantment(item, Enchantment.SOUL_SPEED)) {
				ItemStack generate = GenerateUtils.generatePiglinLoot(item.clone());
				event.getItemDrop().setItemStack(generate);
			}
		}
	}
}