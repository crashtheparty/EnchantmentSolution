package org.ctp.enchantmentsolution.listeners.abilities.support;

import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.listeners.BlockListener;

public class McMMOClassicHandler {

	public static void handleMcMMO(BlockBreakEvent event) {
		BlockListener listener = new BlockListener(mcMMO.p);
		listener.onBlockBreakHigher(event);
		listener.onBlockBreak(event);
	}
}
