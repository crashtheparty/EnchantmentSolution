package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.fishing.Fishing_v1_13_R2;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.fishing.Fishing_v1_13_R1;

public class FishingNMS {
	public static ItemStack replaceLoot(ItemStack i){
		switch(EnchantmentSolution.getBukkitVersion().getVersionNumber()) {
		case 1:
			return Fishing_v1_13_R1.enchantItem(i);
		case 2:
		case 3:
			return Fishing_v1_13_R2.enchantItem(i);
		}
		return i;
	}
}
