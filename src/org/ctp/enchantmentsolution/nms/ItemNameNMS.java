package org.ctp.enchantmentsolution.nms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enums.Language;
import org.ctp.enchantmentsolution.nms.itemname.*;

public class ItemNameNMS {

	public static String returnLocalizedItemName(Language language, ItemStack item) {
		switch (language) {
			case GERMAN:
				return GermanNames.getName(item.getType());
			case CHINA_SIMPLE:
				return ChinaSimplified.getName(item.getType());
			case US:
				switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
					case 1:
						return ItemName_v1_13_R1.returnLocalizedItemName(item);
					case 2:
					case 3:
						return ItemName_v1_13_R2.returnLocalizedItemName(item);
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						return ItemName_v1_14_R1.returnLocalizedItemName(item);
					case 9:
						return ItemName_v1_15_R1.returnLocalizedItemName(item);
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
}
