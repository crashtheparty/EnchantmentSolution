package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSerialization;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.LogType;
import org.ctp.enchantmentsolution.events.blocks.GaiaEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.listeners.VeinMinerListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaTrees;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class BlockListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakHighest(BlockBreakEvent event) {
		runMethod(this, "gaia", event, BlockBreakEvent.class);
//		runMethod(this, "heightWidthDepth", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlaceHighest(BlockPlaceEvent event) {
//		runMethod(this, "wand", event, BlockPlaceEvent.class);
	}

	private void gaia(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GAIA, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.GAIA)) return;
		if (BlockUtils.multiBlockBreakContains(event.getBlock().getLocation())) return;
		if (!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) return;
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		Material mat = event.getBlock().getType();
		GaiaTrees tree = GaiaTrees.getTree(mat);
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (item != null && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GAIA) && tree != null) {
			List<Location> logs = new ArrayList<Location>();
			int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.GAIA);
			int maxBlocks = 50 + level * level * 50;
			logs.add(event.getBlock().getLocation());
			for(int i = 0; i < logs.size(); i++) {
				getLikeBlocks(logs, tree.getLog(), logs.get(i));
				if (logs.size() > maxBlocks) break;
			}
			GaiaEvent gaia = new GaiaEvent(logs, player, level);
			Bukkit.getPluginManager().callEvent(gaia);

			if (!gaia.isCancelled()) {
				for(Location b: logs)
					BlockUtils.addMultiBlockBreak(b, RegisterEnchantments.GAIA);
				esPlayer.addToGaiaController(item, event.getBlock(), logs, tree);
			}
		}
	}

	private void getLikeBlocks(List<Location> logs, LogType type, Location loc) {
		for(int x = -2; x <= 2; x++)
			for(int y = -1; y <= 1; y++)
				for(int z = -2; z <= 2; z++) {
					Block b = loc.getBlock().getRelative(x, y, z);
					Location l = b.getLocation();
					if (!logs.contains(l) && type.hasMaterial(b.getType())) logs.add(l);
				}
	}

	private void heightWidthDepth(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!canRun(event, false, RegisterEnchantments.DEPTH_PLUS_PLUS, RegisterEnchantments.WIDTH_PLUS_PLUS, RegisterEnchantments.HEIGHT_PLUS_PLUS)) return;
		if (BlockUtils.multiBlockBreakContains(event.getBlock().getLocation())) return;
		if (!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) return;
		if (EnchantmentSolution.getPlugin().getVeinMiner() != null && VeinMinerListener.hasVeinMiner(player)) return;
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (item != null && EnchantmentUtils.hasOneEnchantment(item, RegisterEnchantments.DEPTH_PLUS_PLUS, RegisterEnchantments.HEIGHT_PLUS_PLUS, RegisterEnchantments.WIDTH_PLUS_PLUS)) {
			ItemBreakType breakType = ItemBreakType.getType(item.getType());

			if (breakType == null) return;
			int xt = 0;
			int yt = 0;
			int zt = 0;
			int heightPlusPlus = isDisabled(player, RegisterEnchantments.HEIGHT_PLUS_PLUS) ? 0 : EnchantmentUtils.getLevel(item, RegisterEnchantments.HEIGHT_PLUS_PLUS);
			int widthPlusPlus = isDisabled(player, RegisterEnchantments.WIDTH_PLUS_PLUS) ? 0 : EnchantmentUtils.getLevel(item, RegisterEnchantments.WIDTH_PLUS_PLUS);
			int depthPlusPlus = isDisabled(player, RegisterEnchantments.DEPTH_PLUS_PLUS) ? 0 : EnchantmentUtils.getLevel(item, RegisterEnchantments.DEPTH_PLUS_PLUS);
			float pitch = player.getLocation().getPitch();
			float yaw = player.getLocation().getYaw() % 360;
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.WIDTH_PLUS_PLUS) && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.WIDTH_PLUS_PLUS)) {
				while (yaw < 0)
					yaw += 360;
				if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) xt = widthPlusPlus;
				else
					zt = widthPlusPlus;
			}
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.HEIGHT_PLUS_PLUS) && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.HEIGHT_PLUS_PLUS)) {
				while (yaw < 0)
					yaw += 360;
				if (pitch > 53 || pitch <= -53) {
					if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) zt = heightPlusPlus;
					else
						xt = heightPlusPlus;
				} else
					yt = heightPlusPlus;

			}
			String which = "";
			int times = 1;
			if (RegisterEnchantments.isEnabled(RegisterEnchantments.DEPTH_PLUS_PLUS) && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.DEPTH_PLUS_PLUS)) {
				if (pitch > 53 || pitch <= -53) {
					yt = depthPlusPlus;
					which = "yt";
					if (pitch > 53) times = -1;
				} else if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
					zt = depthPlusPlus;
					which = "zt";
					if (yaw > 45 && yaw <= 225) times = -1;
				} else {
					xt = depthPlusPlus;
					which = "xt";
					if (yaw > 45 && yaw <= 225) times = -1;
				}
			} else { /* placeholder */ }
			Material original = event.getBlock().getType();
			if (breakType.getBreakTypes() != null && breakType.getBreakTypes().contains(original)) {
				new ArrayList<Location>();
				Block block = event.getBlock();
				item = player.getInventory().getItemInMainHand();
				if (item == null || MatData.isAir(item.getType())) return;

				Location lowRange = null, highRange = null;;
				if (which.equals("")) {
					lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY() - yt, block.getZ() - zt);
					highRange = new Location(block.getWorld(), block.getX() + xt, block.getY() + yt, block.getZ() + zt);
				} else
					switch (which) {
						case "xt":
							if (times == -1) {
								lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY() - yt, block.getZ() - zt);
								highRange = new Location(block.getWorld(), block.getX(), block.getY() + yt, block.getZ() + zt);
							} else {
								lowRange = new Location(block.getWorld(), block.getX(), block.getY() - yt, block.getZ() - zt);
								highRange = new Location(block.getWorld(), block.getX() + xt, block.getY() + yt, block.getZ() + zt);
							}
							break;
						case "yt":
							if (times == -1) {
								lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY() - yt, block.getZ() - zt);
								highRange = new Location(block.getWorld(), block.getX() + xt, block.getY(), block.getZ() + zt);
							} else {
								lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY(), block.getZ() - zt);
								highRange = new Location(block.getWorld(), block.getX() + xt, block.getY() + yt, block.getZ() + zt);
							}
							break;
						case "zt":
							if (times == -1) {
								lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY() - yt, block.getZ() - zt);
								highRange = new Location(block.getWorld(), block.getX() + xt, block.getY() + yt, block.getZ());
							} else {
								lowRange = new Location(block.getWorld(), block.getX() - xt, block.getY() - yt, block.getZ());
								highRange = new Location(block.getWorld(), block.getX() + xt, block.getY() + yt, block.getZ() + zt);
							}
							break;
					}
				esPlayer.createModel(block, lowRange, highRange, item);
			}
		}
	}

