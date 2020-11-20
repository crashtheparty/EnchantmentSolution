package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSerialization;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.ItemPlaceType;
import org.ctp.enchantmentsolution.events.blocks.*;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent.ExpShareType;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.listeners.VeinMinerListener;
import org.ctp.enchantmentsolution.mcmmo.McMMOAbility;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.*;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils.GaiaTrees;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.items.SmelteryUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class BlockListener extends Enchantmentable {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		runMethod(this, "expShare", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockDropItem(BlockDropItemEvent event) {
		if (event instanceof BlockDropItemAddEvent) return;
		runMethod(this, "greenThumb", event, BlockDropItemEvent.class);
		runMethod(this, "goldDigger", event, BlockDropItemEvent.class);
		runMethod(this, "smeltery", event, BlockDropItemEvent.class);
		runMethod(this, "telepathy", event, BlockDropItemEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakHighest(BlockBreakEvent event) {
		runMethod(this, "gaia", event, BlockBreakEvent.class);
		runMethod(this, "greenThumb", event, BlockBreakEvent.class);
		runMethod(this, "heightWidthDepth", event, BlockBreakEvent.class);
		runMethod(this, "curseOfLag", event, BlockBreakEvent.class);
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
		if (player != null && !isDisabled(player, RegisterEnchantments.CURSE_OF_LAG)) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
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
		if (EnchantmentUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE) && !isDisabled(player, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getExpToDrop();
			if (exp > 0) {
				int level = EnchantmentUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

				ExpShareEvent experienceEvent = new ExpShareEvent(player, level, ExpShareType.BLOCK, exp, AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) event.setExpToDrop(experienceEvent.getNewExp());
			}
		}
	}

	private void gaia(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GAIA, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.GAIA)) return;
		if (BlockUtils.multiBlockBreakContains(event.getBlock().getLocation())) return;
		if (!EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled") && McMMOAbility.getIgnored() != null && McMMOAbility.getIgnored().contains(player)) return;
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		GaiaTrees tree = GaiaTrees.getTree(event.getBlock().getType());
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (item != null && esPlayer.correctGaiaItem(item) && esPlayer.correctGaiaTree(tree) && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GAIA) && tree != null) {
			List<Location> logs = new ArrayList<Location>();
			int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.GAIA);
			int maxBlocks = 50 + level * level * 50;
			logs.add(event.getBlock().getLocation());
			for(int i = 0; i < logs.size(); i++) {
				getLikeBlocks(logs, tree.getLog().getMaterial(), logs.get(i));
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

	private void greenThumb(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GREEN_THUMB, event)) return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		Block block = event.getBlock();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GREEN_THUMB) && block.getBlockData() instanceof Ageable) {
			Ageable age = (Ageable) block.getBlockData();
			Material mat = block.getType();
			if (Crop.hasBlock(mat) && age.getAge() == 0) event.setCancelled(true);
		}
	}

	private void getLikeBlocks(List<Location> logs, Material log, Location loc) {
		for(int x = -2; x <= 2; x++)
			for(int y = -1; y <= 1; y++)
				for(int z = -2; z <= 2; z++) {
					Block b = loc.getBlock().getRelative(x, y, z);
					Location l = b.getLocation();
					if (!logs.contains(l) && b.getType() == log) logs.add(l);
				}
	}

	private void greenThumb(BlockDropItemEvent event) {
		if (!canRun(RegisterEnchantments.GREEN_THUMB, event)) return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || isDisabled(player, RegisterEnchantments.GREEN_THUMB)) return;
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		ItemStack item = player.getInventory().getItemInMainHand();
		Block block = event.getBlock();
		BlockData data = event.getBlockState().getBlockData();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GREEN_THUMB) && data instanceof Ageable) {
			Ageable age = (Ageable) data;
			Material mat = data.getMaterial();
			if (!Crop.hasBlock(mat) || Crop.hasBlock(mat) && age.getAge() != age.getMaximumAge()) return;
			Crop c = Crop.getCrop(mat);
			Item dropItem = null;
			ItemStack dropStack = null;
			List<ItemStack> overrideItems = new ArrayList<ItemStack>();
			List<ItemStack> items = new ArrayList<ItemStack>();
			for(Item i: event.getItems()) {
				if (i.getItemStack().getType() == c.getSeed().getMaterial()) {
					dropItem = i;
					dropStack = i.getItemStack();
					ItemStack clone = dropStack.clone();
					if (clone.getAmount() <= 1) {
						clone.setType(Material.AIR);
						clone.setAmount(0);
					} else
						clone.setAmount(clone.getAmount() - 1);
					items.add(clone);
				} else
					items.add(i.getItemStack());
				overrideItems.add(i.getItemStack());
			}
			if (dropStack == null) for(ItemStack i: esPlayer.getInventoryItems())
				if (i != null && i.getType() == c.getSeed().getMaterial()) {
					dropStack = i;
					break;
				}
			GreenThumbEvent greenThumb = new GreenThumbEvent(block, data, player, items, overrideItems, dropStack);

			Bukkit.getPluginManager().callEvent(greenThumb);

			if (!greenThumb.isCancelled()) {
				dropStack.setAmount(dropStack.getAmount() - 1);
				if (dropStack.getAmount() == 0) dropStack.setType(Material.AIR);
				if (dropItem != null) for(Item i: event.getItems())
					if (i.getItemStack().getType() == c.getSeed().getMaterial()) {
						i.setItemStack(dropStack);
						break;
					}
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					block.setType(c.getBlock().getMaterial());
					Ageable newAge = (Ageable) block.getBlockData();
					newAge.setAge(0);
					AdvancementUtils.awardCriteria(player, ESAdvancement.THUMBS_UP, "replant");
				}, 0l);
			}
		}
	}

	private void goldDigger(BlockDropItemEvent event) {
		if (!canRun(RegisterEnchantments.GOLD_DIGGER, event)) return;
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.GOLD_DIGGER)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		BlockData data = event.getBlockState().getBlockData();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, data);
			if (goldDigger != null) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER);
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, level, event.getBlock(), data, Arrays.asList(goldDigger), GoldDiggerCrop.getExp(data.getMaterial(), level));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					BlockDropItemAddEvent esEvent = new BlockDropItemAddEvent(event.getBlock(), event.getBlockState(), player, ItemUtils.itemStacksToItems(goldDiggerEvent.getItems(), event.getBlock().getLocation()));
					Bukkit.getPluginManager().callEvent(esEvent);
					if (esEvent.isCancelled()) for(Item i: esEvent.getItems())
						i.remove();

					AbilityUtils.dropExperience(goldDiggerEvent.getBlock().getLocation(), goldDiggerEvent.getExpToDrop());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock", goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					DamageUtils.damageItem(player, item);
				}
			}
		}
	}

	private void smeltery(BlockDropItemEvent event) {
		if (!canRun(RegisterEnchantments.SMELTERY, event)) return;
		BlockData data = event.getBlockState().getBlockData();
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.SMELTERY)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) for(Item i: event.getItems()) {
			ItemStack from = i.getItemStack();
			SmelteryMaterial smeltery = SmelteryUtils.getSmelteryItem(data, from, item);
			if (smeltery != null) {
				ItemStack smelted = smeltery.getSmelted();
				int experience = 0;
				boolean fortune = false;
				MatData mat = new MatData(data.getMaterial().name());
				if (mat.hasMaterial() && (mat.getMaterial() == Material.IRON_ORE || mat.getMaterial() == Material.GOLD_ORE)) {
					experience = (int) (Math.random() * 3) + 1;
					fortune = true;
				} else if (mat.hasMaterial() && mat.getMaterialName().equals("ANCIENT_DEBRIS")) {
					experience = (int) (Math.random() * 6) + 2;
					fortune = true;
				} else if (mat.hasMaterial() && (mat.getMaterialName().equals("NETHER_GOLD_ORE") || mat.getMaterialName().equals("GILDED_BLACKSTONE"))) {
					experience = (int) (Math.random() * 2);
					fortune = mat.getMaterialName().equals("NETHER_GOLD_ORE");
				}
				experience = AbilityUtils.setExp(experience, EnchantmentUtils.getLevel(item, RegisterEnchantments.EXP_SHARE));
				SmelteryEvent smelteryEvent = new SmelteryEvent(event.getBlock(), data, player, smelted, smeltery.getToMaterial(), experience, fortune);
				Bukkit.getPluginManager().callEvent(smelteryEvent);

				if (!smelteryEvent.isCancelled()) {
					AbilityUtils.dropExperience(smelteryEvent.getBlock().getLocation(), smelteryEvent.getExp());
					if (from.getType() == smeltery.getFromMaterial()) {
						from.setType(smeltery.getToMaterial());
						if (smelteryEvent.willFortune()) from.setAmount(SmelteryUtils.getFortuneForSmeltery(smelteryEvent.getDrop(), item, smeltery.getFromMaterial()));
						i.setItemStack(from);
					}
					McMMOHandler.handleBlockDrops(event, item, RegisterEnchantments.SMELTERY);
				}
			}
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
		if (item != null && esPlayer.correctHWDItem(item) && EnchantmentUtils.hasOneEnchantment(item, RegisterEnchantments.DEPTH_PLUS_PLUS, RegisterEnchantments.HEIGHT_PLUS_PLUS, RegisterEnchantments.WIDTH_PLUS_PLUS)) {
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
				List<Location> blocks = new ArrayList<Location>();
				Block block = event.getBlock();
				item = player.getInventory().getItemInMainHand();
				if (item == null || MatData.isAir(item.getType())) return;
				for(int x = 0; x <= xt; x++)
					for(int y = 0; y <= yt; y++)
						for(int z = 0; z <= zt; z++) {
							if (x == 0 && y == 0 && z == 0) continue;
							if (which.equals("")) {
								addMultiBlock(blocks, item, original, block, x, y, z);
								addMultiBlock(blocks, item, original, block, -x, y, z);
								addMultiBlock(blocks, item, original, block, x, -y, z);
								addMultiBlock(blocks, item, original, block, x, y, -z);
								addMultiBlock(blocks, item, original, block, -x, -y, z);
								addMultiBlock(blocks, item, original, block, -x, y, -z);
								addMultiBlock(blocks, item, original, block, x, -y, -z);
								addMultiBlock(blocks, item, original, block, -x, -y, -z);
							} else
								switch (which) {
									case "xt":
										int xc = x * times;
										addMultiBlock(blocks, item, original, block, xc, y, z);
										addMultiBlock(blocks, item, original, block, xc, -y, z);
										addMultiBlock(blocks, item, original, block, xc, y, -z);
										addMultiBlock(blocks, item, original, block, xc, -y, -z);
										break;
									case "yt":
										int yc = y * times;
										addMultiBlock(blocks, item, original, block, x, yc, z);
										addMultiBlock(blocks, item, original, block, -x, yc, z);
										addMultiBlock(blocks, item, original, block, x, yc, -z);
										addMultiBlock(blocks, item, original, block, -x, yc, -z);
										break;
									case "zt":
										int zc = z * times;
										addMultiBlock(blocks, item, original, block, x, y, zc);
										addMultiBlock(blocks, item, original, block, -x, y, zc);
										addMultiBlock(blocks, item, original, block, x, -y, zc);
										addMultiBlock(blocks, item, original, block, -x, -y, zc);
										break;
								}
						}

				HeightWidthDepthEvent hwd = new HeightWidthDepthEvent(blocks, block, player, heightPlusPlus, widthPlusPlus, depthPlusPlus);
				Bukkit.getPluginManager().callEvent(hwd);

				if (!hwd.isCancelled()) {
					for(Location b: hwd.getBlocks())
						BlockUtils.addMultiBlockBreak(b, RegisterEnchantments.HEIGHT_PLUS_PLUS);
					esPlayer.addToHWDController(item, hwd.getBlock(), hwd.getBlocks());
				}
			}
		}
	}

	private void wand(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!canRun(RegisterEnchantments.WAND, event) || isDisabled(player, RegisterEnchantments.WAND)) return;
		if (AbilityUtils.getWandBlocks().contains(event.getBlock().getLocation())) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.WAND)) {
				ItemStack offhand = player.getInventory().getItemInOffHand();
				if (!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) return;
				float yaw = player.getLocation().getYaw() % 360;
				float pitch = player.getLocation().getPitch();
				while (yaw < 0)
					yaw += 360;
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.WAND);
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
				List<Location> blocks = new ArrayList<Location>();

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
			if (isDisabled(player, RegisterEnchantments.LIGHT_WEIGHT)) return;
			if (event.getBlock().getType() == Material.FARMLAND && event.getTo() == Material.DIRT) {
				ItemStack boots = player.getInventory().getBoots();
				if (EnchantmentUtils.hasEnchantment(boots, RegisterEnchantments.LIGHT_WEIGHT)) {
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

	private void telepathy(BlockDropItemEvent event) {
		if (!canRun(RegisterEnchantments.TELEPATHY, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.TELEPATHY)) return;
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
			List<ItemStack> itemStacks = new ArrayList<ItemStack>();
			for(Item i: event.getItems())
				itemStacks.add(i.getItemStack());
			TelepathyBlockEvent telepathy = new TelepathyBlockEvent(block, event.getBlockState().getBlockData(), player, itemStacks);
			Bukkit.getPluginManager().callEvent(telepathy);

			if (!telepathy.isCancelled()) {
				ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
				esPlayer.addTelepathyItems(telepathy.getItems());
				McMMOHandler.handleBlockDrops(event, item, RegisterEnchantments.TELEPATHY);
				event.setCancelled(true);
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

	private Collection<Location> addMultiBlock(Collection<Location> blocks, ItemStack tool, Material original, Block relative, int x, int y, int z) {
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
