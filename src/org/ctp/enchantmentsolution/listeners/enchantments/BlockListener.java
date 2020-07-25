package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.crashapi.item.ItemSerialization;
import org.ctp.enchantmentsolution.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.ItemPlaceType;
import org.ctp.enchantmentsolution.events.blocks.HeightWidthEvent;
import org.ctp.enchantmentsolution.events.blocks.LightWeightEvent;
import org.ctp.enchantmentsolution.events.blocks.WandEvent;
import org.ctp.enchantmentsolution.events.modify.GoldDiggerEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent.ExpShareType;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.listeners.VeinMinerListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		runMethod(this, "lightWeight", event, EntityChangeBlockEvent.class);
	}

	private void curseOfLag(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) return;
		Player player = event.getPlayer();
		if (player != null) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				LagEvent lag = new LagEvent(player, player.getLocation(), AbilityUtils.createEffects(player));

				Bukkit.getPluginManager().callEvent(lag);
				if (!lag.isCancelled() && lag.getEffects().size() > 0) {
					Location loc = lag.getLocation();
					for(ParticleEffect effect: lag.getEffects())
						loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
					if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
					AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
				}
			}
		}
	}

	private void expShare(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) return;
		Player player = event.getPlayer();
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if (killItem != null && ItemUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getExpToDrop();
			if (exp > 0) {
				int level = ItemUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

				ExpShareEvent experienceEvent = new ExpShareEvent(player, level, ExpShareType.BLOCK, exp, AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) event.setExpToDrop(experienceEvent.getNewExp());
			}
		}
	}

	private void goldDigger(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GOLD_DIGGER, event)) return;
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) if (!ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
			if (goldDigger != null) {
				int level = ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER);
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, level, event.getBlock(), goldDigger, GoldDiggerCrop.getExp(event.getBlock().getType(), level));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					AbilityUtils.dropExperience(goldDiggerEvent.getBlock().getLocation(), goldDiggerEvent.getExpToDrop());
					ItemUtils.dropItem(goldDiggerEvent.getGoldItem(), goldDiggerEvent.getBlock().getLocation());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock", goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void smeltery(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.SMELTERY, event)) return;
		Block blockBroken = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		if (item != null) if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) SmelteryUtils.handleSmeltery(event, player, blockBroken, item);
	}

	private void telepathy(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.TELEPATHY, event)) return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) if (ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) TelepathyUtils.handleTelepathy(event, player, item, block);
	}

	private void heightWidth(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!canRun(event, false, RegisterEnchantments.WIDTH_PLUS_PLUS, RegisterEnchantments.HEIGHT_PLUS_PLUS)) return;
		if (BlockUtils.multiBlockBreakContains(event.getBlock().getLocation())) return;
		if (!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) return;
		if (EnchantmentSolution.getPlugin().getVeinMiner() != null && VeinMinerListener.hasVeinMiner(player)) return;
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			int heightPlusPlus = ItemUtils.getLevel(item, RegisterEnchantments.HEIGHT_PLUS_PLUS);
			int widthPlusPlus = ItemUtils.getLevel(item, RegisterEnchantments.WIDTH_PLUS_PLUS);
			boolean hasWidthHeight = false;
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.WIDTH_PLUS_PLUS) && ItemUtils.hasEnchantment(item, RegisterEnchantments.WIDTH_PLUS_PLUS)) {
				hasWidthHeight = true;
				float yaw = player.getLocation().getYaw() % 360;
				while (yaw < 0)
					yaw += 360;
				if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) xt = widthPlusPlus;
				else
					zt = widthPlusPlus;

			}
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.HEIGHT_PLUS_PLUS) && ItemUtils.hasEnchantment(item, RegisterEnchantments.HEIGHT_PLUS_PLUS)) {
				hasWidthHeight = true;
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw() % 360;
				while (yaw < 0)
					yaw += 360;
				if (pitch > 53 || pitch <= -53) {
					if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) zt = heightPlusPlus;
					else
						xt = heightPlusPlus;
				} else
					yt = heightPlusPlus;

			}
			Material original = event.getBlock().getType();
			if (hasWidthHeight && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getBreakTypes() != null && ItemBreakType.getType(item.getType()).getBreakTypes().contains(original)) {
				Collection<Location> blocks = new ArrayList<Location>();
				Block block = event.getBlock();
				item = player.getInventory().getItemInMainHand();
				if (item == null || MatData.isAir(item.getType())) return;
				for(int x = 0; x <= xt; x++)
					for(int y = 0; y <= yt; y++)
						for(int z = 0; z <= zt; z++) {
							if (x == 0 && y == 0 && z == 0) continue;
							addHeightWidthBlock(blocks, item, original, block, x, y, z);
							addHeightWidthBlock(blocks, item, original, block, -x, y, z);
							addHeightWidthBlock(blocks, item, original, block, x, -y, z);
							addHeightWidthBlock(blocks, item, original, block, x, y, -z);
							addHeightWidthBlock(blocks, item, original, block, -x, -y, z);
							addHeightWidthBlock(blocks, item, original, block, -x, y, -z);
							addHeightWidthBlock(blocks, item, original, block, x, -y, -z);
							addHeightWidthBlock(blocks, item, original, block, -x, -y, -z);
						}

				HeightWidthEvent heightWidth = new HeightWidthEvent(blocks, block, player, heightPlusPlus, widthPlusPlus);
				Bukkit.getPluginManager().callEvent(heightWidth);

				if (!heightWidth.isCancelled()) {
					boolean async = ConfigString.MULTI_BLOCK_ASYNC.getBoolean();
					if (async) {
						for(Location b: heightWidth.getBlocks())
							BlockUtils.addMultiBlockBreak(b);
						new AsyncBlockController(player, item, heightWidth.getBlock(), heightWidth.getBlocks());
					} else {
						int blocksBroken = 0;
						for(Location b: heightWidth.getBlocks()) {
							BlockUtils.addMultiBlockBreak(b);
							if (BlockUtils.multiBreakBlock(player, item, b)) blocksBroken++;
						}
						AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", blocksBroken);
					}
				}
			}
		}
	}

	private void wand(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!canRun(RegisterEnchantments.WAND, event)) return;
		if (AbilityUtils.getWandBlocks().contains(event.getBlock().getLocation())) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.WAND)) {
				ItemStack offhand = player.getInventory().getItemInOffHand();
				if (!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) return;
				float yaw = player.getLocation().getYaw() % 360;
				float pitch = player.getLocation().getPitch();
				while (yaw < 0)
					yaw += 360;
				int level = ItemUtils.getLevel(item, RegisterEnchantments.WAND);
				Block clickedBlock = event.getBlock();
				if (pitch > 53 || pitch <= -53) {
					xt = level;
					zt = level;
				} else {
					yt = level;
					if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) xt = level;
					else
						zt = level;
				}
				Location rangeOne = new Location(clickedBlock.getWorld(), clickedBlock.getX() - xt, clickedBlock.getY() - yt, clickedBlock.getZ() - zt);
				Location rangeTwo = new Location(clickedBlock.getWorld(), clickedBlock.getX() + xt, clickedBlock.getY() + yt, clickedBlock.getZ() + zt);

				if (LocationUtils.getIntersecting(rangeOne, rangeTwo, player.getLocation(), player.getEyeLocation())) return;
				Collection<Location> blocks = new ArrayList<Location>();

				for(int x = 0; x <= xt; x++)
					for(int y = 0; y <= yt; y++)
						for(int z = 0; z <= zt; z++) {
							if (x == 0 && y == 0 && z == 0) continue;
							addWandBlock(blocks, item, clickedBlock, x, y, z);
							addWandBlock(blocks, item, clickedBlock, -x, y, z);
							addWandBlock(blocks, item, clickedBlock, x, -y, z);
							addWandBlock(blocks, item, clickedBlock, x, y, -z);
							addWandBlock(blocks, item, clickedBlock, -x, -y, z);
							addWandBlock(blocks, item, clickedBlock, -x, y, -z);
							addWandBlock(blocks, item, clickedBlock, x, -y, -z);
							addWandBlock(blocks, item, clickedBlock, -x, -y, -z);
						}

				WandEvent wand = new WandEvent(blocks, player, level);
				Bukkit.getPluginManager().callEvent(wand);

				if (!hasItem(player, offhand)) return;
				if (!wand.isCancelled()) {
					for(Location loc: wand.getBlocks()) {
						Block block = loc.getBlock();
						offhand = player.getInventory().getItemInOffHand();
						if (!hasItem(player, offhand)) return;
						AbilityUtils.addWandBlock(loc);
						if (block.getType() == Material.TORCH) AdvancementUtils.awardCriteria(player, ESAdvancement.BREAKER_BREAKER, "torch");
						Collection<ItemStack> drops = block.getDrops();
						BlockData oldData = block.getBlockData();
						Material oldType = block.getType();
						block.setType(offhand.getType());
						if (block.getBlockData() instanceof Directional) {
							Directional directional = (Directional) block.getBlockData();
							directional.setFacing(((Directional) clickedBlock).getFacing());
							block.setBlockData(directional);
						}
						if (block.getBlockData() instanceof Orientable) {
							Orientable orientable = (Orientable) block.getBlockData();
							orientable.setAxis(((Orientable) clickedBlock).getAxis());
							block.setBlockData(orientable);
						}
						if (block.getBlockData() instanceof Rotatable) {
							Rotatable rotatable = (Rotatable) block.getBlockData();
							rotatable.setRotation(((Rotatable) clickedBlock).getRotation());
							block.setBlockData(rotatable);
						}
						BlockPlaceEvent newEvent = new BlockPlaceEvent(block, block.getState(), clickedBlock, item, player, true, EquipmentSlot.OFF_HAND);
						Bukkit.getServer().getPluginManager().callEvent(newEvent);
						if (!newEvent.isCancelled()) {
							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
							remove(player, offhand);
							block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
							for(ItemStack drop: drops)
								ItemUtils.dropItem(drop, newEvent.getBlock().getLocation());
						} else {
							block.setType(oldType);
							block.setBlockData(oldData);
						}
						AbilityUtils.removeWandBlock(loc);
					}
					DamageUtils.damageItem(player, item, 1, 2);
					if (item == null || MatData.isAir(item.getType())) AdvancementUtils.awardCriteria(player, ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT, "break");
				}
			}
		}
	}

	private void lightWeight(EntityChangeBlockEvent event) {
		if (!canRun(RegisterEnchantments.LIGHT_WEIGHT, event)) return;
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (event.getBlock().getType() == Material.FARMLAND && event.getTo() == Material.DIRT) {
				ItemStack boots = player.getInventory().getBoots();
				if (boots != null && ItemUtils.hasEnchantment(boots, RegisterEnchantments.LIGHT_WEIGHT)) {
					LightWeightEvent lightWeight = new LightWeightEvent(event.getBlock(), player);
					Bukkit.getPluginManager().callEvent(lightWeight);

					if (!lightWeight.isCancelled()) {
						event.setCancelled(true);
						Block block = event.getBlock().getRelative(BlockFace.UP);
						if (block.getBlockData() instanceof Ageable) {
							Ageable crop = (Ageable) block.getBlockData();
							if (crop.getAge() == crop.getMaximumAge()) AdvancementUtils.awardCriteria(player, ESAdvancement.LIGHT_AS_A_FEATHER, "boots");
						}
					}
				}
			}
		}

	}

	private boolean hasItem(Player player, ItemStack item) {
		if (player.getGameMode() == GameMode.CREATIVE) return true;
		for(int i = 0; i < 36; i++) {
			ItemStack removeItem = player.getInventory().getItem(i);
			ItemSerialization serial = EnchantmentSolution.getPlugin().getItemSerial();
			if (removeItem != null && removeItem.getType() == item.getType() && serial.itemToData(removeItem).equals(serial.itemToData(item))) return true;
		}
		return false;
	}

	private void remove(Player player, ItemStack item) {
		if (player.getGameMode() == GameMode.CREATIVE) return;
		for(int i = 0; i < 36; i++) {
			ItemStack removeItem = player.getInventory().getItem(i);
			ItemSerialization serial = EnchantmentSolution.getPlugin().getItemSerial();
			if (removeItem != null && removeItem.getType() == item.getType() && serial.itemToData(removeItem).equals(serial.itemToData(item))) {
				int left = removeItem.getAmount() - 1;
				if (left == 0) {
					removeItem.setAmount(0);
					removeItem.setType(Material.AIR);
				} else
					removeItem.setAmount(left);
				return;
			}
		}
	}

	private Collection<Location> addHeightWidthBlock(Collection<Location> blocks, ItemStack tool, Material original, Block relative, int x, int y, int z) {
		Block block = relative.getRelative(x, y, z);
		if (BlockUtils.multiBlockBreakContains(block.getLocation())) return blocks;
		List<String> pickBlocks = ItemBreakType.WOODEN_PICKAXE.getDiamondPickaxeBlocks();
		if (!pickBlocks.contains(original.name()) && pickBlocks.contains(block.getType().name())) return blocks;

		if (blocks.contains(block.getLocation())) return blocks;

		if (ItemBreakType.getType(tool.getType()).getBreakTypes().contains(block.getType())) blocks.add(block.getLocation());
		return blocks;
	}

	private Collection<Location> addWandBlock(Collection<Location> blocks, ItemStack tool, Block relative, int x, int y, int z) {
		Block block = relative.getRelative(x, y, z);
		if (blocks.contains(block.getLocation())) return blocks;
		if (!block.getType().isSolid()) blocks.add(block.getLocation());
		return blocks;
	}
}
