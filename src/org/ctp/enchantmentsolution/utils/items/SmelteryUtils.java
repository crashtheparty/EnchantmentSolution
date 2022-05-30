package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile.ItemBreakFileType;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile.ItemSpecialBreakFileType;

public class SmelteryUtils {

	private static Material getDrop(BlockData data, ItemStack item) {
		ItemSpecialBreakFile file = ItemSpecialBreakFile.getFile(ItemSpecialBreakFileType.SMELTERY);

		ItemBreakType type = ItemBreakType.getType(item.getType());
		Material material = file.getValues().get(data.getMaterial());
		if (material != null && (type != null && type.getBreakTypes().contains(data.getMaterial()) || ItemBreakType.getBasicTypes(ItemBreakFileType.BREAK).contains(data.getMaterial()))) return material;
		return Material.AIR;
	}

	public static SmelteryMaterial getSmelteryItem(BlockData data, ItemStack from, ItemStack item) {
		Material type = getDrop(data, item);
		if (!MatData.isAir(type)) {
			Material f = from.getType();
			return new SmelteryMaterial(new ItemStack(type, getAmount(item, from)), f, type, data);
		}
		return null;
	}

	private static int getAmount(ItemStack item, ItemStack from) {
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS);
			if (from.getType() == Material.ANCIENT_DEBRIS) {
				double extraAmount = Math.random() * (level * 0.15);
				double rand = Math.random();
				int amount = 0;
				while (extraAmount >= 1) {
					extraAmount--;
					amount++;
				}
				if (extraAmount > rand) amount++;
				return amount + from.getAmount();
			}
		}
		return from.getAmount();
	}
}
