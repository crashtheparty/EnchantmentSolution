package org.ctp.enchantmentsolution.interfaces.walker;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.WalkerInterface;

public class VoidWalker implements WalkerInterface {

	@Override
	public EnchantmentWrapper getEnchantment() {
		return RegisterEnchantments.VOID_WALKER;
	}

	@Override
	public Material getReplacedMaterial() {
		return Material.OBSIDIAN;
	}

	@Override
	public Material getReplaceMaterial() {
		return Material.AIR;
	}

	@Override
	public String getMetadata() {
		return "VoidWalker";
	}

	@Override
	public boolean replaceAir() {
		return true;
	}

	@Override
	public boolean canRun(Player player, Location from, Location to) {
		return LocationUtils.isLocationDifferent(from, to, false) || LocationUtils.isLocationDifferent(from, to, true);
	}

	@Override
	public Location getProperLocation(Player player, Location from, Location to) {
		if (LocationUtils.isLocationDifferent(from, to, true)) return to;
		return player.getLocation();
	}

	@Override
	public EventPriority getPriority() {
		return EventPriority.NORMAL;
	}

}
