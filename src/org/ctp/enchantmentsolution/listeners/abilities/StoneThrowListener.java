package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class StoneThrowListener extends EnchantmentListener{
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if(!canRun(DefaultEnchantments.STONE_THROW, event)) return;
			switch(event.getEntityType()) {
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
							if(entity instanceof Player && event.getEntity() instanceof Phantom) {
								Phantom phantom = (Phantom) event.getEntity();
								if(phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == phantom.getHealth() && phantom.getHealth() <= extraDamage) {
									AdvancementUtils.awardCriteria((Player) entity, ESAdvancement.JUST_DIE_ALREADY, "phantom");
								}
							}
							if(entity instanceof Player && event.getEntity() instanceof EnderDragon) {
								AdvancementUtils.awardCriteria((Player) entity, ESAdvancement.UNDERKILL, "dragon");
							}
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
