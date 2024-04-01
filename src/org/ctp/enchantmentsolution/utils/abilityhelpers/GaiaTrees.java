package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.ctp.crashapi.data.items.MatData;
import org.ctp.enchantmentsolution.enums.LogType;

public enum GaiaTrees {
	ACACIA(LogType.ACACIA, Arrays.asList("ACACIA_LEAVES"), "ACACIA_SAPLING", getOverworldGrow()),
	BIRCH(LogType.BIRCH, Arrays.asList("BIRCH_LEAVES"), "BIRCH_SAPLING", getOverworldGrow()),
	DARK_OAK(LogType.DARK_OAK, Arrays.asList("DARK_OAK_LEAVES"), "DARK_OAK_SAPLING", getOverworldGrow()),
	JUNGLE(LogType.JUNGLE, Arrays.asList("JUNGLE_LEAVES"), "JUNGLE_SAPLING", getOverworldGrow()),
	OAK(LogType.OAK, Arrays.asList("OAK_LEAVES", "AZALEA_LEAVES", "FLOWERING_AZALEA_LEAVES"), "OAK_SAPLING", getOverworldGrow()),
	SPRUCE(LogType.SPRUCE, Arrays.asList("SPRUCE_LEAVES"), "SPRUCE_SAPLING", getOverworldGrow()),
	CRIMSON(LogType.CRIMSON, Arrays.asList("NETHER_WART_BLOCK"), "CRIMSON_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM")),
	WARPED(LogType.WARPED, Arrays.asList("WARPED_WART_BLOCK"), "WARPED_FUNGUS", Arrays.asList("CRIMSON_NYLIUM", "WARPED_NYLIUM")), 
	MANGROVE(LogType.MANGROVE, Arrays.asList("MANGROVE_LEAVES"), "MANGROVE_PROPAGULE", getOverworldGrow()),
	CHERRY(LogType.CHERRY, Arrays.asList("CHERRY_LEAVES"), "CHERRY_SAPLING", getOverworldGrow());

	private final LogType log;
	private final MatData sapling;
	private List<MatData> leaves, growable;

	GaiaTrees(LogType log, List<String> leaf, String sapling, List<String> grow) {
		this.log = log;
		leaves = new ArrayList<MatData>();
		for(String s: leaf)
			leaves.add(new MatData(s));
		this.sapling = new MatData(sapling);
		growable = new ArrayList<MatData>();
		for(String s: grow)
			growable.add(new MatData(s));
	}

	public static GaiaTrees getTree(Material mat) {
		for(GaiaTrees tree: values())
			if (tree.getLog().hasMaterial(mat)) return tree;
		return null;
	}

	private static List<String> getOverworldGrow() {
		return Arrays.asList("GRASS_BLOCK", "GRASS_PATH", "DIRT", "PODZOL", "MUD");
	}

	public List<MatData> getGrowable() {
		return growable;
	}

	public LogType getLog() {
		return log;
	}

	public List<MatData> getLeaves() {
		return leaves;
	}

	public MatData getSapling() {
		return sapling;
	}
}