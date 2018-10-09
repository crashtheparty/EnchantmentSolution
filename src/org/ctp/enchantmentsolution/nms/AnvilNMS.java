package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.anvil.Anvil_v1_13_R2;
import org.ctp.enchantmentsolution.nms.anvil.Anvil_v1_13_R1;

public class AnvilNMS {

	public static int getRepairCost(ItemStack item) {
		if(item == null) {
			return 0;
		}
		switch(Version.VERSION_NUMBER) {
		case 1:
			return Anvil_v1_13_R1.getRepairCost(item);
		case 2:
			return Anvil_v1_13_R2.getRepairCost(item);
		}
		return 0;
	}
	
	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		switch(Version.VERSION_NUMBER) {
		case 1:
			return Anvil_v1_13_R1.setRepairCost(item, repairCost);
		case 2:
			return Anvil_v1_13_R2.setRepairCost(item, repairCost);
		}
		return item;
	}
}
