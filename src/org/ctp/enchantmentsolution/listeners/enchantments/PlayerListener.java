package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;
import org.ctp.enchantmentsolution.events.interact.FlowerGiftEvent;
import org.ctp.enchantmentsolution.events.interact.LassoInteractEvent;
import org.ctp.enchantmentsolution.events.interact.MoisturizeEvent;
import org.ctp.enchantmentsolution.events.interact.SplatterFestEvent;
import org.ctp.enchantmentsolution.events.modify.IcarusLaunchEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.FlowerGiftNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.IcarusDelay;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class PlayerListener extends Enchantmentable {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		runMethod(this, "flowerGift", event, PlayerInteractEvent.class);
		runMethod(this, "irenesLasso", event, PlayerInteractEvent.class);
		runMethod(this, "splatterFest", event, PlayerInteractEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractHighest(PlayerInteractEvent event) {
		runMethod(this, "moisturize", event, PlayerInteractEvent.class);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		runMethod(this, "icarus", event, PlayerMoveEvent.class);
		runMethod(this, "movementListener", event, RegisterEnchantments.MAGMA_WALKER);
		runMethod(this, "movementListener", event, RegisterEnchantments.VOID_WALKER);
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
	
	private void icarus(PlayerMoveEvent event) {
		if(!canRun(RegisterEnchantments.ICARUS, event)) return;
		Player player = event.getPlayer();
		ItemStack chestplate = player.getInventory().getChestplate();
		if(chestplate != null && chestplate.getType().equals(Material.ELYTRA) && player.isGliding() && 
		ItemUtils.hasEnchantment(chestplate, RegisterEnchantments.ICARUS)) {
			int level = ItemUtils.getLevel(player.getInventory().getChestplate(), RegisterEnchantments.ICARUS);
			double additional = Math.log((2 * level + 8) / 5) + 1.5;
			if(player.getLocation().getPitch() < -10) {
				for(IcarusDelay icarus : IcarusDelay.getIcarusDelay()) {
					if(icarus.getPlayer().equals(player)) {
						return;
					}
				}
				int num_breaks = DamageUtils.damageItem(player, chestplate, level * 5, 1, false);
				if(DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks >= chestplate.getType().getMaxDurability()) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.TOO_CLOSE, "failure"); 
					player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 5, 2, 2, 2);
					return;
				}
				IcarusLaunchEvent icarus = new IcarusLaunchEvent(player, additional);
				Bukkit.getPluginManager().callEvent(icarus);
				
				if(!icarus.isCancelled()) {
					DamageUtils.setDamage(chestplate, DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks);
					Vector pV = player.getVelocity().clone();
					Vector v = pV.add(new Vector(0, icarus.getSpeed(), 0)).multiply(new Vector(icarus.getSpeed() / 2, 1, icarus.getSpeed() / 2));
					player.setVelocity(v);
					player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 250, 2, 2, 2);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
					IcarusDelay.getIcarusDelay().add(new IcarusDelay(player));
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
	
	private void moisturize(PlayerInteractEvent event) {
		if(!canRun(RegisterEnchantments.MOISTURIZE, event)) return;
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			ItemStack item = event.getItem();
			if(item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.MOISTURIZE)) {
				Block block = event.getClickedBlock();
				ItemMoisturizeType type = ItemMoisturizeType.getMoisturizeType(block.getType());
				if(type != null) {
					Sound sound = null;
					switch(type.getName().toLowerCase()) {
					case "extinguish":
						if(block.getType() == Material.CAMPFIRE) {
							Campfire fire = (Campfire) block.getBlockData();
							if(fire.isLit()) {
								sound = Sound.BLOCK_WATER_AMBIENT;
							}
						}
						break;
					case "wet":
					case "unsmelt":
						sound = Sound.ENTITY_SHEEP_SHEAR;
						break;
					case "waterlog":
						if(block.getBlockData() instanceof Waterlogged) {
							Waterlogged water = (Waterlogged) block.getBlockData();
							if(block.getType().isInteractable() && event.getPlayer().isSneaking() && !water.isWaterlogged()
							|| !block.getType().isInteractable() && !water.isWaterlogged()) {
								sound = Sound.ENTITY_SHEEP_SHEAR;
							}
						}
						break;
					}
					if(sound != null) {
						MoisturizeEvent moisturize = new MoisturizeEvent(event.getPlayer(), block, type, sound);
						Bukkit.getPluginManager().callEvent(moisturize);
						
						if(!moisturize.isCancelled()) {
							Block moisturizeBlock = moisturize.getBlock();
							Player player = moisturize.getPlayer();
							
							switch(type.getName().toLowerCase()) {
							case "extinguish":
								Campfire fire = (Campfire) moisturizeBlock.getBlockData();
								fire.setLit(false);
								moisturizeBlock.setBlockData(fire);
								DamageUtils.damageItem(player, item);
								player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
								AdvancementUtils.awardCriteria(player, ESAdvancement.EASY_OUT, "campfire");
								break;
							case "wet":
								if(moisturizeBlock.getType().name().contains("CONCRETE")) {
									AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_ADD_WATER, "concrete");
								}
								moisturizeBlock.setType(ItemMoisturizeType.getWet(moisturizeBlock.getType()));
								DamageUtils.damageItem(player, item);
								player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
								break;
							case "unsmelt":
								if(moisturizeBlock.getType() == Material.CRACKED_STONE_BRICKS) {
									AdvancementUtils.awardCriteria(player, ESAdvancement.REPAIRED, "broken_bricks");
								}
								moisturizeBlock.setType(ItemMoisturizeType.getUnsmelt(moisturizeBlock.getType()));
								DamageUtils.damageItem(player, item);
								player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
								break;
							case "waterlog":
								Waterlogged water = (Waterlogged) moisturizeBlock.getBlockData();
								water.setWaterlogged(true);
								moisturizeBlock.setBlockData(water);
								DamageUtils.damageItem(player, item);
								player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void splatterFest(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(ItemUtils.hasEnchantment(item, RegisterEnchantments.SPLATTER_FEST)) {
				event.setCancelled(false);
				if(!canRun(RegisterEnchantments.SPLATTER_FEST, event)) return;
				boolean removed = false;
				
				SplatterFestEvent splatterFest = new SplatterFestEvent(player, player.getGameMode() != GameMode.CREATIVE, 
				player.getInventory().all(Material.EGG).size() > 0);
				Bukkit.getPluginManager().callEvent(splatterFest);
				
				if(!splatterFest.isCancelled()) {
					if(splatterFest.takeEgg()) {
						ItemStack[] contents = splatterFest.getPlayer().getInventory().getContents();
						ItemStack[] extraContents = splatterFest.getPlayer().getInventory().getExtraContents();
						ItemStack[] allContents = Arrays.copyOf(contents, contents.length + extraContents.length);
						System.arraycopy(extraContents, 0, allContents, contents.length, extraContents.length);
						for(int i = 0; i < extraContents.length; i++) {
							ItemStack removeItem = player.getInventory().getItem(i);
							if(removeItem != null && removeItem.getType().equals(Material.EGG)) {
								if(removeItem.getAmount() - 1 <= 0) {
									player.getInventory().setItem(i, new ItemStack(Material.AIR));
									break;
								} else {
									removeItem.setAmount(removeItem.getAmount() - 1);
									break;
								}
							}
						}
					}
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					player.incrementStatistic(Statistic.USE_ITEM, Material.EGG);
					Egg egg = player.launchProjectile(Egg.class);
					egg.setMetadata("splatter_fest", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					if(!splatterFest.takeEgg()) {
						egg.setMetadata("hatch_egg", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					}
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}
	
	private void movementListener(PlayerMoveEvent event, Enchantment enchantment){
		if(!canRun(enchantment, event)) return;
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		if(player.isFlying() || player.isGliding() || player.isInsideVehicle()){
			return;
		}
		ItemStack boots = player.getInventory().getBoots();
		if(boots != null && ItemUtils.hasEnchantment(boots, enchantment)) {
			if(enchantment == RegisterEnchantments.MAGMA_WALKER) {
				if(player.isOnGround()) {
					WalkerUtils.updateBlocks(player, boots, loc, enchantment, Arrays.asList(Material.LAVA), Material.MAGMA_BLOCK, "MagmaWalker");
				}
			} else if (enchantment == RegisterEnchantments.VOID_WALKER) {
				if (LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), false)) {
					WalkerUtils.updateBlocks(player, boots, loc, enchantment, 
					Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR), Material.OBSIDIAN, "VoidWalker");
				} else if (LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), true)) {
					WalkerUtils.updateBlocks(player, boots, event.getTo(), enchantment, 
					Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR), Material.OBSIDIAN, "VoidWalker");
				}
			}
		}
	}
}
