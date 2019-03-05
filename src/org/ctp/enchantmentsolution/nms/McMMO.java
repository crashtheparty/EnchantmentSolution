package org.ctp.enchantmentsolution.nms;

import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.support.McMMOClassicHandler;
import org.ctp.eswrapper.McMMOOverhaulHandler;

public class McMMO {
	
	public static void handleMcMMO(BlockBreakEvent event) {
		switch(EnchantmentSolution.getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulHandler.handleMcMMO(event);
			break;
		case "Classic":
			McMMOClassicHandler.handleMcMMO(event);
			break;
		}
	}
	
}
