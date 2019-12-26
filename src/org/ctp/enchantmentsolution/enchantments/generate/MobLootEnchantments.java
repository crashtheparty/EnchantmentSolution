package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MobLootEnchantments extends LootEnchantments {

	private MobLootEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure) {
		super(player, item, bookshelves, treasure);
	}

	public static MobLootEnchantments generateMobLoot(ItemStack item) {
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int minBookshelves = ConfigString.LOOT_BOOKSHELVES.getInt("mobs.bookshelves");
		boolean treasure = ConfigString.LOOT_TREASURE.getBoolean("mobs.treasure");
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new MobLootEnchantments(null, item, books, treasure);
	}

}
