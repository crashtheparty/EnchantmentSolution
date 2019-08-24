package org.ctp.enchantmentsolution.listeners.abilities.support;

import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.listeners.BlockListener;

public class McMMOClassicHandler {

	public static void handleMcMMO(BlockBreakEvent event) {
		BlockListener listener = new BlockListener(mcMMO.p);
		listener.onBlockBreakHigher(event);
		listener.onBlockBreak(event);
	}

	public static void customName(Entity e) {
		if (e.hasMetadata(mcMMO.customNameKey)) {
			String oldName = e.getMetadata(mcMMO.customNameKey).get(0).asString();
			e.setCustomName(oldName);
		}
		if (e.hasMetadata(mcMMO.customVisibleKey)) {
			boolean oldNameVisible = e.getMetadata(mcMMO.customVisibleKey).get(0).asBoolean();
			e.setCustomNameVisible(oldNameVisible);
		}
	}
}
