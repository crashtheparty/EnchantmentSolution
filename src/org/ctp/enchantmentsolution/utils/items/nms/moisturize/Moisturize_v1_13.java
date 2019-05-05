package org.ctp.enchantmentsolution.utils.items.nms.moisturize;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.ctp.enchantmentsolution.utils.items.nms.ItemMoisturizeType;

public enum Moisturize_v1_13 implements ItemMoisturizeType{
	UNSMELT(), WATERLOG(), WET();
	
	private static List<Material> UNSMELTABLE = Arrays.asList(Material.TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, 
			Material.BROWN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA, 
			Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, 
			Material.ORANGE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, 
			Material.WHITE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.CRACKED_STONE_BRICKS, Material.SMOOTH_QUARTZ, Material.SMOOTH_RED_SANDSTONE,
			Material.SMOOTH_SANDSTONE, Material.SMOOTH_STONE);
	private static List<Material> WET_ITEMS = Arrays.asList(Material.BLACK_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER, Material.BROWN_CONCRETE_POWDER, 
			Material.CYAN_CONCRETE_POWDER, Material.GRAY_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, 
			Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIME_CONCRETE_POWDER, Material.MAGENTA_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, 
			Material.PINK_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER, Material.WHITE_CONCRETE_POWDER, 
			Material.YELLOW_CONCRETE_POWDER);

	@Override
	public String getName() {
		return this.name();
	}
	
	public static ItemMoisturizeType getMoisturizeType(Material mat) {
		BlockData data = Bukkit.createBlockData(mat);
		if(data instanceof Waterlogged) {
			return WATERLOG;
		}
		if(UNSMELTABLE.contains(mat)) {
			return UNSMELT;
		}
		if(WET_ITEMS.contains(mat)) {
			return WET;
		}
		
		return null;
	}
	
	public static Material getUnsmelt(Material mat) {
		if(UNSMELTABLE.contains(mat)) {
			switch(mat) {
			case BLACK_GLAZED_TERRACOTTA:
				return Material.BLACK_TERRACOTTA;
			case BLUE_GLAZED_TERRACOTTA:
				return Material.BLUE_TERRACOTTA;
			case BROWN_GLAZED_TERRACOTTA:
				return Material.BROWN_TERRACOTTA;
			case CRACKED_STONE_BRICKS:
				return Material.STONE_BRICKS;
			case CYAN_GLAZED_TERRACOTTA:
				return Material.CYAN_TERRACOTTA;
			case GRAY_GLAZED_TERRACOTTA:
				return Material.GRAY_TERRACOTTA;
			case GREEN_GLAZED_TERRACOTTA:
				return Material.GREEN_TERRACOTTA;
			case LIGHT_BLUE_GLAZED_TERRACOTTA:
				return Material.LIGHT_BLUE_TERRACOTTA;
			case LIGHT_GRAY_GLAZED_TERRACOTTA:
				return Material.LIGHT_GRAY_TERRACOTTA;
			case LIME_GLAZED_TERRACOTTA:
				return Material.LIME_TERRACOTTA;
			case MAGENTA_GLAZED_TERRACOTTA:
				return Material.MAGENTA_TERRACOTTA;
			case ORANGE_GLAZED_TERRACOTTA:
				return Material.ORANGE_TERRACOTTA;
			case PINK_GLAZED_TERRACOTTA:
				return Material.PINK_TERRACOTTA;
			case PURPLE_GLAZED_TERRACOTTA:
				return Material.PURPLE_TERRACOTTA;
			case RED_GLAZED_TERRACOTTA:
				return Material.RED_TERRACOTTA;
			case SMOOTH_QUARTZ:
				return Material.QUARTZ_BLOCK;
			case SMOOTH_RED_SANDSTONE:
				return Material.RED_SANDSTONE;
			case SMOOTH_SANDSTONE:
				return Material.SANDSTONE;
			case SMOOTH_STONE:
				return Material.STONE;
			case TERRACOTTA:
				return Material.CLAY;
			case WHITE_GLAZED_TERRACOTTA:
				return Material.WHITE_TERRACOTTA;
			case YELLOW_GLAZED_TERRACOTTA:
				return Material.YELLOW_TERRACOTTA;
			default:
				break;
			
			}
		}
		return null;
	}
	
	public static Material getWet(Material mat) {
		if(WET_ITEMS.contains(mat)) {
			switch(mat) {
			case BLACK_CONCRETE_POWDER:
				return Material.BLACK_CONCRETE;
			case BLUE_CONCRETE_POWDER:
				return Material.BLUE_CONCRETE;
			case BROWN_CONCRETE_POWDER:
				return Material.BROWN_CONCRETE;
			case CYAN_CONCRETE_POWDER:
				return Material.CYAN_CONCRETE;
			case GRAY_CONCRETE_POWDER:
				return Material.GRAY_CONCRETE;
			case GREEN_CONCRETE_POWDER:
				return Material.GREEN_CONCRETE;
			case LIGHT_BLUE_CONCRETE_POWDER:
				return Material.LIGHT_BLUE_CONCRETE;
			case LIGHT_GRAY_CONCRETE_POWDER:
				return Material.LIGHT_GRAY_CONCRETE;
			case LIME_CONCRETE_POWDER:
				return Material.LIME_CONCRETE;
			case MAGENTA_CONCRETE_POWDER:
				return Material.MAGENTA_CONCRETE;
			case ORANGE_CONCRETE_POWDER:
				return Material.ORANGE_CONCRETE;
			case PINK_CONCRETE_POWDER:
				return Material.PINK_CONCRETE;
			case PURPLE_CONCRETE_POWDER:
				return Material.PURPLE_CONCRETE;
			case RED_CONCRETE_POWDER:
				return Material.RED_CONCRETE;
			case WHITE_CONCRETE_POWDER:
				return Material.WHITE_CONCRETE;
			case YELLOW_CONCRETE_POWDER:
				return Material.YELLOW_CONCRETE;
			default:
				break;
			}
		}
		return null;
	}

}
