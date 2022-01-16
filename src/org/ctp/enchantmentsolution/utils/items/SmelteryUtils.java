package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile.ItemBreakFileType;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile.ItemSpecialBreakFileType;

public class SmelteryUtils {

	private static Material getDrop(BlockData data, ItemStack item) {
		ItemSpecialBreakFile file = ItemSpecialBreakFile.getFile(ItemSpecialBreakFileType.SMELTERY);

		ItemBreakType type = ItemBreakType.getType(item.getType());
		if (type != null && type.getBreakTypes().contains(data.getMaterial()) || ItemBreakType.getBasicTypes(ItemBreakFileType.BREAK).contains(data.getMaterial())) return file.getValues().get(data.getMaterial());
		return Material.AIR;
	}

	public static SmelteryMaterial getSmelteryItem(BlockData data, ItemStack from, ItemStack item) {
		Material type = getDrop(data, item);
		if (!MatData.isAir(type)) {
			Material f = from.getType();
			return new SmelteryMaterial(new ItemStack(type), f, type, data);
		}
		return null;
	}

	public static int getFortuneForSmeltery(ItemStack smelted, ItemStack item, Material original) {
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS);
			switch (original.name()) {
				case "ANCIENT_DEBRIS":
					double extraAmount = Math.random() * (level * 0.15);
					double rand = Math.random();
					int amount = 1;
					while (extraAmount >= 1) {
						extraAmount--;
						amount++;
					}
					if (extraAmount > rand) amount++;
					return amount;
				case "IRON_ORE":
				case "NETHER_GOLD_ORE":
				case "GOLD_ORE":
					if (VersionUtils.getVersionNumber() > 16) return smelted.getAmount();
					int multiply = (int) (Math.random() * (level + 2));
					if (multiply > 1) return smelted.getAmount() * multiply;
			}
		}
		return smelted.getAmount();
	}
}
