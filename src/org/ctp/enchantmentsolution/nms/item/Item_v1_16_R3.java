package org.ctp.enchantmentsolution.nms.item;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTrident;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.EntityThrownTrident;

public class Item_v1_16_R3 {

	public static String returnLocalizedItemName(ItemStack item) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.getName().getText();
	}

	public static ItemStack getTrident(Trident trident) {
		EntityThrownTrident t = ((CraftTrident) trident).getHandle();
		net.minecraft.server.v1_16_R3.ItemStack a = t.trident;
		return CraftItemStack.asBukkitCopy(a);
	}
}
