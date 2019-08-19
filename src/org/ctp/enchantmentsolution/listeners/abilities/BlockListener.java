package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.listeners.abilities.support.VeinMinerListener;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.items.*;
import org.ctp.enchantmentsolution.utils.items.nms.*;

@SuppressWarnings("unused")
public class BlockListener extends EnchantmentListener{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		runMethod(this, "expShare", event);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBreakHighest(BlockBreakEvent event) {
		runMethod(this, "heightWidth", event);
		runMethod(this, "curseOfLag", event);
		runMethod(this, "goldDigger", event);
		runMethod(this, "smeltery", event);
		runMethod(this, "telepathy", event);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockPlaceHighest(BlockPlaceEvent event) {
		runMethod(this, "wand", event);
	}
	
	private void curseOfLag(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.CURSE_OF_LAG, event)) return;
		Player player = event.getPlayer();
		if(player != null) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG)) {
				AbilityUtils.createEffects(player);
			}
		}
	}
	
	private void expShare(BlockBreakEvent event){
		if(!canRun(DefaultEnchantments.EXP_SHARE, event)) return;
		Player player = event.getPlayer();
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if(killItem != null && Enchantments.hasEnchantment(killItem, DefaultEnchantments.EXP_SHARE)){
			int exp = event.getExpToDrop();
			int level = Enchantments.getLevel(killItem, DefaultEnchantments.EXP_SHARE);
			event.setExpToDrop(AbilityUtils.setExp(exp, level));
		}
	}
	
	private void goldDigger(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.GOLD_DIGGER, event)) return;
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (!Enchantments
					.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.GOLD_DIGGER)) {
					Location loc = event.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
					if(goldDigger != null) {
						AbilityUtils.dropExperience(loc, 
								GoldDiggerCrop.getExp(event.getBlock().getType(), Enchantments.getLevel(item, DefaultEnchantments.GOLD_DIGGER)));
						Item droppedItem = player.getWorld().dropItem(
								loc,
								goldDigger);
						droppedItem.setVelocity(new Vector(0,0,0));
						AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock", goldDigger.getAmount());
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						super.damageItem(player, item);
					}
				}
			}
		}
	}
	
	private void smeltery(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.SMELTERY, event)) return;
		Block blockBroken = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		if(item != null) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(blockBroken, item);
				if(smelted != null) {
					if(smelted.getAmount() > 1 && smelted.getType() == Material.IRON_INGOT) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.IRONT_YOU_GLAD, "iron"); 
					}
					if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TELEPATHY) || !Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
						switch(event.getBlock().getType()) {
						case IRON_ORE:
						case GOLD_ORE:
							AbilityUtils.dropExperience(blockBroken.getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 3) + 1);
							break;
						default:
							break;
						}
						player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						McMMO.handleMcMMO(event, item);
						super.damageItem(player, item);
						event.getBlock().setType(Material.AIR);
						Item droppedItem = player.getWorld().dropItem(
								blockBroken.getLocation().add(0.5, 0.5, 0.5),
								smelted);
						droppedItem.setVelocity(new Vector(0,0,0));
					}
				}
			}
		}
	}

	private void telepathy(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.TELEPATHY, event)) return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				if(block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
				}
				Collection<ItemStack> drops = block.getDrops(item);
				if (ItemUtils.getShulkerBoxes().contains(block.getType())) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						if(ItemUtils.getShulkerBoxes().contains(drop.getType())) {
							BlockStateMeta im = (BlockStateMeta) drop.getItemMeta();
							Container container = (Container) block.getState();
							im.setBlockState(container);
							if(block.getMetadata("shulker_name") != null) {
								for(MetadataValue value : block.getMetadata("shulker_name")) {
									im.setDisplayName(value.asString());
								}
							}
							drop.setItemMeta(im);
							if(block.getMetadata("soulbound").size() > 0) {
								drop = Enchantments.addEnchantmentsToItem(drop, Arrays.asList(
										new EnchantmentLevel(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1)));
							}
							ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
							i.remove();
							AdvancementUtils.awardCriteria(player, ESAdvancement.HEY_IT_WORKS, "shulker_box");
						}
					}
					giveItems(player, item, block, drops);
					damageItem(event);
					return;
				} else if (block.getState() instanceof Container) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
					}
					Container container = (Container) block.getState();
					if(container.getInventory().getHolder() instanceof DoubleChest) {
						DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
						if (doubleChest.getLeftSide().getInventory().getLocation().equals(container.getLocation())) {
							Inventory inv = doubleChest.getLeftSide().getInventory();
							for(int j = 0; j < 27; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						} else {
							Inventory inv = doubleChest.getRightSide().getInventory();
							for(int j = 27; j < 54; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						}
					} else {
						for(ItemStack drop : container.getInventory().getContents()) {
							if(drop != null) {
								ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
							}
						}
						container.getInventory().clear();
					}
					damageItem(event);
					return;
				}
				if(block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
					int num = ((Snow) block.getBlockData()).getLayers();
					drops.add(new ItemStack(Material.SNOWBALL, num));
				}
				giveItems(player, item, block, drops);
				if (Enchantments.hasEnchantment(item, DefaultEnchantments.GOLD_DIGGER)) {
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
					if (goldDigger != null) {
						player.giveExp(GoldDiggerCrop.getExp(block.getType(),
								Enchantments.getLevel(item, DefaultEnchantments.GOLD_DIGGER)));
						ItemUtils.giveItemToPlayer(player, goldDigger, player.getLocation(), true);
					}
				}
				damageItem(event);
			}
		}
	}
	
	private void heightWidth(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!canRun(event, false, DefaultEnchantments.WIDTH_PLUS_PLUS, DefaultEnchantments.HEIGHT_PLUS_PLUS)) return;
		if(AbilityUtils.getHeightWidthBlocks().contains(event.getBlock())) {
			AbilityUtils.removeHeightWidthBlock(event.getBlock());
			return;
		}
		if(McMMO.getIgnoredPlayers() != null && McMMO.getIgnoredPlayers().contains(player)) {
			return;
		}
		if(EnchantmentSolution.getPlugin().getVeinMiner() != null && VeinMinerListener.hasVeinMiner(player)) {
			return;
		}
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			boolean hasWidthHeight = false;
			if (DefaultEnchantments.isEnabled(DefaultEnchantments.WIDTH_PLUS_PLUS) && Enchantments
					.hasEnchantment(item, DefaultEnchantments.WIDTH_PLUS_PLUS)) {
				hasWidthHeight = true;
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
					xt = Enchantments.getLevel(item, DefaultEnchantments.WIDTH_PLUS_PLUS);
				} else {
					zt = Enchantments.getLevel(item, DefaultEnchantments.WIDTH_PLUS_PLUS);
				}
				
			}
			if (DefaultEnchantments.isEnabled(DefaultEnchantments.HEIGHT_PLUS_PLUS) && Enchantments
					.hasEnchantment(item, DefaultEnchantments.HEIGHT_PLUS_PLUS)) {
				hasWidthHeight = true;
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if(pitch > 53 || pitch <= -53) {
					if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
						zt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
					} else {
						xt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
					}
				} else {
					yt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
				}
				
			}
			Material original = event.getBlock().getType();
			if(hasWidthHeight && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getBreakTypes() != null 
					&& ItemBreakType.getType(item.getType()).getBreakTypes().contains(original)) {
				int start = 1;
				int blocksBroken = 0;
				while(start <= xt || start <= yt || start <= zt) {
					int xBegin = start;
					int yBegin = start;
					int zBegin = start;
					if(xBegin > xt) {
						xBegin = xt;
					}
					if(yBegin > yt) {
						yBegin = yt;
					}
					if(zBegin > zt) {
						zBegin = zt;
					}
					for(int x = - xBegin; x <= xBegin; x++) {
						for(int y = - yBegin; y <= yBegin; y++) {
							for(int z = - zBegin; z <= zBegin; z++) {
								if((Math.abs(x) == xBegin && Math.abs(y) == yBegin) 
										|| (Math.abs(x) == xBegin && Math.abs(z) == zBegin) 
										|| (Math.abs(y) == yBegin && Math.abs(z) == zBegin)) {
									item = player.getInventory().getItemInMainHand();
									if(item == null || item.getType() == Material.AIR) return;
									if(x == 0 && y == 0 && z == 0) {
										continue;
									}
									Block block = event.getBlock().getRelative(x, y, z);
									if(original != Material.OBSIDIAN && block.getType() == Material.OBSIDIAN) {
										continue;
									}
									if(ItemBreakType.getType(item.getType()).getBreakTypes().contains(block.getType())) {
										AbilityUtils.addHeightWidthBlock(block);
										BlockBreakEvent newEvent = new BlockBreakEvent(block, player);
										int exp = 0;
										if(!Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)) {
											switch(newEvent.getBlock().getType()) {
											case COAL_ORE:
												exp = (int) (Math.random() * 3);
												break;
											case DIAMOND_ORE:
											case EMERALD_ORE:
												exp = (int) (Math.random() * 5) + 3;
												break;
											case LAPIS_ORE:
											case NETHER_QUARTZ_ORE:
												exp = (int) (Math.random() * 4) + 2;
												break;
											case REDSTONE_ORE:
												exp = (int) (Math.random() * 5) + 1;
												break;
											case SPAWNER:
												exp = (int) (Math.random() * 29) + 15;
												break;
											default:
												break;
											}
										}
										newEvent.setExpToDrop(exp);
										Bukkit.getServer().getPluginManager().callEvent(newEvent);
										if(item != null && newEvent.getBlock().getType() != Material.AIR && !newEvent.isCancelled()) {
											AdvancementUtils.awardCriteria(player, ESAdvancement.FAST_AND_FURIOUS, "diamond_pickaxe"); 
											if(newEvent.getBlock().getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
												int num = ((Snow) newEvent.getBlock().getBlockData()).getLayers();
												Item droppedItem = player.getWorld().dropItem(
														newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5),
														new ItemStack(Material.SNOWBALL, num));
												droppedItem.setVelocity(new Vector(0,0,0));
											}
											blocksBroken ++;
											player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
											player.incrementStatistic(Statistic.USE_ITEM, item.getType());
											newEvent.getBlock().breakNaturally(item);
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), newEvent.getExpToDrop());
											super.damageItem(player, item);
										} else {
											AbilityUtils.removeHeightWidthBlock(event.getBlock());
										}
									}
								}
							}
						}
					}
					start ++;
				}

				AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", blocksBroken); 
			}
		}
	}
	
	private void wand(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!canRun(DefaultEnchantments.WAND, event)) return;
		if(AbilityUtils.getWandBlocks().contains(event.getBlock())) {
			AbilityUtils.removeWandBlock(event.getBlock());
			return;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.WAND)) {
				ItemStack offhand = player.getInventory().getItemInOffHand();
				if(!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) return;
				float yaw = player.getLocation().getYaw() % 360;
				float pitch = player.getLocation().getPitch();
				while(yaw < 0) {
					yaw += 360;
				}
				Block clickedBlock = event.getBlock();
				if(pitch > 53 || pitch <= -53) {
					xt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					zt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
				} else {
					yt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
						xt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					} else {
						zt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					}
				}
				Location rangeOne = new Location(clickedBlock.getWorld(), clickedBlock.getX() - xt, clickedBlock.getY() - yt, clickedBlock.getZ() - zt);
				Location rangeTwo = new Location(clickedBlock.getWorld(), clickedBlock.getX() + xt, clickedBlock.getY() + yt, clickedBlock.getZ() + zt);
				
				if(LocationUtils.getIntersecting(rangeOne, rangeTwo, player.getLocation(), player.getEyeLocation())) {
					return;
				}
				item = player.getInventory().getItemInOffHand();
				int start = 0;
				boolean removed = false;
				while(start <= xt || start <= yt || start <= zt) {
					int xBegin = start;
					int yBegin = start;
					int zBegin = start;
					if(xBegin > xt) {
						xBegin = xt;
					}
					if(yBegin > yt) {
						yBegin = yt;
					}
					if(zBegin > zt) {
						zBegin = zt;
					}
					for(int x = - xBegin; x <= xBegin; x++) {
						for(int y = - yBegin; y <= yBegin; y++) {
							for(int z = - zBegin; z <= zBegin; z++) {
								if((Math.abs(x) == xBegin && Math.abs(y) == yBegin) 
										|| (Math.abs(x) == xBegin && Math.abs(z) == zBegin) 
										|| (Math.abs(y) == yBegin && Math.abs(z) == zBegin)) {
									item = player.getInventory().getItemInOffHand();
									if(x == 0 && y == 0 && z == 0) {
										removed = remove(player, item, 1);
										continue;
									}
									if(item == null || item.getType() == Material.AIR) continue;
									Block block = clickedBlock.getRelative(x, y, z);
									if(!block.getType().isSolid()) {
										AbilityUtils.addWandBlock(block);
										if(block.getType() == Material.TORCH) {
											AdvancementUtils.awardCriteria(player, ESAdvancement.BREAKER_BREAKER, "torch");
										}
										Collection<ItemStack> drops = block.getDrops();
										BlockData oldData = block.getBlockData();
										Material oldType = block.getType();
										block.setType(item.getType());
										if(block.getBlockData() instanceof Directional) {
											Directional directional = (Directional) block.getBlockData();
											directional.setFacing(((Directional) clickedBlock).getFacing());
											block.setBlockData(directional);
										}
										if(block.getBlockData() instanceof Orientable) {
											Orientable orientable = (Orientable) block.getBlockData();
											orientable.setAxis(((Orientable) clickedBlock).getAxis());
											block.setBlockData(orientable);
										}
										if(block.getBlockData() instanceof Rotatable) {
											Rotatable rotatable = (Rotatable) block.getBlockData();
											rotatable.setRotation(((Rotatable) clickedBlock).getRotation());
											block.setBlockData(rotatable);
										}
										BlockPlaceEvent newEvent = new BlockPlaceEvent(block, block.getState(), clickedBlock, item, player, true, EquipmentSlot.OFF_HAND);
										Bukkit.getServer().getPluginManager().callEvent(newEvent);
										if(item != null && !newEvent.isCancelled()) {
											player.incrementStatistic(Statistic.USE_ITEM, item.getType());
											block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
											removed = remove(player, item, 1);
											for(ItemStack drop : drops) {
												block.getWorld().dropItem(newEvent.getBlock().getLocation(), drop);
											}
										} else {
											AbilityUtils.removeWandBlock(block);
											block.setType(oldType);
											block.setBlockData(oldData);
										}
									}
								}
							}
						}
					}
					start ++;
				}
				if(!removed) {
					ItemStack removedItem = item.clone();
					removedItem.setAmount(1);
					ItemUtils.giveItemToPlayer(player, removedItem, player.getLocation(), false);
				}
				damageItem(event);
			}
		}
	}
	
	private boolean remove(Player player, ItemStack item, int remove) {
		if(player.getGameMode() == GameMode.CREATIVE) {
			return false;
		}
		for(int i = 0; i < 36; i++) {
			if(remove == 0) break;
			ItemStack removeItem = player.getInventory().getItem(i);
			if(removeItem != null) { 
				if(removeItem.getType() == item.getType() && ItemSerialization.itemToData(removeItem).equals(ItemSerialization.itemToData(item))) {
					int left = removeItem.getAmount() - remove;
					if(left < 0) {
						remove -= removeItem.getAmount();
						left = 0;
					} else {
						remove = 0;
					}
					if(left == 0) {
						player.getInventory().setItem(i, new ItemStack(Material.AIR));
					} else {
						removeItem.setAmount(left);
						player.getInventory().setItem(i, removeItem);
					}
				}
			}
		}
		if(remove > 0) {
			item.setAmount(item.getAmount() - remove); 
			if(item.getAmount() <= 0) {
				item.setType(Material.AIR);
			}
			return true;
		}
		return false;
	}
	
	private void damageItem(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemStack deadItem = super.damageItem(player, item, 1, 2);
		if(DamageUtils.getDamage(deadItem.getItemMeta()) > deadItem.getType().getMaxDurability()) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT, "break");
		}
	}
	
	private void damageItem(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
			switch(event.getBlock().getType()) {
			case IRON_ORE:
			case GOLD_ORE:
				event.setExpToDrop((int) (Math.random() * 3) + 1);
				break;
			default:
				break;
			}
		}
		AbilityUtils.giveExperience(player, event.getExpToDrop());
		player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
		player.incrementStatistic(Statistic.USE_ITEM, item.getType());
		super.damageItem(player, item);
		McMMO.handleMcMMO(event, item);
		event.getBlock().setType(Material.AIR);
	}
	
	private void giveItems(Player player, ItemStack item, Block block, Collection<ItemStack> drops) {
		if (Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = AbilityUtils.getFortuneItems(item, block,
					drops);
			for(ItemStack drop: fortuneItems) {
				ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
			}
		} else if (Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)
				&& AbilityUtils.getSilkTouchItem(block, item) != null) {
			ItemUtils.giveItemToPlayer(player, AbilityUtils.getSilkTouchItem(block, item),
					player.getLocation(), true);
		} else {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(block, item);
				if (smelted != null) {
					ItemUtils.giveItemToPlayer(player, smelted, player.getLocation(), true);
				} else {
					for(ItemStack drop: drops) {
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
					}
				}
			} else {
				for(ItemStack drop: drops) {
					ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
				}
			}
		}
	}
}
