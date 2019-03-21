package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class SacrificeListener extends EnchantmentListener{

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(!canRun(DefaultEnchantments.SACRIFICE, event)) return;
		Player player = event.getEntity();
		ItemStack chest = player.getInventory().getChestplate();
		if(chest != null) {
			if(Enchantments.hasEnchantment(chest, DefaultEnchantments.SACRIFICE)) {
				int level = Enchantments.getLevel(chest, DefaultEnchantments.SACRIFICE);
				int playerLevel = player.getLevel();
				double damage = ((double) playerLevel) / (8.0D / level);
				Entity killer = player.getKiller();
				if(killer != null) {
					if(killer instanceof Damageable) {
						((Damageable) killer).damage(damage);
					}
				}else{
					if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
						 EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event
				                    .getEntity().getLastDamageCause();
						 if(nEvent.getDamager() instanceof Projectile) {
							 Projectile proj = (Projectile) nEvent.getDamager();
							 if (proj.getShooter() instanceof Damageable) {
								((Damageable) proj.getShooter()).damage(damage);
			                }
						 }else{
							 if (nEvent.getDamager() instanceof Damageable) {
								((Damageable) nEvent.getDamager()).damage(damage);
			                }
						 }
					}
				}
			}
		}
	}
}
