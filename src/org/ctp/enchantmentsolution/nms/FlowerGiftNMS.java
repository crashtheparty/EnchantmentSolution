package org.ctp.enchantmentsolution.nms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.flowergift.FlowerGiftChance_v1_13;
import org.ctp.enchantmentsolution.nms.flowergift.FlowerGiftChance_v1_14;

public class FlowerGiftNMS {

	public static boolean isItem(Material material) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return FlowerGiftChance_v1_13.isItem(material);
		case 4:
		case 5:
			return FlowerGiftChance_v1_14.isItem(material);
		}
		return false;
	}
	
	public static ItemStack getItem(Material material) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return FlowerGiftChance_v1_13.getItem(material);
		case 4:
		case 5:
			return FlowerGiftChance_v1_14.getItem(material);
		}
		return null;
	}
}
