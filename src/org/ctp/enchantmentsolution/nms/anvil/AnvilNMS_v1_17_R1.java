package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.inventory.ItemStack;

public class AnvilNMS_v1_17_R1 {

	public static int getRepairCost(ItemStack item) {
		net.minecraft.world.item.ItemStack nms = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);
		try {
			return (int) nms.getClass().getDeclaredMethod("getRepairCost").invoke(nms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		net.minecraft.world.item.ItemStack nms = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);
		try {
			nms.getClass().getDeclaredMethod("setRepairCost", int.class).invoke(nms, repairCost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asBukkitCopy(nms);
	}

}
