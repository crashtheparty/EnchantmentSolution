package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;

public class MobLootEnchantments extends LootEnchantments {

	private MobLootEnchantments(Player player, ItemStack item, Loots loot) {
		super(player, item, EnchantmentLocation.MOB_LOOT, loot);
	}

	public static MobLootEnchantments generateMobLoot(ItemStack item, String type, Loots defaultLoot) {
		if (item == null) return null;
		Loots loot = Loots.getLoot(type);
		if (loot == null) loot = defaultLoot;

		return new MobLootEnchantments(null, item, loot);
	}

}
