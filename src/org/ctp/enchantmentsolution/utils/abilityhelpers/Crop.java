package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public enum Crop {
	WHEAT("WHEAT", "WHEAT_SEEDS"), CARROTS("CARROT", "CARROT"), POTATOES("POTATO", "POTATO"), NETHER_WART("NETHER_WART", "NETHER_WART"),
	BEETROOTS("BEETROOT", "BEETROOT_SEEDS"), COCOA_BEANS("COCOA_BEANS", "COCOA_BEANS");

	private final MatData block, drop, seed;

	Crop(String drop, String seed) {
		block = new MatData(name());
		this.drop = new MatData(drop);
		this.seed = new MatData(seed);
	}

	public MatData getBlock() {
		return block;
	}

	public MatData getDrop() {
		return drop;
	}

	public MatData getSeed() {
		return seed;
	}

	public static Crop getCrop(Material mat) {
		for(Crop c: values())
			if (c.getBlock().getMaterial() == mat) return c;
		return null;
	}

	public static boolean hasBlock(Material mat) {
		for(Crop c: values())
			if (c.getBlock().getMaterial() == mat) return true;
		return false;
	}
}
