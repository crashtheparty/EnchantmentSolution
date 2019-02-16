package org.ctp.enchantmentsolution.nms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.itemname.ItemName_v1_13_R1;
import org.ctp.enchantmentsolution.nms.itemname.ItemName_v1_13_R2;

public class ItemNameNMS {
	
	public static String returnLocalizedItemName(ItemStack item) {
		switch(EnchantmentSolution.getBukkitVersion().getVersionNumber()) {
		case 1:
			return ItemName_v1_13_R1.returnLocalizedItemName(item);
		case 2:
		case 3:
			return ItemName_v1_13_R2.returnLocalizedItemName(item);
		}
		return item.toString();
	}
	
	public static String returnLocalizedItemName(Material material) {
		ItemStack item = new ItemStack(material);
		return returnLocalizedItemName(item);
	}
}
