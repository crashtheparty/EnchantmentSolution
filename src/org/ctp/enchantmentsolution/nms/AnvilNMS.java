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
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 17, 0)) repairCost = Anvil_1.getRepairCost(item);
		else {
			net.minecraft.server.v1_16_R3.ItemStack nms = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
			repairCost = nms.getRepairCost();
		}
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();
		return repairCost;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();

		if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 0)) return Anvil_2.setRepairCost(item, repairCost);
		else if (isSimilarOrAbove(getVersionNumbers(), 1, 17, 0)) return Anvil_1.setRepairCost(item, repairCost);
		else {
			net.minecraft.server.v1_16_R3.ItemStack nms = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
			nms.setRepairCost(repairCost);
			return org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(nms);
		}
	}
}
