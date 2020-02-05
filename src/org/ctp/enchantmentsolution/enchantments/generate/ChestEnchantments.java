package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ChestEnchantments extends LootEnchantments {

	private ChestEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure) {
		super(player, item, bookshelves, treasure);
	}

	public static ChestEnchantments getChestEnchantment(Player player, ItemStack item, int minBookshelves,
	boolean treasure) {
		if (item == null) return null;
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new ChestEnchantments(player, item, books, treasure);
	}

}
