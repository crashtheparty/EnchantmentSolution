package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.NMS;
import org.ctp.enchantmentsolution.nms.enchant.Enchant_1;
import org.ctp.enchantmentsolution.nms.enchant.Enchant_v1_16_R3;

public class EnchantNMS extends NMS {

	public static void updateCriterion(Player player, ItemStack item) {
		switch (getVersionNumber()) {
			case 16:
				Enchant_v1_16_R3.updateCriterion(player, item);
				break;
			default:
				if (isSimilarOrAbove(getVersionNumbers(), 1, 17, 0)) Enchant_1.updateCriterion(player, item);
				break;
		}
	}
}
