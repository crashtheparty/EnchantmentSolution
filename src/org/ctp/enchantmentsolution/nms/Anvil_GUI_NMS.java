package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.crashapi.data.inventory.InventoryData;
import org.ctp.crashapi.nms.NMS;
import org.ctp.enchantmentsolution.nms.anvil.*;

public class Anvil_GUI_NMS extends NMS {

	public static void createAnvil(Player player, InventoryData data) {
		switch (getVersionNumber()) {
			case 16:
				AnvilGUI_v1_16_R3.createAnvil(player, data);
				break;
			default:
				if (isSimilarOrAbove(getVersionNumbers(), 1, 20, 5)) AnvilGUI_10.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 20, 3)) AnvilGUI_9.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 20, 2)) AnvilGUI_8.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 20, 0)) AnvilGUI_7.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 19, 4)) AnvilGUI_6.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 19, 2)) AnvilGUI_5.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 19, 0)) AnvilGUI_4.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 2)) AnvilGUI_3.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 18, 0)) AnvilGUI_2.createAnvil(player, data);
				else if (isSimilarOrAbove(getVersionNumbers(), 1, 17, 0)) AnvilGUI_1.createAnvil(player, data);
				break;
		}
	}
}
