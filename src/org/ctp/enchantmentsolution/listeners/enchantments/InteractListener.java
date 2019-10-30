package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.interact.LassoInteractEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class InteractListener extends Enchantmentable {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
//		runMethod(this, "flowerGift", event, PlayerInteractEvent.class);
		runMethod(this, "irenesLasso", event, PlayerInteractEvent.class);
//		runMethod(this, "splatterFest", event, PlayerInteractEvent.class);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerInteractHighest(PlayerInteractEvent event) {
//		runMethod(this, "moisturize", event, PlayerInteractEvent.class);
	}
	
	private void irenesLasso(PlayerInteractEvent event) {
		if(!canRun(RegisterEnchantments.IRENES_LASSO, event)) return;
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
				item = player.getInventory().getItemInOffHand();
		    }
			if(item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.IRENES_LASSO)){
				List<Integer> entityIDs = StringUtils.getAnimalIDsFromItem(item);
				if (entityIDs.size() == 0) return;
				int entityID = entityIDs.get(0);
				Iterator<AnimalMob> iterator = EnchantmentSolution.getAnimals().iterator();
				while(iterator.hasNext()) {
					AnimalMob animal = iterator.next();
					if(animal.inItem(item, entityID)) {
						LassoInteractEvent lasso = new LassoInteractEvent(player, item, event.getClickedBlock(), event.getBlockFace(), animal);
						if(!lasso.isCancelled()) {
							event.setCancelled(true);
							AnimalMob fromLasso = lasso.getAnimal();
							Location loc = lasso.getBlock().getRelative(lasso.getFace()).getLocation().clone().add(0.5, 0, 0.5);
							if(loc.getBlock().isPassable()) {
								Entity e = loc.getWorld().spawnEntity(loc, fromLasso.getMob());
								fromLasso.editProperties(e);
								DamageUtils.damageItem(player, item, 1, 2);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
								StringUtils.removeAnimal(item, entityID);
								iterator.remove();
								break;
							}
						}
					}
				}
			}
		}
	}
}
