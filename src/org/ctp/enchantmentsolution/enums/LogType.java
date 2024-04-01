package org.ctp.enchantmentsolution.enums;

import org.bukkit.Material;
import org.ctp.crashapi.data.items.MatData;

public enum LogType {

	OAK(new String[] { "OAK_LOG", "OAK_WOOD", "STRIPPED_OAK_LOG", "STRIPPED_OAK_WOOD" }),
	BIRCH(new String[] { "BIRCH_LOG", "BIRCH_WOOD", "STRIPPED_BIRCH_LOG", "STRIPPED_BIRCH_WOOD" }),
	SPRUCE(new String[] { "SPRUCE_LOG", "SPRUCE_WOOD", "STRIPPED_SPRUCE_LOG", "STRIPPED_SPRUCE_WOOD" }),
	JUNGLE(new String[] { "JUNGLE_LOG", "JUNGLE_WOOD", "STRIPPED_JUNGLE_LOG", "STRIPPED_JUNGLE_WOOD" }),
	DARK_OAK(new String[] { "DARK_OAK_LOG", "DARK_OAK_WOOD", "STRIPPED_DARK_OAK_LOG", "STRIPPED_DARK_OAK_WOOD" }),
	ACACIA(new String[] { "ACACIA_LOG", "ACACIA_WOOD", "STRIPPED_ACACIA_LOG", "STRIPPED_ACACIA_WOOD" }),
	CRIMSON(new String[] { "CRIMSON_STEM", "CRIMSON_HYPHAE", "STRIPPED_CRIMSON_STEM", "STRIPPED_CRIMSON_HYPHAE" }),
	WARPED(new String[] { "WARPED_STEM", "WARPED_HYPHAE", "STRIPPED_WARPED_STEM", "STRIPPED_WARPED_HYPHAE" }),
	MANGROVE(new String[] { "MANGROVE_LOG", "MANGROVE_WOOD", "STRIPPED_MANGROVE_LOG", "STRIPPED_MANGROVE_WOOD" }),
	CHERRY(new String[] { "CHERRY_LOG", "CHERRY_WOOD", "STRIPPED_CHERRY_LOG", "STRIPPED_CHERRY_WOOD" });

	private Material[] types;

	LogType(String[] stringTypes) {
		types = new Material[stringTypes.length];

		for(int i = 0; i < stringTypes.length; i++) {
			MatData data = new MatData(stringTypes[i]);
			if (data.hasMaterial()) types[i] = data.getMaterial();
		}
	}

	public Material[] getMaterials() {
		return types;
	}

	public boolean hasMaterial(Material m) {
		for(Material type: types)
			if (type != null && m == type) return true;
		return false;
	}

	public static LogType fromMaterial(Material m) {
		for(LogType type: values())
			if (type.hasMaterial(m)) return type;

		return null;
	}

}
