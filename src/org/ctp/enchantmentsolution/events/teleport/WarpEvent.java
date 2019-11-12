package org.ctp.enchantmentsolution.events.teleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTeleportEvent;

public class WarpEvent extends EntityTeleportEvent {

	private List<Location> otherLocations;

	public WarpEvent(LivingEntity player, Location from, Location to, List<Location> otherLocations) {
		super(player, from, to);
		setOtherLocations(otherLocations);
	}

	public List<Location> getOtherLocations() {
		return otherLocations;
	}

	public void setOtherLocations(List<Location> otherLocations) {
		this.otherLocations = otherLocations;
	}

}
