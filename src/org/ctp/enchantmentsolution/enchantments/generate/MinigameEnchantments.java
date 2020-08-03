package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.inventory.minigame.MinigameItem;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class MinigameEnchantments extends LootEnchantments {

	public MinigameEnchantments(Player player, ItemStack item, int books, EnchantmentLocation location) {
		super(player, item, books, location);
	}

	public static MinigameEnchantments generateMinigameLoot(Player player, ItemStack item, Block block) {
		int books = 0;
		if (ConfigString.MINIGAME_FAST_RANDOM_BOOKSHELVES.getBoolean()) {
			int maxBooks = 16;
			if (ConfigString.LEVEL_FIFTY.getBoolean()) maxBooks = 24;

			double rand = (Math.random() + Math.random()) / 2;

			int random = (int) (rand * maxBooks);
			if (random >= maxBooks) random = maxBooks - 1;
			books = random;
		} else if (block != null) books = EnchantmentUtils.getBookshelves(block.getLocation());
		return new MinigameEnchantments(player, item, books, EnchantmentLocation.TABLE);
	}

	public static MinigameEnchantments generateMinigameLoot(Player player, ItemStack item, Block block, MinigameItem minigameItem) {
		int books = 0;
		if (minigameItem.getType().fromLocation() && block != null) books = EnchantmentUtils.getBookshelves(block.getLocation());
		else {
			double rand = (Math.random() + Math.random()) / 2;
			int min = minigameItem.getMinBooks();
			int max = minigameItem.getMaxBooks();

			int random = (int) (rand * (max - min + 1) + min);
			if (random >= max) random = max - 1;
			books = random;
		}
		return new MinigameEnchantments(player, item, books, EnchantmentLocation.TABLE);
	}

}
