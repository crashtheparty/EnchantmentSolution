package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;

public class Anvil_4 extends NMS {

	public static int getRepairCost(ItemStack item) {
		net.minecraft.world.item.ItemStack i = asNMSCopy(item);
		try {
			return (int) i.getClass().getDeclaredMethod("H").invoke(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
