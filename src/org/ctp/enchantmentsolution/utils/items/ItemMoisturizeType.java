package org.ctp.enchantmentsolution.utils.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;

public enum ItemMoisturizeType{
	UNSMELT(), WATERLOG(), WET(), EXTINGUISH();
	
	private static List<String> UNSMELTABLE = Arrays.asList("TERRACOTTA", "BLACK_GLAZED_TERRACOTTA", "BLUE_GLAZED_TERRACOTTA", "BROWN_GLAZED_TERRACOTTA", 
			"CYAN_GLAZED_TERRACOTTA", "GRAY_GLAZED_TERRACOTTA", "GREEN_GLAZED_TERRACOTTA", "LIGHT_BLUE_GLAZED_TERRACOTTA", "LIGHT_GRAY_GLAZED_TERRACOTTA", 
			"LIME_GLAZED_TERRACOTTA", "MAGENTA_GLAZED_TERRACOTTA", "ORANGE_GLAZED_TERRACOTTA", "PINK_GLAZED_TERRACOTTA", "PURPLE_GLAZED_TERRACOTTA", 
			"RED_GLAZED_TERRACOTTA", "WHITE_GLAZED_TERRACOTTA", "YELLOW_GLAZED_TERRACOTTA", "CRACKED_STONE_BRICKS", "SMOOTH_QUARTZ", "SMOOTH_RED_SANDSTONE", 
			"SMOOTH_SANDSTONE", "SMOOTH_STONE");
	private static List<String> WET_ITEMS = Arrays.asList("BLACK_CONCRETE_POWDER", "BLUE_CONCRETE_POWDER", "BROWN_CONCRETE_POWDER", "CYAN_CONCRETE_POWDER", 
			"GRAY_CONCRETE_POWDER", "GREEN_CONCRETE_POWDER", "LIGHT_BLUE_CONCRETE_POWDER", "LIGHT_GRAY_CONCRETE_POWDER", "LIME_CONCRETE_POWDER", 
			"MAGENTA_CONCRETE_POWDER", "ORANGE_CONCRETE_POWDER", "PINK_CONCRETE_POWDER", "PURPLE_CONCRETE_POWDER", "RED_CONCRETE_POWDER", "WHITE_CONCRETE_POWDER", 
			"YELLOW_CONCRETE_POWDER");

	public String getName() {
		return this.name();
	}
	
	public static ItemMoisturizeType getMoisturizeType(Material mat) {
		BlockData data = Bukkit.createBlockData(mat);
		if(mat.name().equals("CAMPFIRE")) {
			return EXTINGUISH;
		}
		if(data instanceof Waterlogged) {
			return WATERLOG;
		}
		if(UNSMELTABLE.contains(mat.name())) {
			return UNSMELT;
		}
		if(WET_ITEMS.contains(mat.name())) {
			return WET;
		}
		
		return null;
	}
	
	public static Material getUnsmelt(Material mat) {
		if(UNSMELTABLE.contains(mat.name())) {
			switch(mat) {
			case BLACK_GLAZED_TERRACOTTA:
				return Material.valueOf("BLACK_TERRACOTTA");
			case BLUE_GLAZED_TERRACOTTA:
				return Material.valueOf("BLUE_TERRACOTTA");
			case BROWN_GLAZED_TERRACOTTA:
				return Material.valueOf("BROWN_TERRACOTTA");
			case CRACKED_STONE_BRICKS:
				return Material.valueOf("STONE_BRICKS");
			case CYAN_GLAZED_TERRACOTTA:
				return Material.valueOf("CYAN_TERRACOTTA");
			case GRAY_GLAZED_TERRACOTTA:
				return Material.valueOf("GRAY_TERRACOTTA");
			case GREEN_GLAZED_TERRACOTTA:
				return Material.valueOf("GREEN_TERRACOTTA");
			case LIGHT_BLUE_GLAZED_TERRACOTTA:
				return Material.valueOf("LIGHT_BLUE_TERRACOTTA");
			case LIGHT_GRAY_GLAZED_TERRACOTTA:
				return Material.valueOf("LIGHT_GRAY_TERRACOTTA");
			case LIME_GLAZED_TERRACOTTA:
				return Material.valueOf("LIME_TERRACOTTA");
			case MAGENTA_GLAZED_TERRACOTTA:
				return Material.valueOf("MAGENTA_TERRACOTTA");
			case ORANGE_GLAZED_TERRACOTTA:
				return Material.valueOf("ORANGE_TERRACOTTA");
			case PINK_GLAZED_TERRACOTTA:
				return Material.valueOf("PINK_TERRACOTTA");
			case PURPLE_GLAZED_TERRACOTTA:
				return Material.valueOf("PURPLE_TERRACOTTA");
			case RED_GLAZED_TERRACOTTA:
				return Material.valueOf("RED_TERRACOTTA");
			case SMOOTH_QUARTZ:
				return Material.valueOf("QUARTZ_BLOCK");
			case SMOOTH_RED_SANDSTONE:
				return Material.valueOf("RED_SANDSTONE");
			case SMOOTH_SANDSTONE:
				return Material.valueOf("SANDSTONE");
			case SMOOTH_STONE:
				return Material.valueOf("STONE");
			case TERRACOTTA:
				return Material.valueOf("CLAY");
			case WHITE_GLAZED_TERRACOTTA:
				return Material.valueOf("WHITE_TERRACOTTA");
			case YELLOW_GLAZED_TERRACOTTA:
				return Material.valueOf("YELLOW_TERRACOTTA");
			default:
				break;
			
			}
		}
		return null;
	}
	
	public static Material getWet(Material mat) {
		if(WET_ITEMS.contains(mat.name())) {
			switch(mat) {
			case BLACK_CONCRETE_POWDER:
				return Material.valueOf("BLACK_CONCRETE");
			case BLUE_CONCRETE_POWDER:
				return Material.valueOf("BLUE_CONCRETE");
			case BROWN_CONCRETE_POWDER:
				return Material.valueOf("BROWN_CONCRETE");
			case CYAN_CONCRETE_POWDER:
				return Material.valueOf("CYAN_CONCRETE");
			case GRAY_CONCRETE_POWDER:
				return Material.valueOf("GRAY_CONCRETE");
			case GREEN_CONCRETE_POWDER:
				return Material.valueOf("GREEN_CONCRETE");
			case LIGHT_BLUE_CONCRETE_POWDER:
				return Material.valueOf("LIGHT_BLUE_CONCRETE");
			case LIGHT_GRAY_CONCRETE_POWDER:
				return Material.valueOf("LIGHT_GRAY_CONCRETE");
			case LIME_CONCRETE_POWDER:
				return Material.valueOf("LIME_CONCRETE");
			case MAGENTA_CONCRETE_POWDER:
				return Material.valueOf("MAGENTA_CONCRETE");
			case ORANGE_CONCRETE_POWDER:
				return Material.valueOf("ORANGE_CONCRETE");
			case PINK_CONCRETE_POWDER:
				return Material.valueOf("PINK_CONCRETE");
			case PURPLE_CONCRETE_POWDER:
				return Material.valueOf("PURPLE_CONCRETE");
			case RED_CONCRETE_POWDER:
				return Material.valueOf("RED_CONCRETE");
			case WHITE_CONCRETE_POWDER:
				return Material.valueOf("WHITE_CONCRETE");
			case YELLOW_CONCRETE_POWDER:
				return Material.valueOf("YELLOW_CONCRETE");
			default:
				break;
			}
		}
		return null;
	}

}
