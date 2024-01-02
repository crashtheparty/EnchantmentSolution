package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.events.damage.SandVeilMissEvent;
import org.ctp.enchantmentsolution.utils.player.ESEntity;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class CustomPotionEffectListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Projectile) {
			Projectile p = (Projectile) damager;
			if (p.getShooter() instanceof LivingEntity) damager = (LivingEntity) p.getShooter();
		}
		Entity damaged = event.getEntity();

		if (damager instanceof LivingEntity && damaged instanceof LivingEntity) {
			ESEntity esEntity = EnchantmentSolution.getESEntity((LivingEntity) damager);
			if (esEntity.hasCustomPotionEffect(CustomPotionEffectType.SANDY_EYES)) {
				int level = esEntity.getHighestPotionEffect(CustomPotionEffectType.SANDY_EYES).getAmplifier() + 1;
				double accuracy = Math.max(0, 1 - level * .05);
				if (Math.random() > accuracy) {
					SandVeilMissEvent sandVeil = new SandVeilMissEvent((LivingEntity) damaged, level, (LivingEntity) damager, event.getDamage(), true);
					Bukkit.getPluginManager().callEvent(sandVeil);

					if (!sandVeil.isCancelled()) {
						event.setCancelled(true);
						if (sandVeil.isParticles()) event.getEntity().getWorld().spawnParticle(Particle.CLOUD, event.getEntity().getLocation(), 200, 0.2, 0.2, 0.2);
					}
				}
			}
		}

	}

}
