package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class SplatterFestListener implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.SPLATTER_FEST)) return;
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SPLATTER_FEST)) {
				boolean removed = false;
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
					player.launchProjectile(Egg.class);
					int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
					double chance = (1.0D) / (unbreaking + 1.0D);
					double random = Math.random();
					if(chance > random) {
						DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + 1);
						if(DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
							player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						}
					}
				}
			}
		}
	}
}
