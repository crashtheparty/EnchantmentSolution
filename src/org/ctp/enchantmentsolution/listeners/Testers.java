package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.blocks.SmelteryEvent;
import org.ctp.enchantmentsolution.events.blocks.TelepathyEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Testers implements Listener {

	@EventHandler
	public void onSmelteryEvent(SmelteryEvent event) {
		ChatUtils.sendInfo("Smeltery Event Found: " + event.getExp() + " " + event.getBlock().getLocation().toString()
		+ " " + event.getBlock().getType().name() + " " + event.getChangeTo().name() + " " + event.getDrop().toString());
	}

	@EventHandler
	public void onTelepathyEvent(TelepathyEvent event) {
		ChatUtils
		.sendInfo("Telepathy Event Found: " + event.getType().name() + " " + event.getBlock().getLocation().toString());

		for(ItemStack i: event.getDrops()) {
			ChatUtils.sendInfo(i.toString());
		}
	}
}
