package org.ctp.enchantmentsolution.nms.itemname;

import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemName_v1_13_R1 {

	public static String returnLocalizedItemName(ItemStack item) {
		net.minecraft.server.v1_13_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.getName().getText();
	}
}
