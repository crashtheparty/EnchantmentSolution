package org.ctp.enchantmentsolution.interfaces.walker;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.WalkerInterface;

public class MagmaWalker implements WalkerInterface {

	@Override
	public Enchantment getEnchantment() {
		return RegisterEnchantments.MAGMA_WALKER;
	}

	@Override
	public Material getReplacedMaterial() {
		return Material.MAGMA_BLOCK;
	}

	@Override
	public Material getReplaceMaterial() {
		return Material.LAVA;
	}

	@Override
	public String getMetadata() {
		return "MagmaWalker";
	}

	@Override
	public boolean replaceAir() {
		return false;
	}

	@Override
	public boolean canRun(Player player, Location from, Location to) {
		return LocationUtils.isOnGround(player);
	}

	@Override
	public Location getProperLocation(Player player, Location from, Location to) {
		return player.getLocation();
	}

}
