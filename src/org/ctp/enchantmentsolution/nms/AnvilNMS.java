package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.anvil.AnvilNMS_v1_17_R1;
import org.ctp.enchantmentsolution.nms.anvil.AnvilNMS_v1_18_R1;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class AnvilNMS {

	public static int getRepairCost(ItemStack item) {
		if (item == null) return 0;
		int repairCost = 0;
		switch (VersionUtils.getVersionNumber()) {
			case 16:
				net.minecraft.server.v1_16_R3.ItemStack nmsV7 = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
				repairCost = nmsV7.getRepairCost();
				break;
			case 18:
				repairCost = AnvilNMS_v1_17_R1.getRepairCost(item);
				break;
			case 19:
			case 20:
				repairCost = AnvilNMS_v1_18_R1.getRepairCost(item);
				break;
		}
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();
		return repairCost;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		if (ConfigString.REPAIR_COST_LIMIT.getInt() > 0 && repairCost > ConfigString.REPAIR_COST_LIMIT.getInt()) repairCost = ConfigString.REPAIR_COST_LIMIT.getInt();
		switch (VersionUtils.getVersionNumber()) {
			case 16:
				net.minecraft.server.v1_16_R3.ItemStack nmsV7 = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
				nmsV7.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(nmsV7);
			case 18:
				return AnvilNMS_v1_17_R1.setRepairCost(item, repairCost);
			case 19:
			case 20:
				return AnvilNMS_v1_18_R1.setRepairCost(item, repairCost);
		}
		return item;
	}
}
