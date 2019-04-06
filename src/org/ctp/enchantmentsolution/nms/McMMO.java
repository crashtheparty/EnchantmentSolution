package org.ctp.enchantmentsolution.nms;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.support.McMMOClassicAbility;
import org.ctp.enchantmentsolution.listeners.abilities.support.McMMOClassicHandler;
import org.ctp.eswrapper.McMMOOverhaulAbility;
import org.ctp.eswrapper.McMMOOverhaulHandler;

public class McMMO {
	
	public static void handleMcMMO(BlockBreakEvent event) {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulHandler.handleMcMMO(event);
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
	
	public static Listener getAbilities() {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			return new McMMOOverhaulAbility();
		case "Classic":
			return new McMMOClassicAbility();
		}
		return null;
	}
	
	public static List<Player> getIgnoredPlayers() {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			return McMMOOverhaulAbility.getIgnored();
		case "Classic":
			return McMMOClassicAbility.getIgnored();
		}
		return null;
	}
	
}
