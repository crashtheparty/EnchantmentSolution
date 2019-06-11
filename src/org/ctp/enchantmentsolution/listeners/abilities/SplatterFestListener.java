package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class SplatterFestListener extends EnchantmentListener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SPLATTER_FEST)) {
				event.setCancelled(false);
				if(!canRun(DefaultEnchantments.SPLATTER_FEST, event)) return;
				boolean removed = false;
				if(player.getGameMode().equals(GameMode.CREATIVE)) removed = true;
				for(int i = 0; i < player.getInventory().getSize(); i++) {
					if(removed) break;
					ItemStack removeItem = player.getInventory().getItem(i);
					if(removeItem != null && removeItem.getType().equals(Material.EGG)) {
						if(removeItem.getAmount() - 1 <= 0) {
							player.getInventory().setItem(i, new ItemStack(Material.AIR));
							removed = true;
						} else {
							removeItem.setAmount(removeItem.getAmount() - 1);
							removed = true;
						}
					}
				}
				if(!removed) {
					ItemStack remove = player.getInventory().getItemInOffHand();
					if(player.getInventory().getItemInOffHand().getType().equals(Material.EGG)) {
						if(remove.getAmount() - 1 <= 0) {
							player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
							removed = true;
						} else {
							remove.setAmount(remove.getAmount() - 1);
							removed = true;
						}
					}
				}
				if(removed) {
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					player.incrementStatistic(Statistic.USE_ITEM, Material.EGG);
					Egg egg = player.launchProjectile(Egg.class);
					egg.setMetadata("splatter_fest", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					super.damageItem(player, item);
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if(!canRun(DefaultEnchantments.SPLATTER_FEST, event)) return;
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
