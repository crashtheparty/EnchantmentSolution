package org.ctp.enchantmentsolution.events.teleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WarpEvent extends EntityTeleportEvent {

	private List<Location> otherLocations;
	private final EnchantmentLevel enchantment;

	public WarpEvent(LivingEntity player, Location from, Location to, List<Location> otherLocations, int level) {
		super(player, from, to);
		this.enchantment = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.WARP), level);
		setOtherLocations(otherLocations);
	}

	public List<Location> getOtherLocations() {
		return otherLocations;
	}

	public void setOtherLocations(List<Location> otherLocations) {
		this.otherLocations = otherLocations;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}
