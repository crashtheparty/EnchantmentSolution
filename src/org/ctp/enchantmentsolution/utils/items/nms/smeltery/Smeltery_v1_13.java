package org.ctp.enchantmentsolution.utils.items.nms.smeltery;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.nms.ItemBreakType;

public class Smeltery_v1_13 {
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		Material material = null;
		boolean fortune = false;
		ItemBreakType type = ItemBreakType.getType(item.getType());
		switch(block.getType()) {
		case IRON_ORE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.IRON_INGOT;
				fortune = true;
			}
			break;
		case GOLD_ORE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.GOLD_INGOT;
				fortune = true;
			}
			break;
		case SAND:
			material = Material.GLASS;
			break;
		case COBBLESTONE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.STONE;
			}
			break;
		case STONE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.SMOOTH_STONE;
			}
			break;
		case STONE_BRICKS:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.CRACKED_STONE_BRICKS;
			}
			break;
		case NETHERRACK:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.NETHER_BRICK;
			}
			break;
		case CLAY:
			material = Material.TERRACOTTA;
			break;
		case CACTUS:
			material = Material.CACTUS_GREEN;
			break;
		case SEA_PICKLE:
			material = Material.LIME_DYE;
			break;
		case OAK_LOG:
		case BIRCH_LOG:
		case SPRUCE_LOG:
		case JUNGLE_LOG:
		case DARK_OAK_LOG:
		case ACACIA_LOG:
		case OAK_WOOD:
		case BIRCH_WOOD:
		case SPRUCE_WOOD:
		case JUNGLE_WOOD:
		case DARK_OAK_WOOD:
		case ACACIA_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_OAK_WOOD:
		case STRIPPED_BIRCH_WOOD:
		case STRIPPED_SPRUCE_WOOD:
		case STRIPPED_JUNGLE_WOOD:
		case STRIPPED_DARK_OAK_WOOD:
		case STRIPPED_ACACIA_WOOD:
			material = Material.CHARCOAL;
			break;
		case CHORUS_FRUIT:
			material = Material.POPPED_CHORUS_FRUIT;
			break;
		case WET_SPONGE:
			material = Material.SPONGE;
			break;
		case BLACK_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.BLACK_GLAZED_TERRACOTTA;
			}
			break;
		case BLUE_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.BLUE_GLAZED_TERRACOTTA;
			}
			break;
		case BROWN_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.BROWN_GLAZED_TERRACOTTA;
			}
			break;
		case CYAN_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.CYAN_GLAZED_TERRACOTTA;
			}
			break;
		case GRAY_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.GRAY_GLAZED_TERRACOTTA;
			}
			break;
		case GREEN_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.GREEN_GLAZED_TERRACOTTA;
			}
			break;
		case LIGHT_BLUE_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
			}
			break;
		case LIGHT_GRAY_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.LIGHT_GRAY_GLAZED_TERRACOTTA;
			}
			break;
		case LIME_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.LIME_GLAZED_TERRACOTTA;
			}
			break;
		case MAGENTA_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.MAGENTA_GLAZED_TERRACOTTA;
			}
			break;
		case ORANGE_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.ORANGE_GLAZED_TERRACOTTA;
			}
			break;
		case PINK_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.PINK_GLAZED_TERRACOTTA;
			}
			break;
		case PURPLE_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.PURPLE_GLAZED_TERRACOTTA;
			}
			break;
		case RED_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.RED_GLAZED_TERRACOTTA;
			}
			break;
		case WHITE_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.WHITE_GLAZED_TERRACOTTA;
			}
			break;
		case YELLOW_TERRACOTTA:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.YELLOW_GLAZED_TERRACOTTA;
			}
			break;
		case QUARTZ_BLOCK:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.SMOOTH_QUARTZ;
			}
			break;
		case SANDSTONE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.SMOOTH_SANDSTONE;
			}
			break;
		case RED_SANDSTONE:
			if(type != null && type.getBreakTypes().contains(block.getType())) {
				material = Material.SMOOTH_RED_SANDSTONE;
			}
			break;
		default:
			
		}
		int num = 1;
		if(fortune && Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = Enchantments.getLevel(item,
					Enchantment.LOOT_BONUS_BLOCKS) + 2;
			int multiply = (int) (Math.random() * level);
			if(multiply > 1) {
				num *= multiply;
			}
		}
		if(material != null) {
			return new ItemStack(material, num);
		}
		return null;
	}
}
