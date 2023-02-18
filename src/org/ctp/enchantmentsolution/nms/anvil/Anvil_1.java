package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;

public class Anvil_1 extends NMS {

	public static int getRepairCost(ItemStack item) {
		net.minecraft.world.item.ItemStack i = asNMSCopy(item);
		try {
			return (int) i.getClass().getDeclaredMethod("getRepairCost").invoke(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ItemStack setRepairCost(ItemStack item, int repairCost) {
		net.minecraft.world.item.ItemStack i = asNMSCopy(item);
		try {
			i.getClass().getDeclaredMethod("setRepairCost", int.class).invoke(i, repairCost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asBukkitCopy(i);
	}

}
