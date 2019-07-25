package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class MagicGuardListener extends EnchantmentListener implements Runnable{
	
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
	public void onEntityDamage(EntityDamageEvent event) {
		if(!canRun(DefaultEnchantments.MAGIC_GUARD, event)) return;
		if(event.getCause().equals(DamageCause.MAGIC) || event.getCause().equals(DamageCause.POISON)) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.MAGIC_GUARD, event)) return;
		if(event.getDamager() instanceof AreaEffectCloud) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack shield = player.getInventory().getItemInOffHand();
			if(shield.getType().equals(Material.SHIELD)) {
				if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
					for(PotionEffect effect : player.getActivePotionEffects()) {
						if(ItemUtils.getBadPotions().contains(effect.getType())) {
							player.removePotionEffect(effect.getType());
						}
					}
				}
			}
		}
	}

}
