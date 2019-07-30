package org.ctp.enchantmentsolution.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerWarpEvent extends PlayerTeleportEvent{

	private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
	
	public PlayerWarpEvent(Player player, Location from, Location to) {
		// treat the event like a chorus fruit event
		super(player, from, to, TeleportCause.CHORUS_FRUIT);
	}

}
