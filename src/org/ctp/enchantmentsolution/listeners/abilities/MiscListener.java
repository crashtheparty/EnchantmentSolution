package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class MiscListener extends EnchantmentListener{
	
	@EventHandler
	public void onEntityPotionEffect(EntityPotionEffectEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack shield = player.getInventory().getItemInOffHand();
			if(shield != null && Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
				if(event.getAction() == Action.ADDED || event.getAction() == Action.CHANGED) {
					if(ItemUtils.getBadPotions().contains(event.getModifiedType())) {
						event.setCancelled(true);
						if(event.getCause() == Cause.FOOD) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.THAT_FOOD_IS_FINE, "food");
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		if(!canRun(DefaultEnchantments.TANK, event)) return;
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
