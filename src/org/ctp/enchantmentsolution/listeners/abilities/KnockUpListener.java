package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class KnockUpListener implements Listener{

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.KNOCKUP)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if(attacker instanceof Player){
			Player player = (Player) attacker;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(attackItem, DefaultEnchantments.KNOCKUP)){
				int level = Enchantments.getLevel(attackItem, DefaultEnchantments.KNOCKUP);
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable(){

					@Override
					public void run() {
						double levelMultiplier = 0.18;
						attacked.setVelocity(
								new Vector(attacked.getVelocity().getX(), 0.275184010449 + levelMultiplier * level, attacked.getVelocity().getZ()));
					}
					
				}, 0l);
			}
		}
	}
}
