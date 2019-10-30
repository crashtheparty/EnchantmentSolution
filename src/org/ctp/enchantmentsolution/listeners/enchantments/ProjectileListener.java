package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.SniperLaunchEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class ProjectileListener extends Enchantmentable {

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
//		runMethod(this, "curseOfLag", event, ProjectileLaunchEvent.class);
		runMethod(this, "drowned", event, ProjectileLaunchEvent.class);
		runMethod(this, "sniper", event, ProjectileLaunchEvent.class);
//		runMethod(this, "transmutation", event, ProjectileLaunchEvent.class);
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		runMethod(this, "hardBounce", event, ProjectileHitEvent.class);
//		runMethod(this, "splatterFest", event, ProjectileHitEvent.class);
	}
	
	private void curseOfLag(ProjectileLaunchEvent event){
		if(!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) return;
		Projectile proj = event.getEntity();
		if(proj.getShooter() instanceof Player){
			Player player = (Player) proj.getShooter();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				
				AbilityUtils.createEffects(player);

				AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
			}
		}
	}
	
	private void drowned(ProjectileLaunchEvent event){
		if(!canRun(RegisterEnchantments.DROWNED, event)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Trident){
			Trident trident = (Trident) proj;
			if(trident.getShooter() instanceof Player){
				Player player = (Player) trident.getShooter();
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if(tridentItem != null && ItemUtils.hasEnchantment(tridentItem, RegisterEnchantments.DROWNED)) {
					trident.setMetadata("drowned", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 
							ItemUtils.getLevel(tridentItem, RegisterEnchantments.DROWNED)));
				}
			}
		}
	}
	
	private void sniper(ProjectileLaunchEvent event){
		if(!canRun(RegisterEnchantments.SNIPER, event)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Arrow){
			Arrow arrow = (Arrow) proj;
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				ItemStack bow = player.getInventory().getItemInMainHand();
				if(bow != null && ItemUtils.hasEnchantment(bow, RegisterEnchantments.SNIPER)){
					int level = ItemUtils.getLevel(bow, RegisterEnchantments.SNIPER);
					double speed = 1 + (0.1 * level * level);
					
					SniperLaunchEvent sniper = new SniperLaunchEvent(player, speed);
					Bukkit.getPluginManager().callEvent(sniper);
					
					if(!sniper.isCancelled() && sniper.getSpeed() > 0) {
						arrow.setMetadata("sniper", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 
								LocationUtils.locationToString(player.getLocation())));
						Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable(){
							@Override
							public void run() {
								arrow.setVelocity(arrow.getVelocity().multiply(sniper.getSpeed()));
							}
								
						}, 0l);
					}
				}
			}
		}
	}
	
	private void transmutation(ProjectileLaunchEvent event){
		if(!canRun(RegisterEnchantments.TRANSMUTATION, event)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Trident){
			Trident trident = (Trident) proj;
			if(trident.getShooter() instanceof Player){
				Player player = (Player) trident.getShooter();
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if(tridentItem != null && ItemUtils.hasEnchantment(tridentItem, RegisterEnchantments.TRANSMUTATION)) {
					trident.setMetadata("transmutation", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 1));
				}
			}
		}
	}
	
	private void hardBounce(ProjectileHitEvent event) {
		if(!canRun(RegisterEnchantments.HARD_BOUNCE, event)) return;
		Entity e = event.getHitEntity();
		Projectile p = event.getEntity();
		if(e != null && e instanceof Player) {
			Player player = (Player) e;
			if(player.isBlocking()) {
				ItemStack shield = player.getInventory().getItemInMainHand();
				if(shield.getType() != Material.SHIELD) {
					shield = player.getInventory().getItemInOffHand();
				}
				if(shield != null && ItemUtils.hasEnchantment(shield, RegisterEnchantments.HARD_BOUNCE)) {
					int level = ItemUtils.getLevel(shield, RegisterEnchantments.HARD_BOUNCE);
					p.setMetadata("deflection", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable() {
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
	
	private void splatterFest(ProjectileHitEvent event) {
		if(!canRun(RegisterEnchantments.SPLATTER_FEST, event)) return;
		if(event.getHitEntity() != null && event.getEntityType() == EntityType.EGG) {
			Projectile entity = event.getEntity();
			if(entity.hasMetadata("splatter_fest")) {
				for(MetadataValue meta : entity.getMetadata("splatter_fest")) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(meta.asString()));
					if(offlinePlayer != null) {
						if(event.getHitEntity() instanceof Player) {
							Player player = (Player) event.getHitEntity();
							if(player.getUniqueId().toString().equals(offlinePlayer.getUniqueId().toString())) {
								AdvancementUtils.awardCriteria(player, ESAdvancement.EGGED_BY_MYSELF, "egg");
							}
						} else if (event.getHitEntity().getType() == EntityType.CHICKEN) {
							if(offlinePlayer.getPlayer() != null) {
								AdvancementUtils.awardCriteria(offlinePlayer.getPlayer(), ESAdvancement.CHICKEN_OR_THE_EGG, "egg");
							}
						}
					}
				}
			}
		}
	}
}
