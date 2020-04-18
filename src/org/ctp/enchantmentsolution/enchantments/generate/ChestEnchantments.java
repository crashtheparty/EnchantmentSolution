package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ChestEnchantments extends LootEnchantments {

	private ChestEnchantments(Player player, ItemStack item, int bookshelves) {
		super(player, item, bookshelves, EnchantmentLocation.CHEST_LOOT);
	}

	private ChestEnchantments(Player player, ItemStack item, int bookshelves, EnchantmentLocation location) {
		super(player, item, bookshelves, location);
	}

	public static ChestEnchantments getChestEnchantment(Player player, ItemStack item, int minBookshelves) {
		if (item == null) return null;
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new ChestEnchantments(player, item, books);
	}

	public static ChestEnchantments getChestEnchantment(Player player, ItemStack item, int minBookshelves, EnchantmentLocation location) {
		if (item == null) return null;
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new ChestEnchantments(player, item, books, location);
	}

}
