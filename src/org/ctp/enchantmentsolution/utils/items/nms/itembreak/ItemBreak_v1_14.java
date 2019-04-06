package org.ctp.enchantmentsolution.utils.items.nms.itembreak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.items.nms.ItemBreakType;

public enum ItemBreak_v1_14 implements ItemBreakType{
	DIAMOND_AXE(Material.DIAMOND_AXE), DIAMOND_SHOVEL(Material.DIAMOND_SHOVEL), DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE), 
	IRON_AXE(Material.IRON_AXE), IRON_SHOVEL(Material.IRON_SHOVEL), IRON_PICKAXE(Material.IRON_PICKAXE), 
	GOLDEN_AXE(Material.GOLDEN_AXE), GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL), GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE), 
	STONE_AXE(Material.STONE_AXE), STONE_SHOVEL(Material.STONE_SHOVEL), STONE_PICKAXE(Material.STONE_PICKAXE), 
	WOODEN_AXE(Material.WOODEN_AXE), WOODEN_SHOVEL(Material.WOODEN_SHOVEL), WOODEN_PICKAXE(Material.WOODEN_PICKAXE);
	
	private Material material;
	private List<Material> breakTypes;
	
	ItemBreak_v1_14(Material material) {
		this.material = material;
		this.breakTypes = getItemBreakTypes(material);
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public List<Material> getBreakTypes(){
		return breakTypes;
	}
	
	public static ItemBreakType getType(Material type) {
		for(ItemBreak_v1_13 breakType : ItemBreak_v1_13.values()) {
			if(breakType.getMaterial().equals(type)) {
				return breakType;
			}
		}
		return null;
	}
	
	public static List<Material> allBreakTypes(){
		List<Material> itemTypes = new ArrayList<Material>();
		for(ItemBreak_v1_13 type : ItemBreak_v1_13.values()) {
			itemTypes.addAll(type.getBreakTypes());
		}
		return itemTypes;
	}
	
	private List<Material> getItemBreakTypes(Material type){
		List<Material> itemTypes = new ArrayList<Material>();
		switch(type) {
		case DIAMOND_PICKAXE:
			itemTypes.addAll(getItemBreakTypes(Material.IRON_PICKAXE));
			itemTypes.addAll(Arrays.asList(Material.OBSIDIAN));
			return itemTypes;
		case GOLDEN_PICKAXE:
			itemTypes.addAll(getItemBreakTypes(Material.WOODEN_PICKAXE));
			return itemTypes;
		case IRON_PICKAXE:
			itemTypes.addAll(getItemBreakTypes(Material.STONE_PICKAXE));
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_ORE,
					Material.EMERALD_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE));
			return itemTypes;
		case STONE_PICKAXE:
			itemTypes.addAll(getItemBreakTypes(Material.WOODEN_PICKAXE));
			itemTypes.addAll(Arrays.asList(Material.IRON_BLOCK, Material.LAPIS_BLOCK, Material.IRON_ORE, Material.LAPIS_ORE));
			return itemTypes;
		case DIAMOND_AXE:
		case GOLDEN_AXE:
		case IRON_AXE:
		case STONE_AXE:
			itemTypes.addAll(Arrays.asList(Material.COCOA, Material.JACK_O_LANTERN, Material.PUMPKIN, Material.MELON, Material.BOOKSHELF, 
					Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE, 
					Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE, Material.OAK_FENCE_GATE, 
					Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE, Material.ACACIA_LOG, Material.ACACIA_PLANKS, Material.BIRCH_LOG, Material.BIRCH_PLANKS, 
					Material.DARK_OAK_LOG, Material.DARK_OAK_PLANKS, Material.JUNGLE_LOG, Material.JUNGLE_PLANKS, Material.OAK_LOG, Material.OAK_PLANKS, 
					Material.SPRUCE_LOG, Material.SPRUCE_PLANKS, Material.ACACIA_WOOD, Material.BIRCH_WOOD, Material.DARK_OAK_WOOD, Material.JUNGLE_WOOD, 
					Material.OAK_WOOD, Material.SPRUCE_WOOD, Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_DARK_OAK_LOG, 
					Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_ACACIA_WOOD, Material.STRIPPED_BIRCH_WOOD, 
					Material.STRIPPED_DARK_OAK_WOOD, Material.STRIPPED_JUNGLE_WOOD, Material.STRIPPED_OAK_WOOD, Material.STRIPPED_SPRUCE_WOOD, 
					Material.BROWN_MUSHROOM_BLOCK, Material.RED_MUSHROOM_BLOCK, Material.LADDER));
			return itemTypes;
		case WOODEN_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.ICE, Material.PACKED_ICE, Material.FROSTED_ICE, Material.BLUE_ICE, Material.REDSTONE_BLOCK, Material.IRON_BARS, 
					Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL, Material.RAIL, Material.ANDESITE, Material.COAL_BLOCK, 
					Material.QUARTZ_BLOCK, Material.BRICKS, Material.COAL_ORE, Material.COBBLESTONE, Material.COBBLESTONE_WALL, Material.BLACK_CONCRETE, 
					Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.CYAN_CONCRETE, Material.GRAY_CONCRETE, Material.GREEN_CONCRETE, 
					Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.LIME_CONCRETE, Material.MAGENTA_CONCRETE, Material.ORANGE_CONCRETE, 
					Material.PINK_CONCRETE, Material.PURPLE_CONCRETE, Material.RED_CONCRETE, Material.WHITE_CONCRETE, Material.YELLOW_CONCRETE, 
					Material.DARK_PRISMARINE, Material.DIORITE, Material.END_STONE, Material.END_STONE_BRICKS, Material.BLACK_GLAZED_TERRACOTTA, 
					Material.BLUE_GLAZED_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, 
					Material.GREEN_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA, 
					Material.MAGENTA_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, 
					Material.RED_GLAZED_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.GRANITE, Material.MOSSY_COBBLESTONE, 
					Material.MOSSY_COBBLESTONE_WALL, Material.NETHER_BRICK, Material.NETHER_BRICK_FENCE, Material.NETHER_QUARTZ_ORE, Material.NETHERRACK, 
					Material.POLISHED_ANDESITE, Material.POLISHED_DIORITE, Material.POLISHED_GRANITE, Material.PRISMARINE, Material.PRISMARINE_BRICKS, 
					Material.RED_SANDSTONE, Material.SANDSTONE, Material.BLACK_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, 
					Material.CYAN_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, 
					Material.LIME_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.PINK_TERRACOTTA, Material.PURPLE_TERRACOTTA, 
					Material.RED_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.STONE, Material.STONE_BRICKS, Material.MOSSY_STONE_BRICKS,
					Material.CRACKED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS, Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_COBBLESTONE,
					Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS, Material.INFESTED_STONE, Material.INFESTED_STONE_BRICKS,
					Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.FIRE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK,
					Material.CAMPFIRE, Material.SMOOTH_QUARTZ, Material.MAGMA_BLOCK, Material.SMOOTH_STONE, Material.SMOOTH_RED_SANDSTONE, Material.SMOOTH_SANDSTONE));
			return itemTypes;
		case DIAMOND_SHOVEL:
		case IRON_SHOVEL:
		case GOLDEN_SHOVEL:
		case STONE_SHOVEL:
		case WOODEN_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.SNOW, Material.SNOW_BLOCK, Material.CLAY, Material.COARSE_DIRT, Material.DIRT, Material.FARMLAND, 
					Material.GRASS_BLOCK, Material.GRAVEL, Material.MYCELIUM, Material.PODZOL, Material.RED_SAND, Material.SAND, Material.SOUL_SAND, 
					Material.BLACK_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER, Material.BROWN_CONCRETE_POWDER, Material.CYAN_CONCRETE_POWDER, 
					Material.GRAY_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE_POWDER, 
					Material.LIME_CONCRETE_POWDER, Material.MAGENTA_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, Material.PINK_CONCRETE_POWDER, 
					Material.PURPLE_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER, Material.WHITE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER));
			return itemTypes;
		default:
			break;
		}
		return null;
	}
}
