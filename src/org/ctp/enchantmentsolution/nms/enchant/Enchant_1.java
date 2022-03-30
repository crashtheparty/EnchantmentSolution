package org.ctp.enchantmentsolution.nms.enchant;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.server.level.EntityPlayer;

public class Enchant_1 extends NMS {
	
	public static void updateCriterion(Player player, ItemStack item) {
		EntityPlayer p = (EntityPlayer) getCraftBukkitEntity(player);
		net.minecraft.world.item.ItemStack i = asNMSCopy(item);
		CriterionTriggers.i.a(p, i, 1);
	}

}
