package org.ctp.enchantmentsolution.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class LocationUtils {

	public static boolean isLocationDifferent(Location locOne, Location locTwo, boolean includeY) {
		return locOne.getX() != locTwo.getX() || locOne.getZ() != locTwo.getZ() || includeY && locTwo.getY() != locOne.getY();
	}

	public static boolean isLocationSame(Location locOne, Location locTwo, boolean includeY) {
		return locOne.getX() == locTwo.getX() && locOne.getZ() == locTwo.getZ() && (!includeY || locTwo.getY() == locOne.getY());
	}

	public static boolean hasBlockAbove(Player player) {
		for(int y = player.getLocation().getBlockY(); y < player.getWorld().getMaxHeight(); y++) {
			Location loc = player.getLocation().clone().add(0, y, 0);
			if (!Arrays.asList("AIR", "VOID_AIR", "CAVE_AIR").contains(loc.getBlock().getType().name())) return true;
		}
		return false;
	}

	public static boolean hasBlockBelow(Location location) {
		for(int y = location.getBlockY(); y >= 0; y--) {
			Location loc = location.clone();
			loc.setY(y);
			if (!Arrays.asList("AIR", "VOID_AIR", "CAVE_AIR").contains(loc.getBlock().getType().name())) return true;
		}
		return false;
	}

	public static boolean getIntersecting(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		if (loc1a.getWorld() != loc2a.getWorld()) return false;

		if (!intersectsDimension(loc1a.getBlockX(), loc1b.getBlockX(), loc2a.getBlockX(), loc2b.getBlockX())) return false;

		if (!intersectsDimension(loc1a.getBlockY(), loc1b.getBlockY(), loc2a.getBlockY(), loc2b.getBlockY())) return false;

		if (!intersectsDimension(loc1a.getBlockZ(), loc1b.getBlockZ(), loc2a.getBlockZ(), loc2b.getBlockZ())) return false;
		return true;
	}

	private static boolean intersectsDimension(int aMin, int aMax, int bMin, int bMax) {
		int aOne = aMin > aMax ? aMax : aMin;
		int aTwo = aMin > aMax ? aMin : aMax;
		int bOne = aMin > aMax ? bMax : bMin;
		int bTwo = aMin > aMax ? bMin : bMax;
		return aOne <= bTwo && aTwo >= bOne;
	}

	public static Location stringToLocation(String string) {
		Location loc = null;
		try {
			String[] values = string.split(" @ ");
			if (values.length == 4) loc = new Location(Bukkit.getWorld(values[3]), Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		} catch (Exception ex) {

		}
		return loc;
	}

	public static String locationToString(Location loc) {
		if (loc == null) return "";
		return loc.getBlockX() + " @ " + loc.getBlockY() + " @ " + loc.getBlockZ() + " @ " + loc.getWorld().getName();
	}

	public static Location offset(Location location) {
		return location.clone().add(0.5, 0.5, 0.5);
	}

	public static int getBookshelves(Location loc) {
		int bookshelves = 0;
		for(int x = loc.getBlockX() - 2; x < loc.getBlockX() + 3; x++)
			for(int y = loc.getBlockY(); y < loc.getBlockY() + 2; y++)
				for(int z = loc.getBlockZ() - 2; z < loc.getBlockZ() + 3; z++)
					if (x == loc.getBlockX() - 2 || x == loc.getBlockX() + 2 || z == loc.getBlockZ() - 2 || z == loc.getBlockZ() + 2) {
						Location bookshelf = new Location(loc.getWorld(), x, y, z);
						if (bookshelf.getBlock().getType().equals(Material.BOOKSHELF)) bookshelves++;
					}
		if (ConfigString.LEVEL_FIFTY.getBoolean()) {
			if (bookshelves > 23) bookshelves = 23;
		} else if (bookshelves > 15) bookshelves = 15;
		return bookshelves;
	}

	public static void dropExperience(Location loc, int amount, boolean offset) {
		Location location = offset ? offset(loc) : loc.clone();
		if (amount > 0) location.getWorld().spawn(location, ExperienceOrb.class).setExperience(amount);
	}
}
