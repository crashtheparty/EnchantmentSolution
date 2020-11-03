package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.hotbar.*;

public class HotbarNMS {

	public static void sendHotBarMessage(Player player, String message) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				Hotbar_v1_13_R1.sendHotBarMessage(player, message);
				break;
			case 2:
			case 3:
				Hotbar_v1_13_R2.sendHotBarMessage(player, message);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				Hotbar_v1_14_R1.sendHotBarMessage(player, message);
				break;
			case 9:
			case 10:
			case 11:
				Hotbar_v1_15_R1.sendHotBarMessage(player, message);
				break;
			case 12:
				Hotbar_v1_16_R1.sendHotBarMessage(player, message);
				break;
			case 13:
			case 14:
				Hotbar_v1_16_R2.sendHotBarMessage(player, message);
				break;
			case 15:
				Hotbar_v1_16_R3.sendHotBarMessage(player, message);
				break;
		}
	}
}
