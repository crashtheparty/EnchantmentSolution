package org.ctp.enchantmentsolution.nms;

import org.bukkit.Material;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Language;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.item.*;

public class ItemNMS {

	public static String returnLocalizedItemName(Language language, ItemStack item) {
		switch (language) {
			case GERMAN:
				return GermanNames.getName(item.getType());
			case CHINA_SIMPLE:
				return ChinaSimplified.getName(item.getType());
			case US:
				switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
					case 1:
						return Item_v1_13_R1.returnLocalizedItemName(item);
					case 2:
					case 3:
						return Item_v1_13_R2.returnLocalizedItemName(item);
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						return Item_v1_14_R1.returnLocalizedItemName(item);
					case 9:
					case 10:
					case 11:
						return Item_v1_15_R1.returnLocalizedItemName(item);
					case 12:
						return Item_v1_16_R1.returnLocalizedItemName(item);
				}
				break;
			default:
				break;
		}
		return item.toString();
	}

	public static String returnLocalizedItemName(Language language, Material material) {
		ItemStack item = new ItemStack(material);
		return returnLocalizedItemName(language, item);
	}

	public static ItemStack getTrident(Trident trident) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return Item_v1_13_R1.getTrident(trident);
			case 2:
			case 3:
				return Item_v1_13_R2.getTrident(trident);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return Item_v1_14_R1.getTrident(trident);
			case 9:
			case 10:
			case 11:
				return Item_v1_15_R1.getTrident(trident);
			case 12:
				return Item_v1_16_R1.getTrident(trident);
		}
		return null;
	}
}
