package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.IcarusDelay;
import org.ctp.enchantmentsolution.nms.FlowerGiftNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemMoisturizeType;

@SuppressWarnings("unused")
public class PlayerListener extends EnchantmentListener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		runMethod(this, "flowerGift", event);
		runMethod(this, "irenesLasso", event);
		runMethod(this, "splatterFest", event);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerInteractHighest(PlayerInteractEvent event) {
		runMethod(this, "moisturize", event);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		runMethod(this, "icarus", event);
	}
	
	private void flowerGift(PlayerInteractEvent event) {
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
	
	private void irenesLasso(PlayerInteractEvent event) {
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
				for(AnimalMob animal : EnchantmentSolution.getAnimals()) {
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
					EnchantmentSolution.removeAnimals(remove);
				}
			}
		}
	}
	
	private void moisturize(PlayerInteractEvent event) {
		if(!canRun(DefaultEnchantments.MOISTURIZE, event)) return;
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			ItemStack item = event.getItem();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.MOISTURIZE)) {
				Block block = event.getClickedBlock();
				ItemMoisturizeType type = ItemMoisturizeType.getType(block.getType());
				if(type != null) {
					switch(type.getName().toLowerCase()) {
					case "extinguish":
						if(block.getType() == Material.CAMPFIRE) {
							Campfire fire = (Campfire) block.getBlockData();
							if(fire.isLit()) {
								fire.setLit(false);
								block.setBlockData(fire);
								super.damageItem(event.getPlayer(), item);
								event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_WATER_AMBIENT, 1, 1);
								event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
								AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.EASY_OUT, "campfire");
							}
						}
						break;
					case "wet":
						if(block.getType().name().contains("CONCRETE")) {
							AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.JUST_ADD_WATER, "concrete");
						}
						block.setType(ItemMoisturizeType.getWet(block.getType()));
						super.damageItem(event.getPlayer(), item);
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
						event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
						break;
					case "unsmelt":
						if(block.getType() == Material.CRACKED_STONE_BRICKS) {
							AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.REPAIRED, "broken_bricks");
						}
						block.setType(ItemMoisturizeType.getUnsmelt(block.getType()));
						super.damageItem(event.getPlayer(), item);
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
						event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
						break;
					case "waterlog":
						if(block.getBlockData() instanceof Waterlogged) {
							Waterlogged water = (Waterlogged) block.getBlockData();
							if(block.getState() instanceof Container) {
								if(event.getPlayer().isSneaking()) {
									if(!water.isWaterlogged()) {
										water.setWaterlogged(true);
										block.setBlockData(water);
										super.damageItem(event.getPlayer(), item);
										event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
										event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
										event.setCancelled(true);
									}
								}
							} else {
								if(!water.isWaterlogged()) {
									water.setWaterlogged(true);
									block.setBlockData(water);
									super.damageItem(event.getPlayer(), item);
									event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
									event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
								}
							}
						}
						break;
					}
				}
			}
		}
	}
	
	private void splatterFest(PlayerInteractEvent event) {
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
	
	private void icarus(PlayerMoveEvent event) {
		if(!canRun(DefaultEnchantments.ICARUS, event)) return;
		Player player = event.getPlayer();
		ItemStack chestplate = player.getInventory().getChestplate();
		if(chestplate != null && chestplate.getType().equals(Material.ELYTRA) && player.isGliding() && 
				Enchantments.hasEnchantment(chestplate, DefaultEnchantments.ICARUS)) {
			int level = Enchantments.getLevel(player.getInventory().getChestplate(), DefaultEnchantments.ICARUS);
			double additional = Math.log((2 * level + 8) / 5) + 1.5;
			if(player.getLocation().getPitch() < -10) {
				for(IcarusDelay icarus : IcarusDelay.getIcarusDelay()) {
					if(icarus.getPlayer().equals(player)) {
						return;
					}
				}
				int num_breaks = 0;
				if(!(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))) {
					int unbreaking = Enchantments.getLevel(chestplate, Enchantment.DURABILITY);
					int chances = level * 5;
					for(int i = 0; i < chances; i++) {
						double chance = (1.0D) / (unbreaking + 1.0D);
						double random = Math.random();
						if(chance > random) {
							num_breaks ++;
						}
					}
				}
				if(num_breaks > 0) {
					if(DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks > chestplate.getType().getMaxDurability()) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.TOO_CLOSE, "failure"); 
						player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 5, 2, 2, 2);
						return;
					}
					DamageUtils.setDamage(chestplate, DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks);
				}
				Vector v = player.getVelocity().clone().add(new Vector(0, additional, 0)).multiply(new Vector(additional / 2, 1, additional / 2));
				player.setVelocity(v);
				player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 250, 2, 2, 2);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
				IcarusDelay.getIcarusDelay().add(new IcarusDelay(player));
			}
		}
	}
}
