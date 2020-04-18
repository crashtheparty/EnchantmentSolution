package org.ctp.enchantmentsolution.enums;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;

public enum ItemMoisturizeType {
	UNSMELT(), WATERLOG(), WET(), EXTINGUISH();

	private static List<String> UNSMELTABLE = Arrays.asList("TERRACOTTA", "BLACK_GLAZED_TERRACOTTA", "BLUE_GLAZED_TERRACOTTA", "BROWN_GLAZED_TERRACOTTA", "CYAN_GLAZED_TERRACOTTA", "GRAY_GLAZED_TERRACOTTA", "GREEN_GLAZED_TERRACOTTA", "LIGHT_BLUE_GLAZED_TERRACOTTA", "LIGHT_GRAY_GLAZED_TERRACOTTA", "LIME_GLAZED_TERRACOTTA", "MAGENTA_GLAZED_TERRACOTTA", "ORANGE_GLAZED_TERRACOTTA", "PINK_GLAZED_TERRACOTTA", "PURPLE_GLAZED_TERRACOTTA", "RED_GLAZED_TERRACOTTA", "WHITE_GLAZED_TERRACOTTA", "YELLOW_GLAZED_TERRACOTTA", "CRACKED_STONE_BRICKS", "SMOOTH_QUARTZ", "SMOOTH_RED_SANDSTONE", "SMOOTH_SANDSTONE", "SMOOTH_STONE");
	private static List<String> WET_ITEMS = Arrays.asList("BLACK_CONCRETE_POWDER", "BLUE_CONCRETE_POWDER", "BROWN_CONCRETE_POWDER", "CYAN_CONCRETE_POWDER", "GRAY_CONCRETE_POWDER", "GREEN_CONCRETE_POWDER", "LIGHT_BLUE_CONCRETE_POWDER", "LIGHT_GRAY_CONCRETE_POWDER", "LIME_CONCRETE_POWDER", "MAGENTA_CONCRETE_POWDER", "ORANGE_CONCRETE_POWDER", "PINK_CONCRETE_POWDER", "PURPLE_CONCRETE_POWDER", "RED_CONCRETE_POWDER", "WHITE_CONCRETE_POWDER", "YELLOW_CONCRETE_POWDER");

	public String getName() {
		return name();
	}

	public static ItemMoisturizeType getMoisturizeType(Material mat) {
		BlockData data = Bukkit.createBlockData(mat);
		if (mat.name().equals("CAMPFIRE")) return EXTINGUISH;
		if (data instanceof Waterlogged) return WATERLOG;
		if (UNSMELTABLE.contains(mat.name())) return UNSMELT;
		if (WET_ITEMS.contains(mat.name())) return WET;

		return null;
	}

	public static Material getUnsmelt(Material mat) {
		if (UNSMELTABLE.contains(mat.name())) switch (mat.name()) {
			case "BLACK_GLAZED_TERRACOTTA":
			case "BLUE_GLAZED_TERRACOTTA":
			case "BROWN_GLAZED_TERRACOTTA":
			case "CYAN_GLAZED_TERRACOTTA":
			case "GRAY_GLAZED_TERRACOTTA":
			case "GREEN_GLAZED_TERRACOTTA":
			case "LIGHT_BLUE_GLAZED_TERRACOTTA":
			case "LIGHT_GRAY_GLAZED_TERRACOTTA":
			case "LIME_GLAZED_TERRACOTTA":
			case "MAGENTA_GLAZED_TERRACOTTA":
			case "ORANGE_GLAZED_TERRACOTTA":
			case "PINK_GLAZED_TERRACOTTA":
			case "PURPLE_GLAZED_TERRACOTTA":
			case "RED_GLAZED_TERRACOTTA":
			case "WHITE_GLAZED_TERRACOTTA":
			case "YELLOW_GLAZED_TERRACOTTA":
				return Material.valueOf(mat.name().replace("GLAZED_", ""));
			case "CRACKED_STONE_BRICKS":
				return Material.valueOf("STONE_BRICKS");
			case "SMOOTH_QUARTZ":
				return Material.valueOf("QUARTZ_BLOCK");
			case "SMOOTH_RED_SANDSTONE":
				return Material.valueOf("RED_SANDSTONE");
			case "SMOOTH_SANDSTONE":
				return Material.valueOf("SANDSTONE");
			case "SMOOTH_STONE":
				return Material.valueOf("STONE");
			case "TERRACOTTA":
				return Material.valueOf("CLAY");
			case "POLISHED_BASALT":
				return Material.valueOf("BASALT");
			default:
				break;

		}
		return null;
	}

	public static Material getWet(Material mat) {
		if (WET_ITEMS.contains(mat.name())) return Material.valueOf(mat.name().replace("_POWDER", ""));
		return null;
	}

}
