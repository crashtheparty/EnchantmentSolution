package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.events.blocks.GoldDiggerEvent;
import org.ctp.enchantmentsolution.events.blocks.SmelteryEvent;
import org.ctp.enchantmentsolution.events.blocks.TelepathyEvent;
import org.ctp.enchantmentsolution.events.blocks.TelepathyEvent.TelepathyType;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abillityhelpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;

public class TelepathyUtils {

	public static Collection<ItemStack> handleTelepathyBonus(BlockBreakEvent event, Player player, ItemStack item, Block block) {
		if(block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
		}
		Collection<ItemStack> drops = block.getDrops(item);
		if(block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
			int num = ((Snow) block.getBlockData()).getLayers();
			drops.add(new ItemStack(Material.SNOWBALL, num));
		}
		drops = giveItems(player, item, block, drops);
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
			if (goldDigger != null) {
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, event.getBlock(), goldDigger,
				GoldDiggerCrop.getExp(event.getBlock().getType(),
				ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER)));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					event.setExpToDrop(event.getExpToDrop() + goldDiggerEvent.getExpToDrop());
					drops.add(goldDiggerEvent.getGoldItem());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock",
					goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				}
			}
		}
		return drops;
	}

	public static void handleTelepathy(BlockBreakEvent event, Player player, ItemStack item, Block block) {
		if(block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
		}
		Collection<ItemStack> drops = block.getDrops(item);
		if (ESArrays.getShulkerBoxes().contains(block.getType())) {
			drops = ItemUtils.getSoulboundShulkerBox(player, block, drops);
			drops = giveItems(player, item, block, drops);

			callTelepathy(event, block, player, drops, TelepathyType.SHULKER_BOX, true);
			return;
		} else if (block.getState() instanceof Container) {
			Iterator<ItemStack> i = drops.iterator();
			Collection<ItemStack> newDrops = new ArrayList<ItemStack>();
			while(i.hasNext()) {
				ItemStack drop = i.next();
				newDrops.add(drop);
			}
			Container container = (Container) block.getState();
			if(container.getInventory().getHolder() instanceof DoubleChest) {
				DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
				if (doubleChest.getLeftSide().getInventory().getLocation().equals(container.getLocation())) {
					Inventory inv = doubleChest.getLeftSide().getInventory();
					for(int j = 0; j < 27; j++) {
						ItemStack drop = inv.getItem(j);
						if(drop != null) {
							newDrops.add(drop);
						}
					}
				} else {
					Inventory inv = doubleChest.getRightSide().getInventory();
					for(int j = 27; j < 54; j++) {
						ItemStack drop = inv.getItem(j);
						if(drop != null) {
							newDrops.add(drop);
						}
					}
				}
			} else {
				for(ItemStack drop : container.getInventory().getContents()) {
					if(drop != null) {
						newDrops.add(drop);
					}
				}
			}
			TelepathyEvent telepathy = callTelepathy(event, block, player, newDrops, TelepathyType.CONTAINER, false);
			if(!telepathy.isCancelled()) {
				if(container.getInventory().getHolder() instanceof DoubleChest) {
					DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
					if (doubleChest.getLeftSide().getInventory().getLocation().equals(container.getLocation())) {
						Inventory inv = doubleChest.getLeftSide().getInventory();
						inv.clear();
					} else {
						Inventory inv = doubleChest.getRightSide().getInventory();
						inv.clear();
					}
				} else {
					container.getInventory().clear();
				}
				damageItem(event);
				ItemUtils.giveItemsToPlayer(telepathy.getPlayer(), telepathy.getDrops(), telepathy.getPlayer().getLocation(), true);
			}
			return;
		}
		if(block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
			int num = ((Snow) block.getBlockData()).getLayers();
			drops.add(new ItemStack(Material.SNOWBALL, num));
		}
		drops = giveItems(player, item, block, drops);
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
			ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
			if (goldDigger != null) {
				GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, event.getBlock(), goldDigger,
				GoldDiggerCrop.getExp(event.getBlock().getType(),
				ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER)));
				Bukkit.getPluginManager().callEvent(goldDiggerEvent);

				if (!goldDiggerEvent.isCancelled()) {
					event.setExpToDrop(event.getExpToDrop() + goldDiggerEvent.getExpToDrop());
					drops.add(goldDiggerEvent.getGoldItem());
					AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock",
					goldDigger.getAmount());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				}
			}
		}
		callTelepathy(event, block, player, drops, TelepathyType.NORMAL, true);
	}

	private static Collection<ItemStack> giveItems(Player player, ItemStack item, Block block, Collection<ItemStack> drops) {
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) {
			SmelteryEvent smeltery = SmelteryUtils.handleSmelteryTelepathy(player, block, item);
			if(smeltery != null) {
				Bukkit.getPluginManager().callEvent(smeltery);

				if(!smeltery.isCancelled()) {
					AbilityUtils.giveExperience(player, smeltery.getExp());
					if (smeltery.getDrop() != null) {
						return Arrays.asList(smeltery.getDrop());
					}
				}
			}
		} else if (ItemUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = FortuneUtils.getFortuneItems(item, block,
			drops);
			return fortuneItems;
		} else if (ItemUtils.hasEnchantment(item, Enchantment.SILK_TOUCH)
		&& SilkTouchUtils.getSilkTouchItem(block, item) != null) {
			return Arrays.asList(SilkTouchUtils.getSilkTouchItem(block, item));
		}
		return drops;
	}

	private static TelepathyEvent callTelepathy(BlockBreakEvent event, Block block, Player player, Collection<ItemStack> drops, TelepathyType type, boolean damageItems) {
		TelepathyEvent telepathy = new TelepathyEvent(block, player, drops, type);
		Bukkit.getPluginManager().callEvent(telepathy);

		if(!telepathy.isCancelled() && damageItems) {
			damageItem(event);
			if(AbilityUtils.getHeightWidthBlocks().contains(block)){
				AdvancementUtils.awardCriteria(player, ESAdvancement.FAST_AND_FURIOUS, "diamond_pickaxe");
				AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", 1);
			}
			if(telepathy.getType() == TelepathyType.SHULKER_BOX) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.HEY_IT_WORKS, "shulker_box");
			}
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
		if(EnchantmentSolution.getPlugin().isJobsEnabled()){
			JobsUtils.sendBlockBreakAction(event);
		}
		event.getBlock().setType(Material.AIR);
	}
}
