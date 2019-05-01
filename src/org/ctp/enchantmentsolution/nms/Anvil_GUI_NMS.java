package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_13_R2;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_14_R1;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI_v1_13_R1;

public class Anvil_GUI_NMS {

	public static void createAnvil(Player player, Anvil anvil) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
			AnvilGUI_v1_13_R1.createAnvil(player, anvil);
			break;
		case 2:
		case 3:
			AnvilGUI_v1_13_R2.createAnvil(player, anvil);
			break;
		case 4:
			AnvilGUI_v1_14_R1.createAnvil(player, anvil);
			break;
		}
	}
	
	public static void createAnvil(Player player, ConfigInventory anvil) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
			AnvilGUI_v1_13_R1.createAnvil(player, anvil);
			break;
		case 2:
		case 3:
			AnvilGUI_v1_13_R2.createAnvil(player, anvil);
			break;
		case 4:
			AnvilGUI_v1_14_R1.createAnvil(player, anvil);
			break;
		}
	}
}
