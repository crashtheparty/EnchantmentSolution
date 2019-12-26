package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class AnvilNMS {

	public static int getRepairCost(ItemStack item) {
		if (item == null) return 0;
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				net.minecraft.server.v1_13_R1.ItemStack nmsV1 = org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV1.getRepairCost();
			case 2:
			case 3:
				net.minecraft.server.v1_13_R2.ItemStack nmsV2 = org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV2.getRepairCost();
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				net.minecraft.server.v1_14_R1.ItemStack nmsV3 = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV3.getRepairCost();
			case 9:
			case 10:
				net.minecraft.server.v1_15_R1.ItemStack nmsV4 = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV4.getRepairCost();
		}
		return 0;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				net.minecraft.server.v1_13_R1.ItemStack nmsV1 = org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack.asNMSCopy(item);
				nmsV1.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack.asBukkitCopy(nmsV1);
			case 2:
			case 3:
				net.minecraft.server.v1_13_R2.ItemStack nmsV2 = org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack.asNMSCopy(item);
				nmsV2.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack.asBukkitCopy(nmsV2);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				net.minecraft.server.v1_14_R1.ItemStack nmsV3 = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asNMSCopy(item);
				nmsV3.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asBukkitCopy(nmsV3);
			case 9:
			case 10:
				net.minecraft.server.v1_15_R1.ItemStack nmsV4 = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(item);
				nmsV4.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asBukkitCopy(nmsV4);
		}
		return item;
	}
}
