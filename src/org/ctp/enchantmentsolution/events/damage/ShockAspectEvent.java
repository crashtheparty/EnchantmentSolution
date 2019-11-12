package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.events.ESEntityDamageEntityEvent;

public class ShockAspectEvent extends ESEntityDamageEntityEvent {

	private double chance;
	private Location location;

	public ShockAspectEvent(LivingEntity damaged, LivingEntity damager, double damage, double newDamage, double chance,
	Location location) {
		super(damaged, damager, damage, newDamage);
		setChance(chance);
		setLocation(location);
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
