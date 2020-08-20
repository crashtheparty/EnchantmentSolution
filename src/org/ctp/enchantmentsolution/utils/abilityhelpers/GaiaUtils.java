package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public class GaiaUtils {
	private static ArrayList<Location> BLOCKS = new ArrayList<Location>();

	public static boolean hasLocation(Location loc) {
		for(Location l: BLOCKS)
			if (loc.getBlock().equals(l.getBlock())) return true;
		return false;
	}

	public static boolean removeLocation(Location loc) {
		return BLOCKS.remove(loc);
	}

	public static void addLocation(Location loc) {
		if (!hasLocation(loc)) BLOCKS.add(loc);
	}

	public static List<Location> getLocations() {
		List<Location> locs = new ArrayList<Location>();
		for(Location block: BLOCKS)
			locs.add(block.clone());
		return locs;
	}

	public enum GaiaTrees {
		ACACIA("ACACIA_LOG", "ACACIA_LEAVES", "ACACIA_SAPLING", GaiaTrees.getOverworldGrow()),
		BIRCH("BIRCH_LOG", "BIRCH_LEAVES", "BIRCH_SAPLING", GaiaTrees.getOverworldGrow()),
		DARK_OAK("DARK_OAK_LOG", "DARK_OAK_LEAVES", "DARK_OAK_SAPLING", GaiaTrees.getOverworldGrow()),
		JUNGLE("JUNGLE_LOG", "JUNGLE_LEAVES", "JUNGLE_SAPLING", GaiaTrees.getOverworldGrow()),
		OAK("OAK_LOG", "OAK_LEAVES", "OAK_SAPLING", GaiaTrees.getOverworldGrow()),
		SPRUCE("SPRUCE_LOG", "SPRUCE_LEAVES", "SPRUCE_SAPLING", GaiaTrees.getOverworldGrow()),
		CRIMSON("CRIMSON_STEM", "NETHER_WART_BLOCK", "CRIMSON_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM")),
		WARPED("WARPED_STEM", "WARPED_WART_BLOCK", "WARPED_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM"));

		private final MatData log, leaf, sapling;
		private List<MatData> growable;

		GaiaTrees(String log, String leaf, String sapling, List<String> grow) {
			this.log = new MatData(log);
			this.leaf = new MatData(leaf);
			this.sapling = new MatData(sapling);
			growable = new ArrayList<MatData>();
			for(String s: grow)
				growable.add(new MatData(s));
		}

		public static GaiaTrees getTree(Material mat) {
			switch (mat.name()) {
				case "ACACIA_LOG":
					return ACACIA;
				case "BIRCH_LOG":
					return BIRCH;
				case "DARK_OAK_LOG":
					return DARK_OAK;
				case "JUNGLE_LOG":
					return JUNGLE;
				case "OAK_LOG":
					return OAK;
				case "SPRUCE_LOG":
					return SPRUCE;
				case "CRIMSON_STEM":
					return CRIMSON;
				case "WARPED_STEM":
					return WARPED;
			}
			return null;
		}

		private static List<String> getOverworldGrow() {
			return Arrays.asList("GRASS_BLOCK", "GRASS_PATH", "DIRT", "PODZOL");
		}

		public List<MatData> getGrowable() {
			return growable;
		}

		public MatData getLog() {
			return log;
		}

		public MatData getLeaf() {
			return leaf;
		}

		public MatData getSapling() {
			return sapling;
		}
	}
}
