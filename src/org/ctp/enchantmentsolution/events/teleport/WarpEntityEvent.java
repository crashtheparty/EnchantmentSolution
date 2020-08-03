package org.ctp.enchantmentsolution.events.teleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WarpEntityEvent extends EntityTeleportEvent {

	private List<Location> otherLocations;
	private final EnchantmentLevel enchantment;

	public WarpEntityEvent(LivingEntity entity, Location from, Location to, List<Location> otherLocations, int level) {
		super(entity, from, to);
		enchantment = new EnchantmentLevel(CERegister.WARP, level);
		this.otherLocations = otherLocations;
	}

	public List<Location> getOtherLocations() {
		return otherLocations;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}
