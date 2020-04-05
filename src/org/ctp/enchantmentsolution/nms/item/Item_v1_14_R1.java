package org.ctp.enchantmentsolution.nms.item;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftTrident;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_14_R1.EntityThrownTrident;

public class Item_v1_14_R1 {

	public static String returnLocalizedItemName(ItemStack item) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.getName().getText();
	}
	
	public static ItemStack getTrident(Trident trident) {
		EntityThrownTrident t = ((CraftTrident) trident).getHandle();
        net.minecraft.server.v1_14_R1.ItemStack a = t.trident;
        return CraftItemStack.asBukkitCopy(a);
	}
}
