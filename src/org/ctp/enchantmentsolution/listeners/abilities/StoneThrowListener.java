package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class StoneThrowListener implements Listener{
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if(!DefaultEnchantments.isEnabled(DefaultEnchantments.STONE_THROW)) return;
			switch(event.getEntity().getType()) {
			case BAT:
			case BLAZE:
			case ENDER_DRAGON:
			case GHAST:
			case PARROT:
			case PHANTOM:
			case VEX:
			case WITHER:
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					ProjectileSource shooter = arrow.getShooter();
					if(shooter instanceof HumanEntity) {
						HumanEntity entity = (HumanEntity) shooter;
						ItemStack item = entity.getInventory().getItemInOffHand();
						if(item == null || !Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
							item = entity.getInventory().getItemInMainHand();
						}
						if(Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
							int level = Enchantments.getLevel(item, DefaultEnchantments.STONE_THROW);
							double percentage = .4 * level + 1.2;
							int extraDamage = (int) (percentage * event.getDamage() + .5);
							event.setDamage(extraDamage);
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

}
