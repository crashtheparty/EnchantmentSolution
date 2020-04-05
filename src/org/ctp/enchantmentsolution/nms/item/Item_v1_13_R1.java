package org.ctp.enchantmentsolution.nms.item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.craftbukkit.v1_13_R1.entity.CraftTrident;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R1.EntityThrownTrident;

public class Item_v1_13_R1 {

	public static String returnLocalizedItemName(ItemStack item) {
		net.minecraft.server.v1_13_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.getName().getText();
	}
	
	public static ItemStack getTrident(Trident trident) {
		EntityThrownTrident t = ((CraftTrident) trident).getHandle();
		try {
			Class<?> tridentClass = t.getClass();
			Method getHandle = tridentClass.getMethod("getItemStack");
			net.minecraft.server.v1_13_R1.ItemStack a = (net.minecraft.server.v1_13_R1.ItemStack) getHandle.invoke(t);
	        return CraftItemStack.asBukkitCopy(a);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
