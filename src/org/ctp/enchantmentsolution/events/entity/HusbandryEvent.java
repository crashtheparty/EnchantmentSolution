package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class HusbandryEvent extends ESEntitySpawnEvent {

	public HusbandryEvent(Entity who, LivingEntity damager, Location spawnLocation, int level) {
		super(who, new EnchantmentLevel(CERegister.HUSBANDRY, level), damager, spawnLocation);
	}

}
