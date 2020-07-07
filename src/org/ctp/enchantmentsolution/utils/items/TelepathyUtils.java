package org.ctp.enchantmentsolution.utils.items;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.BlockSound;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.events.blocks.SmelteryEvent;
import org.ctp.enchantmentsolution.events.blocks.TelepathyEvent;
import org.ctp.enchantmentsolution.events.blocks.TelepathyEvent.TelepathyType;
import org.ctp.enchantmentsolution.events.modify.GoldDiggerEvent;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.*;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class TelepathyUtils {

	public static Collection<ItemStack> handleTelepathyBonus(BlockBreakEvent event, Player player, ItemStack item,
	Block block) {
		if (block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
		Collection<ItemStack> drops = block.getDrops(item);
		if (block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
			int num = ((Snow) block.getBlockData()).getLayers();
			drops.add(new ItemStack(Material.SNOWBALL, num));
		}
		drops = giveItems(player, item, block, drops);
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
			if (goldDigger != null) {
				int gdLevel = ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER);
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, gdLevel, event.getBlock(), goldDigger, GoldDiggerCrop.getExp(event.getBlock().getType(), gdLevel));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					event.setExpToDrop(event.getExpToDrop() + goldDiggerEvent.getExpToDrop());
					drops.add(goldDiggerEvent.getGoldItem());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock", goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				}
			}
		}
		return drops;
	}

	public static void handleTelepathy(BlockBreakEvent event, Player player, ItemStack item, Block block) {
		Collection<ItemStack> drops = block.getDrops(item);
		if (ESArrays.getShulkerBoxes().contains(block.getType())) {
			drops = ItemUtils.getSoulboundShulkerBox(player, block, drops);
			drops = giveItems(player, item, block, drops);

			callTelepathy(event, block, player, drops, TelepathyType.SHULKER_BOX, true);
			return;
		} else if (block.getState() instanceof Container) {
			Iterator<ItemStack> i = drops.iterator();
			Collection<ItemStack> newDrops = new ArrayList<ItemStack>();
			while (i.hasNext()) {
				ItemStack drop = i.next();
				newDrops.add(drop);
			}
			Container container = (Container) block.getState();
			int lowBound = 0;
			int highBound = 27;
			if (container.getInventory().getHolder() instanceof DoubleChest) {
				Chest chest = (Chest) block.getBlockData();
				DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
				Location one = doubleChest.getLocation().clone();
				Location two = one.clone();
				if (doubleChest.getLocation().getX() % 1 != 0) {
					one.add(-0.5, 0, 0);
					two.add(0.5, 0, 0);
				} else {
					one.add(0, 0, -0.5);
					two.add(0, 0, 0.5);
				}
				switch (chest.getFacing().name()) {
					case "WEST":
					case "SOUTH":
						if (!LocationUtils.isLocationSame(one, event.getBlock().getLocation(), true)) {
							lowBound = 27;
							highBound = 54;
						}
						break;
					case "EAST":
					case "NORTH":
						if (LocationUtils.isLocationSame(one, event.getBlock().getLocation(), true)) {
							lowBound = 27;
							highBound = 54;
						}
						break;
				}
				Inventory inv = container.getInventory();
				for(int j = lowBound; j < highBound; j++) {
					ItemStack drop = inv.getItem(j);
					if (drop != null) newDrops.add(drop);
				}
			} else if (container.getInventory().getHolder() instanceof Chest) {
				for(ItemStack drop: container.getInventory().getContents())
					if (drop != null) newDrops.add(drop);
			} else {
				highBound = container.getInventory().getSize();
				for(ItemStack drop: container.getInventory().getContents())
					if (drop != null) newDrops.add(drop);
			}
			TelepathyEvent telepathy = callTelepathy(event, block, player, newDrops, TelepathyType.CONTAINER, false);
			if (!telepathy.isCancelled()) {
				if (block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
				Inventory inv = container.getInventory();
				for(int j = lowBound; j < highBound; j++)
					inv.clear(j);
				damageItem(event);
				ItemUtils.giveItemsToPlayer(telepathy.getPlayer(), telepathy.getDrops(), telepathy.getPlayer().getLocation(), true);
			}
			return;
		}
		if (block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
			int num = ((Snow) block.getBlockData()).getLayers();
			drops.add(new ItemStack(Material.SNOWBALL, num));
		}
		drops = giveItems(player, item, block, drops);
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
			if (goldDigger != null) {
				int gdLevel = ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER);
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, gdLevel, event.getBlock(), goldDigger, GoldDiggerCrop.getExp(event.getBlock().getType(), gdLevel));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					event.setExpToDrop(event.getExpToDrop() + goldDiggerEvent.getExpToDrop());
					drops.add(goldDiggerEvent.getGoldItem());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock", goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				}
			}
		}
		callTelepathy(event, block, player, drops, TelepathyType.NORMAL, true);
	}

	private static Collection<ItemStack> giveItems(Player player, ItemStack item, Block block,
	Collection<ItemStack> drops) {
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) {
			SmelteryEvent smeltery = SmelteryUtils.handleSmelteryTelepathy(player, block, item);
			if (smeltery != null) {
				Bukkit.getPluginManager().callEvent(smeltery);

				if (!smeltery.isCancelled()) {
					ItemStack afterSmeltery = smeltery.getDrop();
					afterSmeltery.setType(smeltery.getChangeTo());
					Collection<ItemStack> finalDrops = new ArrayList<ItemStack>();
					finalDrops.add(afterSmeltery);
					if (smeltery.willFortune()) {
						drops = FortuneUtils.getFortuneItems(item, block, finalDrops);
						for (ItemStack i : finalDrops)
							if (i.getAmount() > 1 && i.getType() == Material.IRON_INGOT) AdvancementUtils.awardCriteria(player, ESAdvancement.IRONT_YOU_GLAD, "iron");
					}
					AbilityUtils.giveExperience(player, smeltery.getExp());
					return finalDrops;
				}
			}
		} else if (ItemUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = FortuneUtils.getFortuneItems(item, block, drops);
			return fortuneItems;
		} else if (ItemUtils.hasEnchantment(item, Enchantment.SILK_TOUCH) && SilkTouchUtils.getSilkTouchItem(block, item) != null) return Arrays.asList(SilkTouchUtils.getSilkTouchItem(block, item));
		return drops;
	}

	private static TelepathyEvent callTelepathy(BlockBreakEvent event, Block block, Player player,
	Collection<ItemStack> drops, TelepathyType type, boolean damageItems) {
		TelepathyEvent telepathy = new TelepathyEvent(block, player, drops, type);
		Bukkit.getPluginManager().callEvent(telepathy);

		if (!telepathy.isCancelled() && damageItems) {
			damageItem(event);
			if (BlockUtils.multiBlockBreakContains(block.getLocation())) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.FAST_AND_FURIOUS, "diamond_pickaxe");
				AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", 1);
			}
			if (telepathy.getType() == TelepathyType.SHULKER_BOX) AdvancementUtils.awardCriteria(player, ESAdvancement.HEY_IT_WORKS, "shulker_box");
			ItemUtils.giveItemsToPlayer(telepathy.getPlayer(), telepathy.getDrops(), telepathy.getPlayer().getLocation(), true);
		}
		return telepathy;
	}

	private static void damageItem(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		AbilityUtils.giveExperience(player, event.getExpToDrop());
		player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
		player.incrementStatistic(Statistic.USE_ITEM, item.getType());
		DamageUtils.damageItem(player, item);
		McMMOHandler.handleMcMMO(event, item);
		Block newBlock = event.getBlock();
		Location loc = newBlock.getLocation().clone().add(0.5, 0.5, 0.5);
		if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendBlockBreakAction(event);
		if (ConfigString.USE_PARTICLES.getBoolean()) loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, newBlock.getBlockData());
		if (ConfigString.PLAY_SOUND.getBoolean()) {
			BlockSound sound = BlockSound.getSound(newBlock.getType());
			loc.getWorld().playSound(loc, sound.getSound(), sound.getVolume(newBlock.getType()), sound.getPitch(newBlock.getType()));
		}
		newBlock.setType(Material.AIR);
	}
}
