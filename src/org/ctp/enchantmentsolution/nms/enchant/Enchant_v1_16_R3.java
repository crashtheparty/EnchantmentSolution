package org.ctp.enchantmentsolution.nms.enchant;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.CriterionTriggers;
import net.minecraft.server.v1_16_R3.EntityPlayer;

public class Enchant_v1_16_R3 {
	
	public static void updateCriterion(Player player, ItemStack item) {
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
		CriterionTriggers.i.a(p, i, 1);
	}
}
