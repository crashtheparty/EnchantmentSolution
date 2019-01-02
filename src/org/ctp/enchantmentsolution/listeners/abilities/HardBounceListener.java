package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class HardBounceListener implements Listener{
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Entity e = event.getHitEntity();
		Projectile p = event.getEntity();
		if(e != null && e instanceof Player) {
			Player player = (Player) e;
			if(player.isBlocking()) {
				ItemStack shield = player.getInventory().getItemInMainHand();
				if(shield.getType() != Material.SHIELD) {
					shield = player.getInventory().getItemInOffHand();
				}
				if(shield != null && Enchantments.hasEnchantment(shield, DefaultEnchantments.HARD_BOUNCE)) {
					int level = Enchantments.getLevel(shield, DefaultEnchantments.HARD_BOUNCE);
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.PLUGIN, new Runnable() {
						@Override
						public void run() {
							Vector v = p.getVelocity().clone();
							if(v.getY() < 0) {
								v.setY(- v.getY());
							} else if (v.getY() == 0) {
								v.setY(0.1);
							}
							v.multiply(2 + 2 * level);
							p.setVelocity(v);
						}
						
					}, 1l);
				}
			}
		}
	}

}
