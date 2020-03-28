package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.generate.ChestEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.FishingEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.MobLootEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class GenerateUtils {

	private static List<EnchantmentList> getLists(EnchantmentList[] lists) {
		List<EnchantmentList> list = new ArrayList<EnchantmentList>();
		for(EnchantmentList l: lists)
			if (l != null) list.add(l);
		return list;
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType) {
		int minBookshelves = ConfigString.LOOT_BOOKSHELVES.getInt("loots.chests." + lootType + ".bookshelves");

		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, minBookshelves);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		int random = (int) (Math.random() * lists.size());

		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		return ItemUtils.addEnchantmentsToItem(item, levels);
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
		int random = (int) (Math.random() * lists.size());

		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		item = ItemUtils.removeAllEnchantments(item, true);

		return ItemUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generateMobSpawnLoot(ItemStack item) {
		MobLootEnchantments enchantments = MobLootEnchantments.generateMobLoot(item);

		List<EnchantmentList> lists = getLists(enchantments.getList());
		int random = (int) (Math.random() * lists.size());

		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		item = ItemUtils.removeAllEnchantments(item, true);

		return ItemUtils.addEnchantmentsToItem(item, levels);
	}
}
