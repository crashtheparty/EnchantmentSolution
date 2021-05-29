package org.ctp.enchantmentsolution.utils.items;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;

public class SmelteryUtils {

	private static MatData getDrop(BlockData data, ItemStack item) {
		File file = CrashConfigUtils.getTempFile(new SilkTouchUtils().getClass(), "/resources/abilities/smeltery.yml");
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();

		ItemBreakType type = ItemBreakType.getType(item.getType());
		if (type != null && type.getBreakTypes().contains(data.getMaterial()) || type.getBasicTypes().contains(data.getMaterial())) return new MatData(config.getString(data.getMaterial().name().toLowerCase()));
		return new MatData("air");
	}

	public static SmelteryMaterial getSmelteryItem(BlockData data, ItemStack from, ItemStack item) {
		MatData mat = getDrop(data, item);
		if (mat.hasMaterial() && !MatData.isAir(mat.getMaterial())) {
			Material f = from.getType();
			return new SmelteryMaterial(new ItemStack(mat.getMaterial()), f, mat.getMaterial(), data);
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
