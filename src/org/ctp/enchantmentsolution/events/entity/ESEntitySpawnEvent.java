package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ESEntitySpawnEvent extends ESEntityEvent {

	private final Player spawner;
	private Location spawnLocation;

	public ESEntitySpawnEvent(Entity who, EnchantmentLevel enchantment, Player spawner, Location spawnLocation) {
		super(who, enchantment);
		this.spawner = spawner;
		this.spawnLocation = spawnLocation;
	}

	public Player getSpawner() {
		return spawner;
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation.clone();
	}

	public World getSpawnWorld() {
		return spawnLocation.getWorld();
	}

}
