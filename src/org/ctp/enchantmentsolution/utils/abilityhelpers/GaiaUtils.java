package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enums.LogType;

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
		ACACIA(LogType.ACACIA, "ACACIA_LEAVES", "ACACIA_SAPLING", getOverworldGrow()),
		BIRCH(LogType.BIRCH, "BIRCH_LEAVES", "BIRCH_SAPLING", getOverworldGrow()),
		DARK_OAK(LogType.DARK_OAK, "DARK_OAK_LEAVES", "DARK_OAK_SAPLING", getOverworldGrow()),
		JUNGLE(LogType.JUNGLE, "JUNGLE_LEAVES", "JUNGLE_SAPLING", getOverworldGrow()),
		OAK(LogType.OAK, "OAK_LEAVES", "OAK_SAPLING", getOverworldGrow()),
		SPRUCE(LogType.SPRUCE, "SPRUCE_LEAVES", "SPRUCE_SAPLING", getOverworldGrow()),
		CRIMSON(LogType.CRIMSON, "NETHER_WART_BLOCK", "CRIMSON_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM")),
		WARPED(LogType.WARPED, "WARPED_WART_BLOCK", "WARPED_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM"));
		
		private final LogType log;
		private final MatData leaf, sapling;
		private List<MatData> growable;

		GaiaTrees(LogType log, String leaf, String sapling, List<String> grow) {
			this.log = log;
			this.leaf = new MatData(leaf);
			this.sapling = new MatData(sapling);
			growable = new ArrayList<MatData>();
			for(String s: grow)
				growable.add(new MatData(s));
		}

		public static GaiaTrees getTree(Material mat) {
			for (GaiaTrees tree : values())
				if (tree.getLog().hasMaterial(mat)) return tree;
			return null;
		}

		private static List<String> getOverworldGrow() {
			return Arrays.asList("GRASS_BLOCK", "GRASS_PATH", "DIRT", "PODZOL");
		}

		public List<MatData> getGrowable() {
			return growable;
		}

		public LogType getLog() {
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
