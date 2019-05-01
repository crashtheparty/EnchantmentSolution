package org.ctp.enchantmentsolution.nms.fishing;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import net.minecraft.server.v1_14_R1.ItemStack;

public class Fishing_v1_14_R1 {
	
	public static org.bukkit.inventory.ItemStack enchantItem(org.bukkit.inventory.ItemStack item) {
		ItemStack newItem = CraftItemStack.asNMSCopy(item);
		CraftItemStack cItem = CraftItemStack.asCraftCopy(item);
		if((cItem.getType().equals(Material.ENCHANTED_BOOK))) {
			newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(new org.bukkit.inventory.ItemStack(Material.BOOK), "fishing"));
		} else if (newItem.hasEnchantments()) {
			newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(CraftItemStack.asBukkitCopy(newItem), "fishing"));
		}
		if(newItem != null) {
			return CraftItemStack.asBukkitCopy(newItem);
		}
		return item;
	}
}
