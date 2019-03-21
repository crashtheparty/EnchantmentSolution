package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;

import java.util.HashMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class CurseOfLagListener extends EnchantmentListener{
	
	private static HashMap<OfflinePlayer, Long> APRIL_FOOLS = new HashMap<OfflinePlayer, Long>();

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.CURSE_OF_LAG, event)) return;
		Player player = event.getPlayer();
		if(player != null) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG) || EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()) {
				int random = (int) ((Math.random() * 5) + 2);
				int numParticles = (int) ((Math.random() * 400) + 11);
				
				for(int i = 0; i < random; i++) {
					Particle particle = generateParticle();
					player.getWorld().spawnParticle(particle, player.getLocation(), numParticles, 0.5, 2, 0.5);
				}
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
				if(EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()){
					long sys = System.currentTimeMillis() / 1000;
					if(!(APRIL_FOOLS.containsKey(player)) || !(APRIL_FOOLS.get(player) >= sys)) {
						sys += 300;
						ChatUtils.sendMessage(player, "April Fools!");
						if(player.hasPermission("enchantmentsolution.command.edit")) {
							ChatUtils.sendMessage(player, "You may remove the lag spike by disabling april_fools_day in config.yml.");
						}
						APRIL_FOOLS.put(player, sys);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.CURSE_OF_LAG, event)) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG) || EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()) {
				int random = (int) ((Math.random() * 5) + 2);
				int numParticles = (int) ((Math.random() * 400) + 11);
				
				for(int i = 0; i < random; i++) {
					Particle particle = generateParticle();
					player.getWorld().spawnParticle(particle, player.getLocation(), numParticles, 0.5, 2, 0.5);
				}
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
				if(EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()){
					long sys = System.currentTimeMillis() / 1000;
					if(!(APRIL_FOOLS.containsKey(player)) || !(APRIL_FOOLS.get(player) >= sys)) {
						sys += 300;
						ChatUtils.sendMessage(player, "April Fools!");
						if(player.hasPermission("enchantmentsolution.command.edit")) {
							ChatUtils.sendMessage(player, "You may remove the lag spike by disabling april_fools_day in config.yml.");
						}
						APRIL_FOOLS.put(player, sys);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		if(!canRun(DefaultEnchantments.CURSE_OF_LAG, event)) return;
		Projectile proj = event.getEntity();
		if(proj.getShooter() instanceof Player){
			Player player = (Player) proj.getShooter();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG) || EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()) {
				int random = (int) ((Math.random() * 5) + 2);
				int numParticles = (int) ((Math.random() * 400) + 11);
				
				for(int i = 0; i < random; i++) {
					Particle particle = generateParticle();
					player.getWorld().spawnParticle(particle, player.getLocation(), numParticles, 0.5, 2, 0.5);
				}
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
				if(EnchantmentSolution.getPlugin().getConfigFiles().getAprilFools()){
					long sys = System.currentTimeMillis() / 1000;
					if(!(APRIL_FOOLS.containsKey(player)) || !(APRIL_FOOLS.get(player) >= sys)) {
						sys += 300;
						ChatUtils.sendMessage(player, "April Fools!");
						if(player.hasPermission("enchantmentsolution.command.edit")) {
							ChatUtils.sendMessage(player, "You may remove the lag spike by disabling april_fools_day in config.yml.");
						}
						APRIL_FOOLS.put(player, sys);
					}
				}
			}
		}
	}
	
	private Particle generateParticle() {
		Particle particle = null;
		int tries = 0;
		while(particle == null && tries < 10) {
			int particleType = (int) (Math.random() * Particle.values().length);
			particle = Particle.values()[particleType];
			if(!particle.getDataType().isAssignableFrom(Void.class)) {
				particle = null;
			}
			
		}
		return particle;
	}
	
}