//	private void wand(BlockPlaceEvent event) {
//		Player player = event.getPlayer();
//		if (!canRun(RegisterEnchantments.WAND, event) || isDisabled(player, RegisterEnchantments.WAND)) return;
//		if (AbilityUtils.getWandBlocks().contains(event.getBlock().getLocation())) return;
//		ItemStack item = player.getInventory().getItemInMainHand();
//		if (item != null) {
//			int xt = 0;
//			int yt = 0;
//			int zt = 0;
//			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.WAND)) {
//				ItemStack offhand = player.getInventory().getItemInOffHand();
//				if (!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) return;
//				float yaw = player.getLocation().getYaw() % 360;
//				float pitch = player.getLocation().getPitch();
//				while (yaw < 0)
//					yaw += 360;
//				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.WAND);
//				Block clickedBlock = event.getBlock();
//				if (pitch > 53 || pitch <= -53) {
//					xt = level;
//					zt = level;
//				} else {
//					yt = level;
//					if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) xt = level;
//					else
//						zt = level;
//				}
//				Location rangeOne = new Location(clickedBlock.getWorld(), clickedBlock.getX() - xt, clickedBlock.getY() - yt, clickedBlock.getZ() - zt);
//				Location rangeTwo = new Location(clickedBlock.getWorld(), clickedBlock.getX() + xt, clickedBlock.getY() + yt, clickedBlock.getZ() + zt);
//
//				if (LocationUtils.getIntersecting(rangeOne, rangeTwo, player.getLocation(), player.getEyeLocation())) return;
//				List<Location> blocks = new ArrayList<Location>();
//
//				for(int x = 0; x <= xt; x++)
//					for(int y = 0; y <= yt; y++)
//						for(int z = 0; z <= zt; z++) {
//							if (x == 0 && y == 0 && z == 0) continue;
//							addWandBlock(blocks, item, clickedBlock, x, y, z);
//							addWandBlock(blocks, item, clickedBlock, -x, y, z);
//							addWandBlock(blocks, item, clickedBlock, x, -y, z);
//							addWandBlock(blocks, item, clickedBlock, x, y, -z);
//							addWandBlock(blocks, item, clickedBlock, -x, -y, z);
//							addWandBlock(blocks, item, clickedBlock, -x, y, -z);
//							addWandBlock(blocks, item, clickedBlock, x, -y, -z);
//							addWandBlock(blocks, item, clickedBlock, -x, -y, -z);
//						}
//
//				WandEvent wand = new WandEvent(blocks, player, level);
//				Bukkit.getPluginManager().callEvent(wand);
//
//				if (!hasItem(player, offhand)) return;
//				if (!wand.isCancelled()) {
//					for(Location loc: wand.getBlocks()) {
//						Block block = loc.getBlock();
//						offhand = player.getInventory().getItemInOffHand();
//						if (!hasItem(player, offhand)) return;
//						AbilityUtils.addWandBlock(loc);
//						if (block.getType() == Material.TORCH) AdvancementUtils.awardCriteria(player, ESAdvancement.BREAKER_BREAKER, "torch");
//						Collection<ItemStack> drops = block.getDrops();
//						BlockData oldData = block.getBlockData();
//						Material oldType = block.getType();
//						block.setType(offhand.getType());
//						if (block.getBlockData() instanceof Directional) {
//							Directional directional = (Directional) block.getBlockData();
//							directional.setFacing(((Directional) clickedBlock).getFacing());
//							block.setBlockData(directional);
//						}
//						if (block.getBlockData() instanceof Orientable) {
//							Orientable orientable = (Orientable) block.getBlockData();
//							orientable.setAxis(((Orientable) clickedBlock).getAxis());
//							block.setBlockData(orientable);
//						}
//						if (block.getBlockData() instanceof Rotatable) {
//							Rotatable rotatable = (Rotatable) block.getBlockData();
//							rotatable.setRotation(((Rotatable) clickedBlock).getRotation());
//							block.setBlockData(rotatable);
//						}
//						BlockPlaceEvent newEvent = new BlockPlaceEvent(block, block.getState(), clickedBlock, item, player, true, EquipmentSlot.OFF_HAND);
//						Bukkit.getServer().getPluginManager().callEvent(newEvent);
//						if (!newEvent.isCancelled()) {
//							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
//							remove(player, offhand);
//							block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
//							for(ItemStack drop: drops)
//								ItemUtils.dropItem(drop, newEvent.getBlock().getLocation());
//						} else {
//							block.setType(oldType);
//							block.setBlockData(oldData);
//						}
//						AbilityUtils.removeWandBlock(loc);
//					}
//					DamageUtils.damageItem(player, item, 1, 2);
//					if (item == null || MatData.isAir(item.getType())) AdvancementUtils.awardCriteria(player, ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT, "break");
//				}
//			}
//		}
//	}

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

	private Collection<Location> addMultiBlock(Collection<Location> blocks, ItemStack tool, Material original, Block relative, int x, int y, int z) {
		Block block = relative.getRelative(x, y, z);
		if (BlockUtils.multiBlockBreakContains(block.getLocation())) return blocks;
		List<Material> pickBlocks = ItemBreakType.getDiamondPickaxeBlocks();
		if (!pickBlocks.contains(original) && pickBlocks.contains(block.getType())) return blocks;

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
