package org.ctp.enchantmentsolution.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {
	
	public static boolean hasBlockAbove(Player player) {
		for(int y = player.getLocation().getBlockY(); y < player.getWorld().getMaxHeight(); y++) {
			Location loc = player.getLocation().clone().add(0, y, 0);
			if(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.VOID_AIR && loc.getBlock().getType() != Material.CAVE_AIR) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasBlockBelow(Location location) {
		for(int y = location.getBlockY(); y >= 0; y--) {
			Location loc = location.clone();
			loc.setY(y);
			if(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.VOID_AIR && loc.getBlock().getType() != Material.CAVE_AIR) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean getIntersecting(Location loc1a, Location loc1b,
			Location loc2a, Location loc2b) {
		if (loc1a.getWorld() != loc2a.getWorld()) {
			return false;
		}
		int min1X = loc1a.getBlockX() > loc1b.getBlockX() ? loc1b.getBlockX()
				: loc1a.getBlockX();
		int max1X = loc1a.getBlockX() < loc1b.getBlockX() ? loc1b.getBlockX()
				: loc1a.getBlockX();
		int min1Y = loc1a.getBlockY() > loc1b.getBlockY() ? loc1b.getBlockY()
				: loc1a.getBlockY();
		int max1Y = loc1a.getBlockY() < loc1b.getBlockY() ? loc1b.getBlockY()
				: loc1a.getBlockY();
		int min1Z = loc1a.getBlockZ() > loc1b.getBlockZ() ? loc1b.getBlockZ()
				: loc1a.getBlockZ();
		int max1Z = loc1a.getBlockZ() < loc1b.getBlockZ() ? loc1b.getBlockZ()
				: loc1a.getBlockZ();
		int min2X = loc2a.getBlockX() > loc2b.getBlockX() ? loc2b.getBlockX()
				: loc2a.getBlockX();
		int max2X = loc2a.getBlockX() < loc2b.getBlockX() ? loc2b.getBlockX()
				: loc2a.getBlockX();
		int min2Y = loc2a.getBlockY() > loc2b.getBlockY() ? loc2b.getBlockY()
				: loc2a.getBlockY();
		int max2Y = loc2a.getBlockY() < loc2b.getBlockY() ? loc2b.getBlockY()
				: loc2a.getBlockY();
		int min2Z = loc2a.getBlockZ() > loc2b.getBlockZ() ? loc2b.getBlockZ()
				: loc2a.getBlockZ();
		int max2Z = loc2a.getBlockZ() < loc2b.getBlockZ() ? loc2b.getBlockZ()
				: loc2a.getBlockZ();

		if (!intersectsDimension(min1X, max1X, min2X, max2X))
			return false;

		if (!intersectsDimension(min1Z, max1Z, min2Z, max2Z))
			return false;

		if (!intersectsDimension(min1Y, max1Y, min2Y, max2Y))
			return false;
		return true;
	}

	private static boolean intersectsDimension(int aMin, int aMax, int bMin,
			int bMax) {
		return aMin <= bMax && aMax >= bMin;
	}
	
	public static Location stringToLocation(String string) {
		Location loc = null;
		try {
			int x = 0, y = 0, z = 0;
			World world = null;
			String[] values = string.split(" @ ");
			if(values.length == 4) {
				x = Integer.parseInt(values[0]);
				y = Integer.parseInt(values[1]);
				z = Integer.parseInt(values[2]);
				world = Bukkit.getWorld(values[3]);
				
				loc = new Location(world, x, y, z);
			}
		} catch (Exception ex) {
			
		}
		return loc;
	}
	
	public static String locationToString(Location loc) {
		if(loc == null) return "";
		return loc.getBlockX() + " @ " + loc.getBlockY() + " @ " + loc.getBlockZ() + " @ " + loc.getWorld().getName();
	}

}
