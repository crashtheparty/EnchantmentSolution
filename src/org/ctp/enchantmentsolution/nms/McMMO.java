package org.ctp.enchantmentsolution.nms;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.mcmmo.McMMOClassicHandler;
import org.ctp.eswrapper.McMMOOverhaulHandler;

public class McMMO {
	
	public static void handleMcMMO(BlockBreakEvent event, ItemStack item) {
		switch(EnchantmentSolution.getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulHandler.handleMcMMO(event, item);
			break;
		case "Classic":
			McMMOClassicHandler.handleMcMMO(event);
			break;
		}
	}
	
}
