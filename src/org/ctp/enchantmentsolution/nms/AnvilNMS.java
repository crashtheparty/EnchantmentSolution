package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;
import org.ctp.enchantmentsolution.nms.anvil.*;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class AnvilNMS extends NMS {

	public static int getRepairCost(ItemStack item) {
		if (item == null) return 0;
		int repairCost = 0;
		if (isSimilarOrAbove(getVersionNumbers(), 1, 19, 0)) repairCost = Anvil_4.getRepairCost(item);
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 2)) repairCost = Anvil_3.getRepairCost(item);
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 0)) repairCost = Anvil_2.getRepairCost(item);
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 16, 5)) repairCost = Anvil_1.getRepairCost(item);
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();
		return repairCost;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();

		if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 0)) return Anvil_2.setRepairCost(item, repairCost);
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 16, 5)) return Anvil_1.setRepairCost(item, repairCost);

		return item;
	}
}
