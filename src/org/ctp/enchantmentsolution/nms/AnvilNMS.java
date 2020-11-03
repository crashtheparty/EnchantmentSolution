package org.ctp.enchantmentsolution.nms;

import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.CrashAPI;

public class AnvilNMS {

	public static int getRepairCost(ItemStack item) {
		if (item == null) return 0;
		switch (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
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
			case 11:
				net.minecraft.server.v1_15_R1.ItemStack nmsV4 = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV4.getRepairCost();
			case 12:
				net.minecraft.server.v1_16_R1.ItemStack nmsV5 = org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV5.getRepairCost();
			case 13:
			case 14:
				net.minecraft.server.v1_16_R2.ItemStack nmsV6 = org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV6.getRepairCost();
			case 15:
				net.minecraft.server.v1_16_R3.ItemStack nmsV7 = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
				return nmsV7.getRepairCost();
		}
		return 0;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		switch (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
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
			case 11:
				net.minecraft.server.v1_15_R1.ItemStack nmsV4 = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(item);
				nmsV4.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asBukkitCopy(nmsV4);
			case 12:
				net.minecraft.server.v1_16_R1.ItemStack nmsV5 = org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asNMSCopy(item);
				nmsV5.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asBukkitCopy(nmsV5);
			case 13:
			case 14:
				net.minecraft.server.v1_16_R2.ItemStack nmsV6 = org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asNMSCopy(item);
				nmsV6.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asBukkitCopy(nmsV6);
			case 15:
				net.minecraft.server.v1_16_R3.ItemStack nmsV7 = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(item);
				nmsV7.setRepairCost(repairCost);
				return org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(nmsV7);
		}
		return item;
	}
}
