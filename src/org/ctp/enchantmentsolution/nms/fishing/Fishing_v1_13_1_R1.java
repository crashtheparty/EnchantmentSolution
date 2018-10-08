package org.ctp.enchantmentsolution.nms.fishing;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.ctp.enchantmentsolution.utils.ItemUtils;

import net.minecraft.server.v1_13_R1.ItemStack;

public class Fishing_v1_13_1_R1 {
	
	public static org.bukkit.inventory.ItemStack enchantItem(org.bukkit.inventory.ItemStack item) {
		ItemStack newItem = CraftItemStack.asNMSCopy(item);
		CraftItemStack cItem = CraftItemStack.asCraftCopy(item);
		if((cItem.getType().equals(Material.ENCHANTED_BOOK))) {
			newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(new org.bukkit.inventory.ItemStack(Material.BOOK)));
		} else if (newItem.hasEnchantments()) {
			newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(CraftItemStack.asBukkitCopy(newItem)));
		}
		if(newItem != null) {
			return CraftItemStack.asBukkitCopy(newItem);
		}
		return item;
	}
}
