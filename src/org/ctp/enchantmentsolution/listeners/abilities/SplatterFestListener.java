package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class SplatterFestListener extends EnchantmentListener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!canRun(DefaultEnchantments.SPLATTER_FEST, event)) return;
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SPLATTER_FEST)) {
				boolean removed = false;
				if(player.getGameMode().equals(GameMode.CREATIVE)) removed = true;
				for(int i = 0; i < player.getInventory().getSize(); i++) {
					if(removed) break;
					ItemStack removeItem = player.getInventory().getItem(i);
					if(removeItem != null && removeItem.getType().equals(Material.EGG)) {
						if(removeItem.getAmount() - 1 <= 0) {
							player.getInventory().setItem(i, new ItemStack(Material.AIR));
							removed = true;
						} else {
							removeItem.setAmount(removeItem.getAmount() - 1);
							removed = true;
						}
					}
				}
				if(!removed) {
					ItemStack remove = player.getInventory().getItemInOffHand();
					if(player.getInventory().getItemInOffHand().getType().equals(Material.EGG)) {
						if(remove.getAmount() - 1 <= 0) {
							player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
							removed = true;
						} else {
							remove.setAmount(remove.getAmount() - 1);
							removed = true;
						}
					}
				}
				if(removed) {
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					player.incrementStatistic(Statistic.USE_ITEM, Material.EGG);
					player.launchProjectile(Egg.class);
					super.damageItem(player, item);
				}
			}
		}
	}
}
