package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class SacrificeListener extends EnchantmentListener{

	private static List<UUID> SACRIFICE_ADVANCEMENT = new ArrayList<UUID>();
	
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
								if(((Damageable) proj.getShooter()).getHealth() < 0) {
									SACRIFICE_ADVANCEMENT.add(player.getUniqueId());
								}
			                }
						 }else{
							 if (nEvent.getDamager() instanceof Damageable) {
								((Damageable) nEvent.getDamager()).damage(damage);
								if(((Damageable) nEvent.getDamager()).getHealth() < 0) {
									SACRIFICE_ADVANCEMENT.add(player.getUniqueId());
								}
			                }
						 }
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(SACRIFICE_ADVANCEMENT.contains(event.getPlayer().getUniqueId())) {
			AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.DIVINE_RETRIBUTION, "retribution");
		}
	}
}
