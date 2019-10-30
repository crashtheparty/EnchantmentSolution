package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_13_R2;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_14_R1;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_13_R1;

public class Anvil_GUI_NMS {

	public static void createAnvil(Player player, InventoryData data) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
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
		}
	}
}
