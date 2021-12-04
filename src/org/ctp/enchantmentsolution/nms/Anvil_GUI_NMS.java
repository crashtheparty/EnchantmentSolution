package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_16_R3;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_17_R1;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_18_R1;
import org.ctp.enchantmentsolution.utils.VersionUtils;

public class Anvil_GUI_NMS {

	public static void createAnvil(Player player, InventoryData data) {
		switch (VersionUtils.getVersionNumber()) {
			case 16:
				AnvilGUI_v1_16_R3.createAnvil(player, data);
				break;
			case 18:
				AnvilGUI_v1_17_R1.createAnvil(player, data);
				break;
			case 19:
			case 20:
				AnvilGUI_v1_18_R1.createAnvil(player, data);
				break;
		}
	}
}
