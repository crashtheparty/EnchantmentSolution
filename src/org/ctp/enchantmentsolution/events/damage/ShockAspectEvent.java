package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.ESEntityDamageEntityEvent;

public class ShockAspectEvent extends ESEntityDamageEntityEvent {

	private double chance;
	private Location location;

	public ShockAspectEvent(LivingEntity damaged, int level, LivingEntity damager, double damage, double newDamage,
	double chance, Location location) {
		super(damaged, new EnchantmentLevel(CERegister.SHOCK_ASPECT, level), damager, damage, newDamage);
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
