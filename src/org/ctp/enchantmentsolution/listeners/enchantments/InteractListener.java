package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.interact.FlowerGiftEvent;
import org.ctp.enchantmentsolution.events.interact.LassoInteractEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.FlowerGiftNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class InteractListener extends Enchantmentable {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		runMethod(this, "flowerGift", event, PlayerInteractEvent.class);
		runMethod(this, "irenesLasso", event, PlayerInteractEvent.class);
		// runMethod(this, "splatterFest", event, PlayerInteractEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractHighest(PlayerInteractEvent event) {
		// runMethod(this, "moisturize", event, PlayerInteractEvent.class);
	}

	private void flowerGift(PlayerInteractEvent event) {
		if (!canRun(RegisterEnchantments.FLOWER_GIFT, event)) {
			return;
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
				return; // off hand packet, ignore.
			}
			Player player = event.getPlayer();
			if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
				return;
			}
			ItemStack item = player.getInventory().getItemInMainHand();
			Block block = event.getClickedBlock();
			if (block != null && item != null) {
				if (ItemUtils.hasEnchantment(item, RegisterEnchantments.FLOWER_GIFT)) {
					if (FlowerGiftNMS.isItem(block.getType())) {
						FlowerGiftEvent flowerGiftEvent = new FlowerGiftEvent(player, item, block,
								FlowerGiftNMS.getItem(block.getType()), block.getLocation());
						Bukkit.getPluginManager().callEvent(flowerGiftEvent);

						if (!flowerGiftEvent.isCancelled()) {
							Location loc = flowerGiftEvent.getDropLocation();
							ChatUtils.sendInfo("Location: " + loc);
							ItemStack flowerGift = flowerGiftEvent.getFlower();
							if (flowerGiftEvent.getFlower() != null) {
								player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 30, 0.2, 0.5, 0.2);
								if (FlowerGiftNMS.isDoubleFlower(flowerGift.getType())) {
									AdvancementUtils.awardCriteria(player, ESAdvancement.BONEMEAL_PLUS, "bonemeal");
								} else if (FlowerGiftNMS.isWitherRose(flowerGift.getType())) {
									AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_AS_SWEET, "wither_rose");
								}
							} else {
								player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc, 30, 0.2, 0.5, 0.2);
							}
							ItemUtils.dropItem(flowerGiftEvent.getFlower(), flowerGiftEvent.getDropLocation(), true);
							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
							DamageUtils.damageItem(player, item);
						}
					}
				}
			}
		}
	}

	private void irenesLasso(PlayerInteractEvent event) {
		if (!canRun(RegisterEnchantments.IRENES_LASSO, event)) {
			return;
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
				item = player.getInventory().getItemInOffHand();
			}
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.IRENES_LASSO)) {
				List<Integer> entityIDs = StringUtils.getAnimalIDsFromItem(item);
				if (entityIDs.size() == 0) {
					return;
				}
				int entityID = entityIDs.get(0);
				Iterator<AnimalMob> iterator = EnchantmentSolution.getAnimals().iterator();
				while (iterator.hasNext()) {
					AnimalMob animal = iterator.next();
					if (animal.inItem(item, entityID)) {
						LassoInteractEvent lasso = new LassoInteractEvent(player, item, event.getClickedBlock(),
								event.getBlockFace(), animal);
						if (!lasso.isCancelled()) {
							event.setCancelled(true);
							AnimalMob fromLasso = lasso.getAnimal();
							Location loc = lasso.getBlock().getRelative(lasso.getFace()).getLocation().clone().add(0.5,
									0, 0.5);
							if (loc.getBlock().isPassable()) {
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
