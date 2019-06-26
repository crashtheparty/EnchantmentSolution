package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.FlowerGiftNMS;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class FlowerGiftListener extends EnchantmentListener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!canRun(DefaultEnchantments.FLOWER_GIFT, event)) return;
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			Player player = event.getPlayer();
			if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			Block block = event.getClickedBlock();
			if (block != null && item != null) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.FLOWER_GIFT)) {
					Location loc = block.getLocation().clone().add(0.5, 0.5, 0.5);
					if(FlowerGiftNMS.isItem(block.getType())) {
						ItemStack flowerGift = FlowerGiftNMS.getItem(block.getType());
						if(flowerGift != null) {
							Item droppedItem = player.getWorld().dropItem(
									loc,
									flowerGift);
							droppedItem.setVelocity(new Vector(0,0,0));
							player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 30, 0.2, 0.5, 0.2);
							if(FlowerGiftNMS.isDoubleFlower(flowerGift.getType())) {
								AdvancementUtils.awardCriteria(player, ESAdvancement.BONEMEAL_PLUS, "bonemeal");
							} else if (FlowerGiftNMS.isWitherRose(flowerGift.getType())) {
								AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_AS_SWEET, "wither_rose");
							}
						} else {
							player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc, 30, 0.2, 0.5, 0.2);
						}
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						super.damageItem(player, item);
					}
				}
			}
		}
	}
}
