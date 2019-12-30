package org.ctp.enchantmentsolution.events.teleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class WarpPlayerEvent extends PlayerTeleportEvent {

	private List<Location> otherLocations;
	private final EnchantmentLevel enchantment;

	public WarpPlayerEvent(Player player, Location from, Location to, List<Location> otherLocations, int level) {
		super(player, from, to);
		enchantment = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.WARP), level);
		this.otherLocations = otherLocations;
	}

	public List<Location> getOtherLocations() {
		return otherLocations;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

}