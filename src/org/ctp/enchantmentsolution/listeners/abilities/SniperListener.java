package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.LocationUtils;

public class SniperListener extends EnchantmentListener{
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		if(!canRun(DefaultEnchantments.SNIPER, event)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Arrow){
			Arrow arrow = (Arrow) proj;
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				ItemStack bow = player.getInventory().getItemInMainHand();
				if(bow != null && Enchantments.hasEnchantment(bow, DefaultEnchantments.SNIPER)){
					int level = Enchantments.getLevel(bow, DefaultEnchantments.SNIPER);
					arrow.setMetadata("sniper", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 
							LocationUtils.locationToString(player.getLocation())));
					double speed = 1 + (0.1 * level * level);
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable(){
						@Override
						public void run() {
							arrow.setVelocity(arrow.getVelocity().multiply(speed));
						}
							
					}, 0l);
				}
			}
		}
	}
}
