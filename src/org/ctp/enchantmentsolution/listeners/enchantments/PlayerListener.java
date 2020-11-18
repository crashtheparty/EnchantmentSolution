package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;
import org.ctp.enchantmentsolution.events.interact.*;
import org.ctp.enchantmentsolution.events.modify.IcarusLaunchEvent;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.threads.IcarusThread;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.FlowerGiftDrop;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class PlayerListener extends Enchantmentable {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		runMethod(this, "flowerGift", event, PlayerInteractEvent.class);
		runMethod(this, "frosty", event, PlayerInteractEvent.class);
		runMethod(this, "irenesLasso", event, PlayerInteractEvent.class);
		runMethod(this, "overkill", event, PlayerInteractEvent.class);
		runMethod(this, "splatterFest", event, PlayerInteractEvent.class);
		runMethod(this, "zeal", event, PlayerInteractEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractHighest(PlayerInteractEvent event) {
		runMethod(this, "moisturize", event, PlayerInteractEvent.class);
	}

	@EventHandler
	public void onPlayerChangeCoords(PlayerChangeCoordsEvent event) {
		runMethod(this, "icarus", event, PlayerChangeCoordsEvent.class);
		runMethod(this, "movementListener", event, RegisterEnchantments.MAGMA_WALKER);
		runMethod(this, "movementListener", event, RegisterEnchantments.VOID_WALKER);
	}

	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent event) {
		runMethod(this, "stickyHold", event, PlayerItemBreakEvent.class);
	}

	private void flowerGift(PlayerInteractEvent event) {
		if (!canRun(RegisterEnchantments.FLOWER_GIFT, event)) return;
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) return; // off hand packet, ignore.
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.FLOWER_GIFT)) return;
			if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			Block block = event.getClickedBlock();
			if (block != null && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FLOWER_GIFT) && FlowerGiftDrop.isItem(block.getType())) {
				FlowerGiftEvent flowerGiftEvent = new FlowerGiftEvent(player, item, block, FlowerGiftDrop.getItem(block.getType()), LocationUtils.offset(block.getLocation()));
				Bukkit.getPluginManager().callEvent(flowerGiftEvent);

				if (!flowerGiftEvent.isCancelled() && flowerGiftEvent.overCooldown()) {
					Location loc = flowerGiftEvent.getDropLocation();
					ItemStack flowerGift = flowerGiftEvent.getFlower();
					if (FlowerGiftDrop.isDoubleFlower(flowerGift.getType())) loc.add(0, 1, 0);
					if (flowerGiftEvent.getFlower() != null) {
						player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 30, 0.2, 0.5, 0.2);
						if (FlowerGiftDrop.isDoubleFlower(flowerGift.getType())) AdvancementUtils.awardCriteria(player, ESAdvancement.BONEMEAL_PLUS, "bonemeal");
						else if (FlowerGiftDrop.isWitherRose(flowerGift.getType())) AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_AS_SWEET, "wither_rose");
						ItemUtils.dropItem(flowerGift, flowerGiftEvent.getDropLocation());
					} else
						player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc, 30, 0.2, 0.5, 0.2);
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
					esPlayer.setCooldown(RegisterEnchantments.FLOWER_GIFT);
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void frosty(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.FROSTY)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FROSTY)) {
				event.setCancelled(false);
				if (!canRun(RegisterEnchantments.FROSTY, event)) return;
				boolean takeSnow = player.getGameMode() != GameMode.CREATIVE;
				FrostyEvent frosty = new FrostyEvent(player, item, takeSnow, player.getInventory().all(Material.SNOWBALL).size() > 0);
				Bukkit.getPluginManager().callEvent(frosty);

				if (!frosty.isCancelled() && !frosty.willCancel()) {
					if (frosty.takeSnowball() && frosty.hasSnowball()) {
						ItemStack[] contents = frosty.getPlayer().getInventory().getContents();
						ItemStack[] extraContents = frosty.getPlayer().getInventory().getExtraContents();
						ItemStack[] allContents = Arrays.copyOf(contents, contents.length + extraContents.length);
						System.arraycopy(extraContents, 0, allContents, contents.length, extraContents.length);
						for(int i = 0; i < allContents.length; i++) {
							ItemStack removeItem = player.getInventory().getItem(i);
							if (removeItem != null && removeItem.getType().equals(Material.SNOWBALL)) if (removeItem.getAmount() - 1 <= 0) {
								player.getInventory().setItem(i, new ItemStack(Material.AIR));
								break;
							} else {
								removeItem.setAmount(removeItem.getAmount() - 1);
								break;
							}
						}
					}
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					Snowball snowball = player.launchProjectile(Snowball.class);
					snowball.setMetadata("frosty", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1, 1);
					ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
					esPlayer.setCooldown(RegisterEnchantments.FROSTY);
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void icarus(PlayerChangeCoordsEvent event) {
		if (!canRun(RegisterEnchantments.ICARUS, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.ICARUS)) return;
		ItemStack chestplate = player.getInventory().getChestplate();
		if (chestplate != null && chestplate.getType().equals(Material.ELYTRA) && player.isGliding() && EnchantmentUtils.hasEnchantment(chestplate, RegisterEnchantments.ICARUS)) {
			int level = EnchantmentUtils.getLevel(player.getInventory().getChestplate(), RegisterEnchantments.ICARUS);
			double additional = Math.log((2 * level + 8) / 5) + 1.5;
			if (player.getLocation().getPitch() < -10) {
				ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
				if (esPlayer.getIcarusDelay() > 0) return;
				int num_breaks = DamageUtils.damageItem(player, chestplate, level * 5, 1, false).getDamageAmount();
				if (DamageUtils.getDamage(chestplate) + num_breaks >= DamageUtils.getMaxDamage(chestplate)) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.TOO_CLOSE, "failure");
					player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 5, 2, 2, 2);
					return;
				}
				IcarusLaunchEvent icarus = new IcarusLaunchEvent(player, level, additional, 30);
				Bukkit.getPluginManager().callEvent(icarus);

				if (!icarus.isCancelled()) {
					DamageUtils.setDamage(chestplate, DamageUtils.getDamage(chestplate) + num_breaks);
					Vector pV = player.getVelocity().clone();
					Vector v = pV.add(new Vector(0, icarus.getSpeed(), 0)).multiply(new Vector(icarus.getSpeed() / 2, 1, icarus.getSpeed() / 2));
					player.setVelocity(v);
					player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 250, 2, 2, 2);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
					esPlayer.setIcarusDelay(icarus.getSecondDelay());
					IcarusThread thread = IcarusThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 20l);
						thread.setScheduler(scheduler);
					}
				}
			}
		}
	}

	private void irenesLasso(PlayerInteractEvent event) {
		if (!canRun(RegisterEnchantments.IRENES_LASSO, event)) return;
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.IRENES_LASSO)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (event.getHand() == EquipmentSlot.OFF_HAND) item = player.getInventory().getItemInOffHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.IRENES_LASSO)) {
				List<Integer> entityIDs = PersistenceNMS.getAnimalIDsFromItem(item);
				if (entityIDs.size() == 0) return;
				int entityID = entityIDs.get(0);
				Iterator<AnimalMob> iterator = EnchantmentSolution.getAnimals().iterator();
				while (iterator.hasNext()) {
					AnimalMob animal = iterator.next();
					if (animal.inItem(item, entityID)) {
						LassoInteractEvent lasso = new LassoInteractEvent(player, EnchantmentUtils.getLevel(item, RegisterEnchantments.IRENES_LASSO), item, event.getClickedBlock(), event.getBlockFace(), animal);
						if (!lasso.isCancelled()) {
							event.setCancelled(true);
							AnimalMob fromLasso = lasso.getAnimal();
							Location loc = lasso.getBlock().getRelative(lasso.getFace()).getLocation().clone().add(0.5, 0, 0.5);
							if (loc.getBlock().isPassable()) {
								Entity e = loc.getWorld().spawnEntity(loc, fromLasso.getMob());
								if (e == null) return;
								fromLasso.editProperties(e, true, false);
								PersistenceNMS.removeAnimal(item, entityID);
								DamageUtils.damageItem(player, item, 1, 2);
								player.incrementStatistic(Statistic.USE_ITEM, item.getType());
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
		if (!canRun(RegisterEnchantments.MOISTURIZE, event) || isDisabled(event.getPlayer(), RegisterEnchantments.MOISTURIZE)) return;

		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) return; // off hand packet, ignore.
			Player player = event.getPlayer();
			ItemStack item = event.getItem();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.MOISTURIZE)) {
				Block block = event.getClickedBlock();
				ItemMoisturizeType type = ItemMoisturizeType.getMoisturizeType(block.getType());
				if (type != null) {
					Sound sound = null;
					switch (type.getName().toLowerCase(Locale.ROOT)) {
						case "extinguish":
							if (block.getType() == Material.CAMPFIRE) {
								Campfire fire = (Campfire) block.getBlockData();
								if (fire.isLit()) sound = Sound.BLOCK_WATER_AMBIENT;
							}
							break;
						case "wet":
						case "unsmelt":
							sound = Sound.ENTITY_SHEEP_SHEAR;
							break;
						case "waterlog":
							if (block.getBlockData() instanceof Waterlogged) {
								Waterlogged water = (Waterlogged) block.getBlockData();
								if (block.getType().isInteractable() && player.isSneaking() && !water.isWaterlogged() || !block.getType().isInteractable() && !water.isWaterlogged()) sound = Sound.ENTITY_SHEEP_SHEAR;
							}
							break;
					}
					if (sound != null) {
						BlockState state = block.getState();

						Event blockEvent = null;

						switch (type.getName().toLowerCase(Locale.ROOT)) {
							case "extinguish":
								break;
							case "wet":
							case "unsmelt":
								blockEvent = new EntityBlockFormEvent(player, block, state);
								break;
							case "waterlog":
								blockEvent = new PlayerBucketEmptyEvent(player, block, block, event.getBlockFace(), Material.WATER_BUCKET, item);
								break;
						}

						if (blockEvent != null) {
							Bukkit.getPluginManager().callEvent(blockEvent);
							if (((Cancellable) blockEvent).isCancelled()) return;
						}
						MoisturizeEvent moisturize = new MoisturizeEvent(player, item, block, type, sound);
						Bukkit.getPluginManager().callEvent(moisturize);

						if (!moisturize.isCancelled()) {
							Block moisturizeBlock = moisturize.getBlock();

							switch (type.getName().toLowerCase(Locale.ROOT)) {
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
									if (moisturizeBlock.getType().name().contains("CONCRETE")) AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_ADD_WATER, "concrete");
									moisturizeBlock.setType(ItemMoisturizeType.getWet(moisturizeBlock.getType()));
									DamageUtils.damageItem(player, item);
									player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
									player.incrementStatistic(Statistic.USE_ITEM, item.getType());

									break;
								case "unsmelt":
									if (moisturizeBlock.getType() == Material.CRACKED_STONE_BRICKS) AdvancementUtils.awardCriteria(player, ESAdvancement.REPAIRED, "broken_bricks");
									moisturizeBlock.setType(ItemMoisturizeType.getUnsmelt(moisturizeBlock.getType()));

									DamageUtils.damageItem(player, item);
									player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
									player.incrementStatistic(Statistic.USE_ITEM, item.getType());
									event.setCancelled(true);
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

	private void overkill(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.OVERKILL)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.OVERKILL)) {
				event.setCancelled(false);
				if (!canRun(RegisterEnchantments.OVERKILL, event)) return;
				boolean takeArrow = player.getGameMode() != GameMode.CREATIVE && !EnchantmentUtils.hasEnchantment(item, Enchantment.ARROW_INFINITE);
				OverkillEvent overkill = new OverkillEvent(player, item, takeArrow, player.getInventory().all(Material.ARROW).size() > 0, 0.4);
				Bukkit.getPluginManager().callEvent(overkill);

				if (!overkill.isCancelled() && !overkill.willCancel()) {
					if (overkill.takeArrow() && overkill.hasArrow()) {
						ItemStack[] contents = overkill.getPlayer().getInventory().getContents();
						ItemStack[] extraContents = overkill.getPlayer().getInventory().getExtraContents();
						ItemStack[] allContents = Arrays.copyOf(contents, contents.length + extraContents.length);
						System.arraycopy(extraContents, 0, allContents, contents.length, extraContents.length);
						for(int i = 0; i < allContents.length; i++) {
							ItemStack removeItem = player.getInventory().getItem(i);
							if (removeItem != null && removeItem.getType().equals(Material.ARROW)) if (removeItem.getAmount() - 1 <= 0) {
								player.getInventory().setItem(i, new ItemStack(Material.AIR));
								break;
							} else {
								removeItem.setAmount(removeItem.getAmount() - 1);
								break;
							}
						}
					}
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					Arrow arrow = player.launchProjectile(Arrow.class);
					arrow.setMetadata("overkill", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					if (!overkill.takeArrow()) arrow.setMetadata("no_pickup", new FixedMetadataValue(EnchantmentSolution.getPlugin(), true));
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
						arrow.setVelocity(arrow.getVelocity().multiply(overkill.getSpeed()));
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
						ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
						esPlayer.setCooldown(RegisterEnchantments.OVERKILL);
					}, 0l);
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void splatterFest(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.SPLATTER_FEST)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SPLATTER_FEST)) {
				event.setCancelled(false);
				if (!canRun(RegisterEnchantments.SPLATTER_FEST, event)) return;
				SplatterFestEvent splatterFest = new SplatterFestEvent(player, item, player.getGameMode() != GameMode.CREATIVE, player.getInventory().all(Material.EGG).size() > 0);
				Bukkit.getPluginManager().callEvent(splatterFest);

				if (!splatterFest.isCancelled() && !splatterFest.willCancel()) {
					if (splatterFest.takeEgg() && splatterFest.hasEgg()) {
						ItemStack[] contents = splatterFest.getPlayer().getInventory().getContents();
						ItemStack[] extraContents = splatterFest.getPlayer().getInventory().getExtraContents();
						ItemStack[] allContents = Arrays.copyOf(contents, contents.length + extraContents.length);
						System.arraycopy(extraContents, 0, allContents, contents.length, extraContents.length);
						for(int i = 0; i < allContents.length; i++) {
							ItemStack removeItem = player.getInventory().getItem(i);
							if (removeItem != null && removeItem.getType().equals(Material.EGG)) if (removeItem.getAmount() - 1 <= 0) {
								player.getInventory().setItem(i, new ItemStack(Material.AIR));
								break;
							} else {
								removeItem.setAmount(removeItem.getAmount() - 1);
								break;
							}
						}
					}
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					player.incrementStatistic(Statistic.USE_ITEM, Material.EGG);
					Egg egg = player.launchProjectile(Egg.class);
					egg.setMetadata("splatter_fest", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					if (!splatterFest.takeEgg()) egg.setMetadata("hatch_egg", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 1, 1);
					ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
					esPlayer.setCooldown(RegisterEnchantments.SPLATTER_FEST);
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void stickyHold(PlayerItemBreakEvent event) {
		if (!canRun(RegisterEnchantments.STICKY_HOLD, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.STICKY_HOLD)) return;
		ItemStack item = event.getBrokenItem();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.STICKY_HOLD)) {
			ItemStack finalItem = item.clone();
			Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
				ItemStack stickItem = PersistenceNMS.createStickyHold(finalItem);
				ItemUtils.giveItemToPlayer(player, stickItem, player.getLocation(), false);
				AdvancementUtils.awardCriteria(player, ESAdvancement.STICKY_BEES, "break", 1);
			}, 1l);
		}
	}

	private void zeal(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			Player player = event.getPlayer();
			if (isDisabled(player, RegisterEnchantments.ZEAL)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.ZEAL)) {
				event.setCancelled(false);
				if (!canRun(RegisterEnchantments.ZEAL, event)) return;
				boolean takeFireCharge = player.getGameMode() != GameMode.CREATIVE;
				ZealEvent zeal = new ZealEvent(player, item, takeFireCharge, player.getInventory().all(Material.FIRE_CHARGE).size() > 0);
				Bukkit.getPluginManager().callEvent(zeal);

				if (!zeal.isCancelled() && !zeal.willCancel()) {
					if (zeal.takeFireCharge() && zeal.hasFireCharge()) {
						ItemStack[] contents = zeal.getPlayer().getInventory().getContents();
						ItemStack[] extraContents = zeal.getPlayer().getInventory().getExtraContents();
						ItemStack[] allContents = Arrays.copyOf(contents, contents.length + extraContents.length);
						System.arraycopy(extraContents, 0, allContents, contents.length, extraContents.length);
						for(int i = 0; i < allContents.length; i++) {
							ItemStack removeItem = player.getInventory().getItem(i);
							if (removeItem != null && removeItem.getType().equals(Material.FIRE_CHARGE)) if (removeItem.getAmount() - 1 <= 0) {
								player.getInventory().setItem(i, new ItemStack(Material.AIR));
								break;
							} else {
								removeItem.setAmount(removeItem.getAmount() - 1);
								break;
							}
						}
					}
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					Fireball fireball = player.launchProjectile(Fireball.class);
					fireball.setMetadata("zeal", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
					player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 1);
					ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
					esPlayer.setCooldown(RegisterEnchantments.ZEAL);
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void movementListener(PlayerChangeCoordsEvent event, Enchantment enchantment) {
		if (!canRun(enchantment, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, enchantment)) return;
		Location loc = player.getLocation();
		if (player.isFlying() || player.isGliding() || player.isInsideVehicle()) return;
		ItemStack boots = player.getInventory().getBoots();
		if (EnchantmentUtils.hasEnchantment(boots, enchantment) && enchantment == RegisterEnchantments.MAGMA_WALKER && player.isOnGround()) WalkerUtils.updateBlocks(player, boots, loc, enchantment, Arrays.asList(Material.LAVA), Material.MAGMA_BLOCK, "MagmaWalker");
		else if (EnchantmentUtils.hasEnchantment(boots, enchantment) && enchantment == RegisterEnchantments.VOID_WALKER && LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), false)) WalkerUtils.updateBlocks(player, boots, loc, enchantment, Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR), Material.OBSIDIAN, "VoidWalker");
		else if (EnchantmentUtils.hasEnchantment(boots, enchantment) && enchantment == RegisterEnchantments.VOID_WALKER && LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), true)) WalkerUtils.updateBlocks(player, boots, event.getTo(), enchantment, Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR), Material.OBSIDIAN, "VoidWalker");
	}
}
