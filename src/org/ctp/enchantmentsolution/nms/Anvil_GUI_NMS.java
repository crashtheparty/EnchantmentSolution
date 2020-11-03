package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.anvil.*;

public class Anvil_GUI_NMS {

	public static void createAnvil(Player player, InventoryData data) {
		switch (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				AnvilGUI_v1_13_R1.createAnvil(player, data);
				break;
			case 2:
			case 3:
				AnvilGUI_v1_13_R2.createAnvil(player, data);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				AnvilGUI_v1_14_R1.createAnvil(player, data);
				break;
			case 9:
			case 10:
			case 11:
				AnvilGUI_v1_15_R1.createAnvil(player, data);
				break;
			case 12:
				AnvilGUI_v1_16_R1.createAnvil(player, data);
				break;
			case 13:
			case 14:
				AnvilGUI_v1_16_R2.createAnvil(player, data);
				break;
			case 15:
				AnvilGUI_v1_16_R3.createAnvil(player, data);
				break;
		}
	}
}
