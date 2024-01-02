package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.inventory.minigame.MinigameItem;

public class MinigameEnchantments extends LootEnchantments {

	public MinigameEnchantments(Player player, ItemStack item, EnchantmentLocation location, Loots loot, Location loc) {
		super(player, item, location, loot, loc);
	}

	public static MinigameEnchantments generateMinigameLoot(Player player, ItemStack item, Location loc, String type, Loots defaultLoot) {
		if (item == null) return null;
		Loots loot = Loots.getLoot(type);
		if (loot == null) loot = defaultLoot;

		return new MinigameEnchantments(player, item, EnchantmentLocation.TABLE, loot, loc);
	}

	public static MinigameEnchantments generateMinigameLoot(Player player, ItemStack item, Location loc, String type, Loots defaultLoot,
	MinigameItem minigameItem) {
		if (item == null) return null;
		Loots loot = Loots.getLoot(type);
		if (loot == null) loot = defaultLoot;

		return new MinigameEnchantments(player, item, EnchantmentLocation.TABLE, loot, loc);
	}

}
