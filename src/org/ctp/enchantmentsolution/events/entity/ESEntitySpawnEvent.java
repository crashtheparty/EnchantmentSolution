package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ESEntitySpawnEvent extends ESEntityEvent {

	private final LivingEntity spawner;
	private Location spawnLocation;

	public ESEntitySpawnEvent(Entity who, EnchantmentLevel enchantment, LivingEntity spawner, Location spawnLocation) {
		super(who, enchantment);
		this.spawner = spawner;
		this.spawnLocation = spawnLocation;
	}

	public LivingEntity getSpawner() {
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
