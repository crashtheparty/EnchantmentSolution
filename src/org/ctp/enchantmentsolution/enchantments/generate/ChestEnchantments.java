package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;

public class ChestEnchantments extends LootEnchantments {

	private ChestEnchantments(Player player, ItemStack item, Loots loot) {
		super(player, item, EnchantmentLocation.CHEST_LOOT, loot);
	}

	private ChestEnchantments(Player player, ItemStack item, EnchantmentLocation location, Loots loot) {
		super(player, item, location, loot);
	}

	public static ChestEnchantments getChestEnchantment(Player player, ItemStack item, String type, Loots defaultLoot, EnchantmentLocation location) {
		if (item == null) return null;
		Loots loot = Loots.getLoot(type);
		if (loot == null) loot = defaultLoot;

		return new ChestEnchantments(player, item, location, loot);
	}

}
