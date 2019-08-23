package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.ItemBreakType;
import org.ctp.enchantmentsolution.utils.items.helpers.MaterialData;

public class Smeltery {
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		MaterialData materialData = null;
		boolean fortune = false;
		ItemBreakType type = ItemBreakType.getType(item.getType());
		if(type == null) {
			return null;
		}
		switch(block.getType().name()) {
		case "IRON_ORE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("IRON_INGOT");
				fortune = true;
			}
			break;
		case "GOLD_ORE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("GOLD_INGOT");
				fortune = true;
			}
			break;
		case "SAND":
			materialData = new MaterialData("GLASS");
			break;
		case "COBBLESTONE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("STONE");
			}
			break;
		case "STONE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("SMOOTH_STONE");
			}
			break;
		case "STONE_BRICKS":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("CRACKED_STONE_BRICKS");
			}
			break;
		case "NETHERRACK":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("NETHER_BRICK");
			}
			break;
		case "CLAY":
			materialData = new MaterialData("TERRACOTTA");
			break;
		case "CACTUS":
			materialData = new MaterialData("GREEN_DYE");
			if(materialData.getMaterial() == null) {
				materialData = new MaterialData("CACTUS_GREEN");
			}
			break;
		case "SEA_PICKLE":
			materialData = new MaterialData("LIME_DYE");
			break;
		case "OAK_LOG":
		case "BIRCH_LOG":
		case "SPRUCE_LOG":
		case "JUNGLE_LOG":
		case "DARK_OAK_LOG":
		case "ACACIA_LOG":
		case "OAK_WOOD":
		case "BIRCH_WOOD":
		case "SPRUCE_WOOD":
		case "JUNGLE_WOOD":
		case "DARK_OAK_WOOD":
		case "ACACIA_WOOD":
		case "STRIPPED_OAK_LOG":
		case "STRIPPED_BIRCH_LOG":
		case "STRIPPED_SPRUCE_LOG":
		case "STRIPPED_JUNGLE_LOG":
		case "STRIPPED_DARK_OAK_LOG":
		case "STRIPPED_ACACIA_LOG":
		case "STRIPPED_OAK_WOOD":
		case "STRIPPED_BIRCH_WOOD":
		case "STRIPPED_SPRUCE_WOOD":
		case "STRIPPED_JUNGLE_WOOD":
		case "STRIPPED_DARK_OAK_WOOD":
		case "STRIPPED_ACACIA_WOOD":
			materialData = new MaterialData("CHARCOAL");
			break;
		case "CHORUS_FRUIT":
			materialData = new MaterialData("POPPED_CHORUS_FRUIT");
			break;
		case "WET_SPONGE":
			materialData = new MaterialData("SPONGE");
			break;
		case "BLACK_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("BLACK_GLAZED_TERRACOTTA");
			}
			break;
		case "BLUE_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("BLUE_GLAZED_TERRACOTTA");
			}
			break;
		case "BROWN_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("BROWN_GLAZED_TERRACOTTA");
			}
			break;
		case "CYAN_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("CYAN_GLAZED_TERRACOTTA");
			}
			break;
		case "GRAY_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("GRAY_GLAZED_TERRACOTTA");
			}
			break;
		case "GREEN_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("GREEN_GLAZED_TERRACOTTA");
			}
			break;
		case "LIGHT_BLUE_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("LIGHT_BLUE_GLAZED_TERRACOTTA");
			}
			break;
		case "LIGHT_GRAY_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("LIGHT_GRAY_GLAZED_TERRACOTTA");
			}
			break;
		case "LIME_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("LIME_GLAZED_TERRACOTTA");
			}
			break;
		case "MAGENTA_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("MAGENTA_GLAZED_TERRACOTTA");
			}
			break;
		case "ORANGE_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("ORANGE_GLAZED_TERRACOTTA");
			}
			break;
		case "PINK_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("PINK_GLAZED_TERRACOTTA");
			}
			break;
		case "PURPLE_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("PURPLE_GLAZED_TERRACOTTA");
			}
			break;
		case "RED_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("RED_GLAZED_TERRACOTTA");
			}
			break;
		case "WHITE_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("WHITE_GLAZED_TERRACOTTA");
			}
			break;
		case "YELLOW_TERRACOTTA":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("YELLOW_GLAZED_TERRACOTTA");
			}
			break;
		case "QUARTZ_BLOCK":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("SMOOTH_QUARTZ");
			}
			break;
		case "SANDSTONE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("SMOOTH_SANDSTONE");
			}
			break;
		case "RED_SANDSTONE":
			if(type.getBreakTypes().contains(block.getType())) {
				materialData = new MaterialData("SMOOTH_RED_SANDSTONE");
			}
			break;
		default:
			
		}
		if(materialData != null && materialData.getMaterial() != null) {
			int num = 1;
			if(fortune && Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
				int level = Enchantments.getLevel(item,
						Enchantment.LOOT_BONUS_BLOCKS) + 2;
				int multiply = (int) (Math.random() * level);
				if(multiply > 1) {
					num *= multiply;
				}
			}
			return new ItemStack(materialData.getMaterial(), num);
		}
		return null;
	}
}
