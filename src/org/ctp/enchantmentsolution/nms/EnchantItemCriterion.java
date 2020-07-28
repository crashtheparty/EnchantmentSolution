package org.ctp.enchantmentsolution.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.CrashAPI;

public class EnchantItemCriterion {

	public static void enchantItemTrigger(Player player, ItemStack item) {
		String packageName = "net.minecraft.server." + CrashAPI.getPlugin().getBukkitVersion().getAPIVersion();
		String craftPackageName = "org.bukkit.craftbukkit." + CrashAPI.getPlugin().getBukkitVersion().getAPIVersion();
		Object entityPlayer = null;
		Object itemStack = null;
		try {
			Class<?> craftPlayer = Class.forName(craftPackageName + ".entity.CraftPlayer");
			Method getHandle = craftPlayer.getMethod("getHandle");
			entityPlayer = getHandle.invoke(player);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		try {
			Class<?> craftItemStack = Class.forName(craftPackageName + ".inventory.CraftItemStack");
			Method nmsCopy = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
			itemStack = nmsCopy.invoke(null, item);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		if (entityPlayer != null && itemStack != null) try {
			Class<?> clazz = Class.forName(packageName + ".CriterionTriggers");
			Object f = clazz.getDeclaredField("i").get(null);
			f.getClass().getDeclaredMethod("a", entityPlayer.getClass(), itemStack.getClass(), int.class).invoke(f, entityPlayer, itemStack, 1);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
