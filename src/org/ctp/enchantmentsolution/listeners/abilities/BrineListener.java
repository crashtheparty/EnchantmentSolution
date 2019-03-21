package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class BrineListener extends EnchantmentListener{

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.BRINE, event)) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.BRINE)) {
				Entity damaged = event.getEntity();
				if(damaged instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) damaged;
					AttributeInstance a = living.getAttribute(Attribute.GENERIC_MAX_HEALTH);
					double maxHealth = a.getValue();
					double health = living.getHealth();
					if(health <= maxHealth / 2) {
						event.setDamage(event.getDamage() * 2);
					}
				}
			}
		}
	}
}
