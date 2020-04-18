package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HusbandryEvent extends ESEntitySpawnEvent {

	private double chance;

	public HusbandryEvent(Entity who, Player spawner, Location spawnLocation, int level, double chance) {
		super(who, new EnchantmentLevel(CERegister.HUSBANDRY, level), spawner, spawnLocation);
		this.setChance(chance);
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

}
