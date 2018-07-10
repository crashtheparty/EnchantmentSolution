package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class TankListener implements Listener{

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TANK)) return;
		ItemStack item = event.getItem();
		if(Enchantments.hasEnchantment(item, DefaultEnchantments.TANK)) {
			int level = Enchantments.getLevel(item, DefaultEnchantments.TANK);
			double chance = (level * 1.0D) / (level + 1);
			int damage = event.getDamage();
			for(int i = 0; i < event.getDamage(); i++) {
				double random = Math.random();
				if(chance > random) {
					damage --;
				}
			}
			event.setDamage(damage);
		}
	}
}
