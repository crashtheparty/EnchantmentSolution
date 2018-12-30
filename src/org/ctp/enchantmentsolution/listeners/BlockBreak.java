package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;
import org.ctp.enchantmentsolution.listeners.abilities.VoidWalkerListener;

public class BlockBreak implements Listener{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(VoidWalkerListener.BLOCKS.contains(event.getBlock())) {
			event.setCancelled(true);
		}
		if(MagmaWalkerListener.BLOCKS.contains(event.getBlock())) {
			event.setCancelled(true);
		}
	}
}
