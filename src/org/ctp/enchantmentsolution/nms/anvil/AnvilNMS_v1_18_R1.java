package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.inventory.ItemStack;

public class AnvilNMS_v1_18_R1 {

	public static int getRepairCost(ItemStack item) {
		net.minecraft.world.item.ItemStack nms = org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack.asNMSCopy(item);
		return nms.F();
	}
	
	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		net.minecraft.world.item.ItemStack nms = org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack.asNMSCopy(item);
		nms.c(repairCost);
		return org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack.asBukkitCopy(nms);
	}
	
}
