package org.ctp.enchantmentsolution.listeners.abilities.support;

import org.bukkit.event.block.BlockBreakEvent;

import us.eunoians.mcrpg.events.vanilla.BreakEvent;

public class McRPGHandler {
	
	public static void handleMcRPG(BlockBreakEvent event) {
		BreakEvent listener = new BreakEvent();
		listener.breakEvent(event);
	}
}
