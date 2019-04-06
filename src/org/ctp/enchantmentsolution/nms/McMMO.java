package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.support.McMMOClassicHandler;
import org.ctp.eswrapper.McMMOOverhaulHandler;

public class McMMO {
	
	public static void handleMcMMO(BlockBreakEvent event, ItemStack item) {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulHandler.handleMcMMO(event, item);
			break;
		case "Classic":
			McMMOClassicHandler.handleMcMMO(event);
			break;
		}
	}
	
	public static void customName(Entity e) {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
		case "Classic":
			McMMOClassicHandler.customName(e);
			break;
		}
	}
	
}
