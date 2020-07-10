package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.MobData;
import org.ctp.enchantmentsolution.enums.VanillaEnchantment;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class PiglinTrade implements Listener {

	@EventHandler
	public void onPiglinTrade(EntityDropItemEvent event) {
		if (event.getEntityType() == new MobData("piglin").getEntity()) {
			ItemStack item = event.getItemDrop().getItemStack();
			if (item.hasItemMeta() && ItemUtils.hasEnchantment(item, VanillaEnchantment.SOUL_SPEED.getEnchantment())) event.getItemDrop().setItemStack(GenerateUtils.generatePiglinLoot(item));
		}
	}
}
