package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class SandVeilListener extends EnchantmentListener{
	
	private static List<EntityAccuracy> ENTITIES = new ArrayList<EntityAccuracy>();
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.SAND_VEIL, event)) return;
		Entity damager = event.getDamager();
		if(damager instanceof Projectile) {
			if(((Projectile) damager).getShooter() instanceof Entity) {
				damager = (Entity) ((Projectile) damager).getShooter();
			} else {
				return;
			}
		}
		EntityAccuracy ea = null;
		for(EntityAccuracy entity : ENTITIES) {
			if(entity.getEntity().equals(damager)) {
				ea = entity;
				break;
			}
		}
		if(ea != null) {
			double accuracy = ea.getAccuracy();
			double random = Math.random();
			if(accuracy <= random) {
				event.setCancelled(true);
				event.getEntity().getWorld().spawnParticle(Particle.BLOCK_DUST, event.getEntity().getLocation(), 50, 0.2, 2, 0.2);
			}
		}
		if(!event.isCancelled()) {
			if(damager instanceof Player && event.getEntity() instanceof LivingEntity) {
				Player player = (Player) damager;
				ItemStack item = player.getInventory().getItemInMainHand();
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SAND_VEIL)) {
					double accuracy = 1 - Enchantments.getLevel(item, DefaultEnchantments.SAND_VEIL) * .05;
					LivingEntity entity = (LivingEntity) event.getEntity();
					ea = null;
					for(EntityAccuracy entityAccuracy : ENTITIES) {
						if(entityAccuracy.getEntity().equals(entity)) {
							ea = entityAccuracy;
							break;
						}
					}
					if(ea == null) {
						ea = new EntityAccuracy(entity, accuracy);
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), ea, 1l, 1l);
						ea.setScheduler(scheduler);
						ENTITIES.add(ea);
					} else {
						ea.resetRun();
						ea.setAccuracy(accuracy);
					}
				}
			}
		}
	}
	
	public class EntityAccuracy implements Runnable{
		
		private LivingEntity entity;
		private double accuracy;
		private int run, scheduler;
		
		public EntityAccuracy(LivingEntity entity, double accuracy) {
			this.setEntity(entity);
			this.setAccuracy(accuracy);
			resetRun();
		}

		@Override
		public void run() {
			run --;
			if(run <= 0) {
				Bukkit.getScheduler().cancelTask(scheduler);
				ENTITIES.remove(this);
			}
		}

		public LivingEntity getEntity() {
			return entity;
		}

		public void setEntity(LivingEntity entity) {
			this.entity = entity;
		}

		public double getAccuracy() {
			return accuracy;
		}

		public void setAccuracy(double accuracy) {
			this.accuracy = accuracy;
		}
		
		public void resetRun() {
			run = 160;
		}

		public int getScheduler() {
			return scheduler;
		}

		public void setScheduler(int scheduler) {
			this.scheduler = scheduler;
		}
	}

}
