package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class ShockAspectListener extends EnchantmentListener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		if(!canRun(DefaultEnchantments.SHOCK_ASPECT, event)) return;
		if(event.getDamage() == 0) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player){
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SHOCK_ASPECT) && item.getType() != Material.BOOK){
				int level = Enchantments.getLevel(item, DefaultEnchantments.SHOCK_ASPECT);
				double chance = .05 + 0.1 * level;
				double random = Math.random();
				if(chance > random){
					World world = player.getLocation().getWorld();
					world.strikeLightning(event.getEntity().getLocation());
				}
			}
		}
	}
	
}
