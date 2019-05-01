package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Anvil_v1_14_R1 {

	public static int getRepairCost(ItemStack item) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.getRepairCost();
	}
	
	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		nmsItem.setRepairCost(repairCost);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
}
