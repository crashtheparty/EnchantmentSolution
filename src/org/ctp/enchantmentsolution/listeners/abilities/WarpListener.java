package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class WarpListener implements Listener{
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.WARP)) return;
		Entity attacked = event.getEntity();
		if(attacked instanceof LivingEntity){
			LivingEntity player = (LivingEntity) attacked;
			ItemStack leggings = player.getEquipment().getLeggings();
			if(leggings == null) return;
			if(Enchantments.hasEnchantment(leggings, DefaultEnchantments.WARP)){
				int level = Enchantments.getLevel(leggings, DefaultEnchantments.WARP);
				List<Location> locsToTp = new ArrayList<Location>();
				for(int x = - (level + 4); x <= level + 5; x++){
					for(int y = - (level + 4); y <= level + 5; y++){
						for(int z = - (level + 4); z <= level + 5; z++){
							if(x == y && x == 0 && z == 0){
								continue;
							}
							Location tp = new Location(player.getWorld(), player.getLocation().getBlockX() + x, player.getLocation().getBlockY() + y, 
									player.getLocation().getBlockZ() + z);
							if(player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY() + 1, tp.getBlockZ())).getType().equals(Material.AIR)
									&& player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY(), tp.getBlockZ())).getType().equals(Material.AIR)
									&& !(player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY() - 1, tp.getBlockZ())).getType().equals(Material.AIR))){
								locsToTp.add(tp);
							}
							
						}
					}
				}
				double chance = .25;
				double random = Math.random();
				if(chance > random && locsToTp.size() > 0){
					int randomLoc = (int) (Math.random() * locsToTp.size());
					Location toTeleport = locsToTp.get(randomLoc);
					World world = toTeleport.getWorld();
					world.spawnParticle(Particle.PORTAL, toTeleport, 50, 0.2, 2, 0.2);
					world.spawnParticle(Particle.PORTAL, attacked.getLocation(), 50, 0.2, 2, 0.2);
					world.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10, 1);
					
					toTeleport.setYaw(player.getLocation().getYaw());
					toTeleport.setPitch(player.getLocation().getPitch());
					player.teleport(toTeleport);
				}
			}
		}
	}
}
