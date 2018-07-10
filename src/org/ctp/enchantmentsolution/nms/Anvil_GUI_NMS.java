package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_13_R1;

public class Anvil_GUI_NMS {

	public static void createAnvil(Player player, Anvil anvil) {
		switch(Version.VERSION_NUMBER) {
		case 1:
			AnvilGUI_v1_13_R1.createAnvil(player, anvil);
			break;
		}
	}
}
