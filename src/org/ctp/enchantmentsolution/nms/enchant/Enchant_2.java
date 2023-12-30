package org.ctp.enchantmentsolution.nms.enchant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;
import org.ctp.enchantmentsolution.Chatable;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.advancements.critereon.CriterionTriggerEnchantedItem;
import net.minecraft.server.level.EntityPlayer;

public class Enchant_2 extends NMS {
	
	public static void updateCriterion(Player player, ItemStack item) {
		EntityPlayer p = (EntityPlayer) getCraftBukkitEntity(player);
		net.minecraft.world.item.ItemStack i = asNMSCopy(item);
		try {
			Class<?> c1 = CriterionTriggers.class;
			Field f = c1.getDeclaredField("j");
			Object o = f.get(null);
			if (o instanceof CriterionTriggerEnchantedItem) {
				Class<?> c2 = CriterionTriggerEnchantedItem.class;
				Method m = c2.getDeclaredMethod("a", EntityPlayer.class, net.minecraft.world.item.ItemStack.class, int.class);
				m.invoke(o, p, i, 1);
			}
			else
				Chatable.get().sendWarning("Issue with EnchantNMS - unable to find TriggerEnchantedItem");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
