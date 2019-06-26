package org.ctp.enchantmentsolution.nms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.nms.itemname.GermanNames_v1_13;
import org.ctp.enchantmentsolution.nms.itemname.GermanNames_v1_14;
import org.ctp.enchantmentsolution.nms.itemname.ItemName_v1_13_R1;
import org.ctp.enchantmentsolution.nms.itemname.ItemName_v1_13_R2;
import org.ctp.enchantmentsolution.nms.itemname.ItemName_v1_14_R1;

public class ItemNameNMS {
	
	public static String returnLocalizedItemName(Language language, ItemStack item) {
		switch(language) {
		case GERMAN:
			switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
			case 2:
			case 3:
				return GermanNames_v1_13.getName(item.getType());
			case 4:
			case 5:
			case 6:
			case 7:
				return GermanNames_v1_14.getName(item.getType());
			}
			break;
		case US:
			switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return ItemName_v1_13_R1.returnLocalizedItemName(item);
			case 2:
			case 3:
				return ItemName_v1_13_R2.returnLocalizedItemName(item);
			case 4:
			case 5:
			case 6:
			case 7:
				return ItemName_v1_14_R1.returnLocalizedItemName(item);
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
