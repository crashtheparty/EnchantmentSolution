package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;

public class IrenesLassoListener extends EnchantmentListener{

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.IRENES_LASSO, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if(attacker instanceof Player && attacked instanceof Animals){
			Player player = (Player) attacker;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if(attackItem != null && Enchantments.hasEnchantment(attackItem, DefaultEnchantments.IRENES_LASSO)){
				event.setCancelled(true);
				int max = Enchantments.getLevel(attackItem, DefaultEnchantments.IRENES_LASSO);
				int current = 0;
				for(AnimalMob animal : AnimalMob.ANIMALS) {
					if((animal.getItem() != null && animal.getItem().equals(attackItem)) || StringUtils.getAnimalIDsFromItem(attackItem).contains(animal.getEntityID())) {
						current ++;
					}
				}
				if(current >= max) return;
				if(attacked instanceof Tameable) {
					if(((Tameable) attacked).getOwner() != null && !((Tameable) attacked).getOwner().getUniqueId().equals(player.getUniqueId())) {
						ChatUtils.sendMessage(player, "This animal does not belong to you!");
						return;
					}
					String type = attacked.getType().name().toLowerCase();
					AdvancementProgress progress = player.getAdvancementProgress(
							Bukkit.getAdvancement(ESAdvancement.THORGY.getNamespace()));
					if(progress.getRemainingCriteria().contains(type)) {
						progress.awardCriteria(type);
					}
					AdvancementProgress progress2 = player.getAdvancementProgress(
							Bukkit.getAdvancement(ESAdvancement.FREE_PETS.getNamespace()));
					for(String criteria : progress2.getRemainingCriteria()) {
						ChatUtils.sendMessage(player, type + " " + criteria);
					}
					if(progress2.getRemainingCriteria().contains(type)) {
						progress2.awardCriteria(type);
					}
				}
				McMMO.customName(attacked);
				AnimalMob.ANIMALS.add(new AnimalMob((Animals) attacked, attackItem));
				attacked.remove();
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!canRun(DefaultEnchantments.IRENES_LASSO, event)) return;
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.IRENES_LASSO)){
				AnimalMob remove = null;
				List<Integer> entityIDs = StringUtils.getAnimalIDsFromItem(item);
				if(entityIDs.size() == 0) return;
				int entityID = entityIDs.get(0);
				for(AnimalMob animal : AnimalMob.ANIMALS) {
					if((animal.getItem() != null && item.equals(animal.getItem())) || (entityIDs.size() > 0 && entityID == animal.getEntityID())) {
						remove = animal;
						Location loc = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5, 0, 0.5);
						Entity e = loc.getWorld().spawnEntity(loc, animal.getMob());
						animal.editProperties(e);
						damageItem(player, item, 1, 2);
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						StringUtils.removeAnimal(item, entityID);
						break;
					}
				}
				if(remove != null) {
					AnimalMob.ANIMALS.remove(remove);
				}
			}
		}
	}
}
