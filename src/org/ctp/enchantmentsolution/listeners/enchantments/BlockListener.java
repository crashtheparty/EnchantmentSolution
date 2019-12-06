package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.ItemPlaceType;
import org.ctp.enchantmentsolution.events.ExperienceEvent;
import org.ctp.enchantmentsolution.events.ExperienceEvent.ExpShareType;
import org.ctp.enchantmentsolution.events.blocks.GoldDiggerEvent;
import org.ctp.enchantmentsolution.events.blocks.HeightWidthEvent;
import org.ctp.enchantmentsolution.events.blocks.WandEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.listeners.VeinMinerListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.utils.abillityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.*;

@SuppressWarnings("unused")
public class BlockListener extends Enchantmentable {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		runMethod(this, "expShare", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakHighest(BlockBreakEvent event) {
		runMethod(this, "heightWidth", event, BlockBreakEvent.class);
		runMethod(this, "curseOfLag", event, BlockBreakEvent.class);
		runMethod(this, "goldDigger", event, BlockBreakEvent.class);
		runMethod(this, "telepathy", event, BlockBreakEvent.class);
		runMethod(this, "smeltery", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlaceHighest(BlockPlaceEvent event) {
		runMethod(this, "wand", event, BlockPlaceEvent.class);
	}

	private void curseOfLag(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) {
			return;
		}
		Player player = event.getPlayer();
		if (player != null) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				LagEvent lag = new LagEvent(player, player.getLocation(), AbilityUtils.createEffects(player));

				Bukkit.getPluginManager().callEvent(lag);
				if (!lag.isCancelled() && lag.getEffects().size() > 0) {
					Location loc = lag.getLocation();
					for(ParticleEffect effect: lag.getEffects()) {
						loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(),
						effect.getVarY(), effect.getVarZ());
					}
					if (lag.getSound() != null) {
						loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
					}
					AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
				}
			}
		}
	}

	private void expShare(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if (killItem != null && ItemUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getExpToDrop();
			if (exp > 0) {
				int level = ItemUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

				ExperienceEvent experienceEvent = new ExperienceEvent(player, ExpShareType.BLOCK, exp,
				AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
					event.setExpToDrop(experienceEvent.getNewExp());
				}
			}
		}
	}

	private void goldDigger(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GOLD_DIGGER, event)) {
			return;
		}
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (!ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
				if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
					if (goldDigger != null) {
						GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, event.getBlock(), goldDigger,
						GoldDiggerCrop.getExp(event.getBlock().getType(),
						ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER)));
						Bukkit.getPluginManager().callEvent(goldDiggerEvent);

						if (!goldDiggerEvent.isCancelled()) {
							AbilityUtils.dropExperience(goldDiggerEvent.getBlock().getLocation(),
							goldDiggerEvent.getExpToDrop());
							ItemUtils.dropItem(goldDiggerEvent.getGoldItem(), goldDiggerEvent.getBlock().getLocation());
							AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock",
							goldDigger.getAmount());
							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
							DamageUtils.damageItem(player, item);
						}
					}
				}
			}
		}
	}

	private void smeltery(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.SMELTERY, event)) {
			return;
		}
		Block blockBroken = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		if (item != null) {
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) {
				SmelteryUtils.handleSmeltery(event, player, blockBroken, item);
			}
		}
	}

	private void telepathy(BlockBreakEvent event) {
		if(!canRun(RegisterEnchantments.TELEPATHY, event)) {
			return;
		}
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
				TelepathyUtils.handleTelepathy(event, player, item, block);
			}
		}
	}

	private void heightWidth(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!canRun(event, false, RegisterEnchantments.WIDTH_PLUS_PLUS, RegisterEnchantments.HEIGHT_PLUS_PLUS)) {
			return;
		}
		if(AbilityUtils.getHeightWidthBlocks().contains(event.getBlock())) {
			AbilityUtils.removeHeightWidthBlock(event.getBlock());
			return;
		}
		if(!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) {
			return;
		}
		if(EnchantmentSolution.getPlugin().getVeinMiner() != null && VeinMinerListener.hasVeinMiner(player)) {
			return;
		}
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			boolean hasWidthHeight = false;
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.WIDTH_PLUS_PLUS) && ItemUtils
			.hasEnchantment(item, RegisterEnchantments.WIDTH_PLUS_PLUS)) {
				hasWidthHeight = true;
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if(yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
					xt = ItemUtils.getLevel(item, RegisterEnchantments.WIDTH_PLUS_PLUS);
				} else {
					zt = ItemUtils.getLevel(item, RegisterEnchantments.WIDTH_PLUS_PLUS);
				}

			}
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.HEIGHT_PLUS_PLUS) && ItemUtils
			.hasEnchantment(item, RegisterEnchantments.HEIGHT_PLUS_PLUS)) {
				hasWidthHeight = true;
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if(pitch > 53 || pitch <= -53) {
					if(yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
						zt = ItemUtils.getLevel(item, RegisterEnchantments.HEIGHT_PLUS_PLUS);
					} else {
						xt = ItemUtils.getLevel(item, RegisterEnchantments.HEIGHT_PLUS_PLUS);
					}
				} else {
					yt = ItemUtils.getLevel(item, RegisterEnchantments.HEIGHT_PLUS_PLUS);
				}

			}
			Material original = event.getBlock().getType();
			if(hasWidthHeight && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getBreakTypes() != null
			&& ItemBreakType.getType(item.getType()).getBreakTypes().contains(original)) {
				int start = 1;
				int blocksBroken = 0;
				Collection<Block> blocks = new ArrayList<Block>();
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
								if(Math.abs(x) == xBegin && Math.abs(y) == yBegin
								|| Math.abs(x) == xBegin && Math.abs(z) == zBegin
								|| Math.abs(y) == yBegin && Math.abs(z) == zBegin) {
									item = player.getInventory().getItemInMainHand();
									if(item == null || item.getType() == Material.AIR) {
										return;
									}
									if(x == 0 && y == 0 && z == 0) {
										continue;
									}
									Block block = event.getBlock().getRelative(x, y, z);
									if(original != Material.OBSIDIAN && block.getType() == Material.OBSIDIAN) {
										continue;
									}

									if(ItemBreakType.getType(item.getType()).getBreakTypes().contains(block.getType())) {
										blocks.add(block);
									}
								}
							}
						}
					}
					start ++;
				}

				HeightWidthEvent heightWidth = new HeightWidthEvent(blocks, player);
				Bukkit.getPluginManager().callEvent(heightWidth);

				if(!heightWidth.isCancelled()) {
					for(Block block : heightWidth.getBlocks()) {
						AbilityUtils.addHeightWidthBlock(block);
						BlockBreakEvent newEvent = new BlockBreakEvent(block, player);
						int exp = 0;
						if(!ItemUtils.hasEnchantment(item, Enchantment.SILK_TOUCH)) {
							switch(newEvent.getBlock().getType().name()) {
								case "COAL_ORE":
									exp = (int) (Math.random() * 3);
									break;
								case "DIAMOND_ORE":
								case "EMERALD_ORE":
									exp = (int) (Math.random() * 5) + 3;
									break;
								case "LAPIS_ORE":
								case "NETHER_QUARTZ_ORE":
									exp = (int) (Math.random() * 4) + 2;
									break;
								case "REDSTONE_ORE":
									exp = (int) (Math.random() * 5) + 1;
									break;
								case "SPAWNER":
									exp = (int) (Math.random() * 29) + 15;
									break;
							}
						}
						newEvent.setExpToDrop(exp);
						Bukkit.getServer().getPluginManager().callEvent(newEvent);
						if(item != null && newEvent.getBlock().getType() != Material.AIR && !newEvent.isCancelled()) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.FAST_AND_FURIOUS, "diamond_pickaxe");
							if(newEvent.getBlock().getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
								int num = ((Snow) newEvent.getBlock().getBlockData()).getLayers();
								ItemUtils.dropItem(new ItemStack(Material.SNOWBALL, num), newEvent.getBlock().getLocation());
							}
							blocksBroken ++;
							player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
							newEvent.getBlock().breakNaturally(item);
							AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), newEvent.getExpToDrop());
							DamageUtils.damageItem(player, item);
						} else {
							AbilityUtils.removeHeightWidthBlock(event.getBlock());
						}
					}
				}

				AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", blocksBroken);
			}
		}
	}

	private void wand(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!canRun(RegisterEnchantments.WAND, event)) {
			return;
		}
		if(AbilityUtils.getWandBlocks().contains(event.getBlock())) {
			AbilityUtils.removeWandBlock(event.getBlock());
			return;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.WAND)) {
				ItemStack offhand = player.getInventory().getItemInOffHand();
				if(!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) {
					return;
				}
				float yaw = player.getLocation().getYaw() % 360;
				float pitch = player.getLocation().getPitch();
				while(yaw < 0) {
					yaw += 360;
				}
				Block clickedBlock = event.getBlock();
				if(pitch > 53 || pitch <= -53) {
					xt = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
					zt = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
				} else {
					yt = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
					if(yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
						xt = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
					} else {
						zt = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
					}
				}
				Location rangeOne = new Location(clickedBlock.getWorld(), clickedBlock.getX() - xt, clickedBlock.getY() - yt, clickedBlock.getZ() - zt);
				Location rangeTwo = new Location(clickedBlock.getWorld(), clickedBlock.getX() + xt, clickedBlock.getY() + yt, clickedBlock.getZ() + zt);

				if(LocationUtils.getIntersecting(rangeOne, rangeTwo, player.getLocation(), player.getEyeLocation())) {
					return;
				}
				int start = 0;
				Collection<Block> blocks = new ArrayList<Block>();

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
								if(Math.abs(x) == xBegin && Math.abs(y) == yBegin
								|| Math.abs(x) == xBegin && Math.abs(z) == zBegin
								|| Math.abs(y) == yBegin && Math.abs(z) == zBegin) {
									offhand = player.getInventory().getItemInOffHand();
									if(x == 0 && y == 0 && z == 0) {
										continue;
									}
									if(offhand == null || offhand.getType() == Material.AIR) {
										continue;
									}
									Block block = clickedBlock.getRelative(x, y, z);
									if(!block.getType().isSolid()) {
										blocks.add(block);
									}
								}
							}
						}
					}
					start ++;
				}
				WandEvent wand = new WandEvent(blocks, player);
				Bukkit.getPluginManager().callEvent(wand);

				if(!hasItem(player, offhand)) {
					return;
				}
				if(!wand.isCancelled()) {
					for(Block block : wand.getBlocks()) {
						offhand = player.getInventory().getItemInOffHand();
						if(!hasItem(player, offhand)) {
							return;
						}
						AbilityUtils.addWandBlock(block);
						if(block.getType() == Material.TORCH) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.BREAKER_BREAKER, "torch");
						}
						Collection<ItemStack> drops = block.getDrops();
						BlockData oldData = block.getBlockData();
						Material oldType = block.getType();
						block.setType(offhand.getType());
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
							remove(player, offhand);
							block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
							for(ItemStack drop : drops) {
								ItemUtils.dropItem(drop, newEvent.getBlock().getLocation());
							}
						} else {
							AbilityUtils.removeWandBlock(block);
							block.setType(oldType);
							block.setBlockData(oldData);
						}
						if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
							return;
						}
						if(item == null || item.getType() == Material.AIR) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT, "break");
						}
					}
					DamageUtils.damageItem(player, item, 1, 2);
				}
			}
		}
	}

	private boolean hasItem(Player player, ItemStack item) {
		if(player.getGameMode() == GameMode.CREATIVE) {
			return true;
		}
		for(int i = 0; i < 36; i++) {
			ItemStack removeItem = player.getInventory().getItem(i);
			if(removeItem != null) {
				if(removeItem.getType() == item.getType() && ItemSerialization.itemToData(removeItem).equals(ItemSerialization.itemToData(item))) {
					return true;
				}
			}
		}
		return false;
	}

	private void remove(Player player, ItemStack item) {
		if(player.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		for(int i = 0; i < 36; i++) {
			ItemStack removeItem = player.getInventory().getItem(i);
			if(removeItem != null) {
				if(removeItem.getType() == item.getType() && ItemSerialization.itemToData(removeItem).equals(ItemSerialization.itemToData(item))) {
					int left = removeItem.getAmount() - 1;
					if(left == 0) {
						player.getInventory().setItem(i, new ItemStack(Material.AIR));
					} else {
						removeItem.setAmount(left);
						player.getInventory().setItem(i, removeItem);
					}
					return;
				}
			}
		}
	}
}
