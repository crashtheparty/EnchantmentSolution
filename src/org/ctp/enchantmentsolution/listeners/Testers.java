package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.events.teleport.WarpEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Testers implements Listener {

	@EventHandler
	public void onWarpEvent(WarpEvent event) {
		ChatUtils.sendInfo("Warp Event Found: " + event.getTo().toString() + " " + event.getFrom().toString());
		for(Location l: event.getOtherLocations()) {
			ChatUtils.sendInfo(l.toString());
		}
	}
}
