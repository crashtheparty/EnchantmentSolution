package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.generate.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.inventory.minigame.MinigameItem;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class GenerateUtils {

	private static List<EnchantmentList> getLists(EnchantmentList[] lists) {
		List<EnchantmentList> list = new ArrayList<EnchantmentList>();
		if (lists != null) for(EnchantmentList l: lists)
			if (l != null) list.add(l);
		return list;
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType) {
		int minBookshelves = ConfigString.LOOT_BOOKSHELVES.getInt("loots.chests." + lootType + ".bookshelves");

		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, minBookshelves);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "ChestLoot");
			return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generatePiglinLoot(ItemStack item) {
		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(null, item, 0, EnchantmentLocation.PIGLIN_TRADES);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "PiglinTrades");
			return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType, EnchantmentLocation location) {
		int minBookshelves = ConfigString.LOOT_BOOKSHELVES.getInt("loots.chests." + lootType + ".bookshelves");

		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, minBookshelves, location);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, location == EnchantmentLocation.CHEST_LOOT ? "ChestLoot" : "PiglinTrades");
			return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generateMinigameLoot(Player player, ItemStack item, Block block) {
		MinigameEnchantments enchantments = MinigameEnchantments.generateMinigameLoot(player, item, block);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "MinigameLoot");
			return item;
		}

		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static MinigameEnchantments generateMinigameEnchants(Player player, ItemStack item, Block block) {
		return MinigameEnchantments.generateMinigameLoot(player, item, block);
	}

	public static ItemStack generateMinigameLoot(Player player, ItemStack enchant, Block block, MinigameItem item) {
		MinigameEnchantments enchantments = MinigameEnchantments.generateMinigameLoot(player, enchant, block, item);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists, item.getMinLevels(), item.getMaxLevels());
		if (levels == null || levels.size() == 0) {
			warningMessages(enchant, "MinigameLoot");
			return enchant;
		}

		if (!item.getType().isMultiple()) while (levels.size() > 1)
			levels.remove(levels.size() - 1);

		return EnchantmentUtils.addEnchantmentsToItem(enchant, levels);
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item) {
		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, 0);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		int random = (int) (Math.random() * lists.size());

		return lists.get(random).getEnchantments();
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item, EnchantmentLocation location) {
		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, 0, location);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		int random = (int) (Math.random() * lists.size());

		return lists.get(random).getEnchantments();
	}

	public static ItemStack generateFishingLoot(Player player, ItemStack item, int minBookshelves) {
		FishingEnchantments enchantments = FishingEnchantments.getFishingEnchantments(player, item, minBookshelves);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "FishingLoot");
			return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generateMobSpawnLoot(ItemStack item) {
		MobLootEnchantments enchantments = MobLootEnchantments.generateMobLoot(item);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		List<EnchantmentLevel> levels = getEnchantments(lists);
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "MobLoot");
			return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	private static List<EnchantmentLevel> getEnchantments(List<EnchantmentList> lists) {
		int random = (int) (Math.random() * lists.size());
		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		while (lists.size() > 1 && (levels == null || levels.size() == 0)) {
			lists.remove(random);
			random = (int) (Math.random() * lists.size());

			levels = lists.get(random).getEnchantments();
		}
		return levels;
	}

	private static List<EnchantmentLevel> getEnchantments(List<EnchantmentList> lists, int min, int max) {
		int random = (int) (Math.random() * lists.size());
		if (random < min && !(min < 0)) random = min;
		if (random > max && !(max > lists.size())) random = max;
		if (max >= lists.size()) random = lists.size() - 1;
		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		while (lists.size() > 1 && (levels == null || levels.size() == 0)) {
			lists.remove(random);
			random = (int) (Math.random() * lists.size());

			levels = lists.get(random).getEnchantments();
		}
		return levels;
	}

	private static void warningMessages(ItemStack item, String type) {
		Chatable.get().sendWarning("Item couldn't find EnchantmentSolution enchantments. Keeping default enchantments on the item.");
		Chatable.get().sendWarning("This occurs when items do not have valid enchantments based on the item being spawned (as well as the player spawning them, if applicable). THIS IS ONLY A BUG IF THE ITEM HAS VALID ENCHANTMENTS SPECIFIED FOR IT.");
		Chatable.get().sendWarning("Item: " + item.toString() + " Type: " + type);
	}
}
