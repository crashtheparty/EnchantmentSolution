package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class BeheadingListener extends EnchantmentListener{

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(!canRun(DefaultEnchantments.BEHEADING, event)) return;
		Entity entity = event.getEntity();
		Player killer = event.getEntity().getKiller();
		if(killer != null){
			if(Enchantments.hasEnchantment(killer.getInventory().getItemInMainHand(), DefaultEnchantments.BEHEADING)){
				double chance = Enchantments.getLevel(killer.getInventory().getItemInMainHand(), DefaultEnchantments.BEHEADING) * .05;
				double random = Math.random();
				if(chance > random){
					if(entity instanceof WitherSkeleton){
						ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
						for(ItemStack drop : event.getDrops()) {
							if(drop.getType().equals(Material.WITHER_SKELETON_SKULL)) {
								AdvancementProgress progress = killer.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.DOUBLE_HEADER.getNamespace()));
								if(progress.getRemainingCriteria().contains("wither_skull")) {
									progress.awardCriteria("wither_skull");
								}
								break;
							}
						}
						event.getDrops().add(skull);
					}else if(entity instanceof Skeleton){
						ItemStack skull = new ItemStack(Material.SKELETON_SKULL);
						event.getDrops().add(skull);
					}else if(entity instanceof Zombie){
						ItemStack skull = new ItemStack(Material.ZOMBIE_HEAD);
						event.getDrops().add(skull);
					}else if(entity instanceof Creeper){
						ItemStack skull = new ItemStack(Material.CREEPER_HEAD);
						event.getDrops().add(skull);
					}else if(entity instanceof Player){
						ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
						SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
						skullMeta.setOwningPlayer(((Player) entity));
						skullMeta.setDisplayName(ChatColor.DARK_RED + ((Player) entity).getName() + "'s Skull");
						skull.setItemMeta(skullMeta);
						event.getDrops().add(skull);
						AdvancementProgress progress = killer.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.HEADHUNTER.getNamespace()));
						if(progress.getRemainingCriteria().contains("player_head")) {
							progress.awardCriteria("player_head");
						}
					}else if(entity instanceof EnderDragon){
						ItemStack skull = new ItemStack(Material.DRAGON_HEAD);
						event.getDrops().add(skull);
					}
				}
			}
		}
	}
}
