package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MobLootEnchantments extends LootEnchantments {

	private MobLootEnchantments(Player player, ItemStack item, int bookshelves) {
		super(player, item, bookshelves, EnchantmentLocation.MOB_LOOT);
	}

	public static MobLootEnchantments generateMobLoot(ItemStack item) {
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int minBookshelves = ConfigString.LOOT_BOOKSHELVES.getInt("mobs.bookshelves");
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new MobLootEnchantments(null, item, books);
	}

}
