package org.ctp.enchantmentsolution.utils;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.BlockSound;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.events.blocks.BlockBreakMultiEvent;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class BlockUtils {

	private static Map<Enchantment, List<Location>> MULTI_BLOCK_BREAK = new HashMap<Enchantment, List<Location>>();

	public static boolean multiBlockBreakContains(Location loc) {
		for(Enchantment enchantment: MULTI_BLOCK_BREAK.keySet())
			if (MULTI_BLOCK_BREAK.get(enchantment).contains(loc)) return true;
		return false;
	}

	public static boolean multiBlockBreakContains(Location loc, Enchantment enchantment) {
		if (!MULTI_BLOCK_BREAK.containsKey(enchantment)) return false;
		return MULTI_BLOCK_BREAK.get(enchantment).contains(loc);
	}

	public static void addMultiBlockBreak(Location loc, Enchantment enchantment) {
		List<Location> locs = MULTI_BLOCK_BREAK.get(enchantment);
		if (locs == null) locs = new ArrayList<Location>();
		locs.add(loc);
		MULTI_BLOCK_BREAK.put(enchantment, locs);
	}

	public static void removeMultiBlockBreak(Location loc, Enchantment enchantment) {
		List<Location> locs = MULTI_BLOCK_BREAK.get(enchantment);
		if (locs == null) locs = new ArrayList<Location>();
		locs.remove(loc);
		MULTI_BLOCK_BREAK.put(enchantment, locs);
	}

	public static boolean isNextTo(Block b1, Block b2) {
		int x = Math.abs(b1.getX() - b2.getX());
		int y = Math.abs(b1.getY() - b2.getY());
		int z = Math.abs(b1.getZ() - b2.getZ());
		return x <= 1 && y <= 1 && z <= 1;
	}

	public static List<Location> getNextTo(Location loc) {
		return getNextTo(loc, 1);
	}

	public static List<Location> getNextTo(Location loc, int i) {
		List<Location> locs = new ArrayList<Location>();
		for(int x = -i; x <= i; x++)
			for(int y = -i; y <= i; y++)
				for(int z = -i; z <= i; z++)
					locs.add(loc.getBlock().getRelative(x, y, z).getLocation());
		return locs;
	}

	public static boolean multiBreakBlock(Player player, ItemStack item, Location b, Enchantment enchantment) {
		return multiBreakBlock(player, item, b, enchantment, 1, 1);
	}

	public static boolean multiBreakBlock(Player player, ItemStack item, Location b, Enchantment enchantment, int damage, float dropChance) {
		BlockBreakEvent newEvent = new BlockBreakMultiEvent(b.getBlock(), player);
		int exp = 0;
		if (item != null && !EnchantmentUtils.hasEnchantment(item, Enchantment.SILK_TOUCH)) switch (newEvent.getBlock().getType().name()) {
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
		newEvent.setExpToDrop(exp);
		Bukkit.getServer().getPluginManager().callEvent(newEvent);
		if (item != null && !MatData.isAir(item.getType()) && !MatData.isAir(newEvent.getBlock().getType()) && !newEvent.isCancelled()) {
			Block newBlock = newEvent.getBlock();
			if (RegisterEnchantments.getHWD().contains(enchantment)) AdvancementUtils.awardCriteria(player, ESAdvancement.FAST_AND_FURIOUS, "diamond_pickaxe");
			if (newBlock.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
				int num = ((Snow) newBlock.getBlockData()).getLayers();
				if (dropChance > Math.random()) ItemUtils.dropItem(new ItemStack(Material.SNOWBALL, num), newBlock.getLocation());
			}
			player.incrementStatistic(Statistic.MINE_BLOCK, newBlock.getType());
			player.incrementStatistic(Statistic.USE_ITEM, item.getType());
			Location loc = newBlock.getLocation().clone().add(0.5, 0.5, 0.5);
			if (ConfigString.USE_PARTICLES.getBoolean()) loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, newBlock.getBlockData());
			if (ConfigString.PLAY_SOUND.getBoolean()) {
				BlockSound sound = BlockSound.getSound(newBlock.getType());
				loc.getWorld().playSound(loc, sound.getSound(), sound.getVolume(newBlock.getType()), sound.getPitch(newBlock.getType()));
			}
			Collection<ItemStack> drops = newBlock.getDrops(item, player);
			List<Item> items = new ArrayList<Item>();
			for(ItemStack drop: drops) {
				if (drop == null || MatData.isAir(drop.getType())) continue;
				items.add(ItemUtils.spawnItem(drop, loc));
			}
			BlockState blockState = newBlock.getState();
			newBlock.setType(Material.AIR);
			if (items.size() > 0) {
				BlockDropItemEvent blockDrop = new BlockDropItemEvent(newBlock, blockState, player, items);
				Bukkit.getServer().getPluginManager().callEvent(blockDrop);

				if (blockDrop.isCancelled()) for(Item i: blockDrop.getItems())
					i.remove();
			}
			AbilityUtils.dropExperience(loc, newEvent.getExpToDrop());
			DamageUtils.damageItem(player, item, damage);
			EnchantmentSolution.getESPlayer(player).breakBlock();
			BlockUtils.removeMultiBlockBreak(b, enchantment);
			return true;
		} else if (!newEvent.isCancelled() && item == null || MatData.isAir(item.getType())) {
			Block newBlock = newEvent.getBlock();
			Location loc = newBlock.getLocation().clone().add(0.5, 0.5, 0.5);
			if (ConfigString.USE_PARTICLES.getBoolean()) loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, newBlock.getBlockData());
			if (ConfigString.PLAY_SOUND.getBoolean()) {
				BlockSound sound = BlockSound.getSound(newBlock.getType());
				loc.getWorld().playSound(loc, sound.getSound(), sound.getVolume(newBlock.getType()), sound.getPitch(newBlock.getType()));
			}
			Collection<ItemStack> drops = newBlock.getDrops(item, player);
			List<Item> items = new ArrayList<Item>();
			for(ItemStack drop: drops)
				items.add(ItemUtils.spawnItem(drop, loc));
			BlockState blockState = newBlock.getState();
			newBlock.setType(Material.AIR);
			BlockDropItemEvent blockDrop = new BlockDropItemEvent(newBlock, blockState, player, items);
			Bukkit.getServer().getPluginManager().callEvent(blockDrop);

			if (blockDrop.isCancelled()) for(Item i: blockDrop.getItems())
				i.remove();
			BlockUtils.removeMultiBlockBreak(b, enchantment);
			return true;
		}
		BlockUtils.removeMultiBlockBreak(b, enchantment);
		return false;
	}

	public static void rerun() {

	}
}
