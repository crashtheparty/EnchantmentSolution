package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.anvil.Anvil_v1_13_R2;
import org.ctp.enchantmentsolution.nms.anvil.Anvil_v1_14_R1;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.anvil.Anvil_v1_13_R1;

public class AnvilNMS {

	public static int getRepairCost(ItemStack item) {
		if(item == null) {
			return 0;
		}
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
			return Anvil_v1_13_R1.getRepairCost(item);
		case 2:
		case 3:
			return Anvil_v1_13_R2.getRepairCost(item);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return Anvil_v1_14_R1.getRepairCost(item);
		}
		return 0;
	}
	
	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
			return Anvil_v1_13_R1.setRepairCost(item, repairCost);
		case 2:
		case 3:
			return Anvil_v1_13_R2.setRepairCost(item, repairCost);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return Anvil_v1_14_R1.setRepairCost(item, repairCost);
		}
		return item;
	}
}
