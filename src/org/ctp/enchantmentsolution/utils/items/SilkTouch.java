package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.ItemBreakType;
import org.ctp.enchantmentsolution.utils.items.helpers.MaterialData;

public class SilkTouch {
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		ItemBreakType type = ItemBreakType.getType(item.getType());
		if(type == null) return null;
		MaterialData data = null;
		switch(block.getType().name()) {
		case "COAL_ORE":
		case "NETHER_QUARTZ_ORE":
		case "STONE":
		case "LAPIS_ORE":
		case "EMERALD_ORE":
		case "DIAMOND_ORE":
		case "REDSTONE_ORE":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData(block.getType().name());
			}
			break;
		case "INFESTED_STONE":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData("STONE");
			}
			return null;
		case "INFESTED_COBBLESTONE":
			if(type.getBreakTypes().contains(block.getType())) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
					data = new MaterialData("STONE");
				}
				data = new MaterialData("COBBLESTONE");
			}
			return null;
		case "INFESTED_STONE_BRICKS":
			if(type.getBreakTypes().contains(block.getType())) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
					data = new MaterialData("CRACKED_STONE_BRICKS");
				}
				data = new MaterialData("STONE_BRICKS");
			}
			return null;
		case "INFESTED_CRACKED_STONE_BRICKS":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData("CRACKED_STONE_BRICKS");
			}
			return null;
		case "SNOW_BLOCK":
		case "SNOW":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData(block.getType().name());
			}
			return null;
		case "CAMPFIRE":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData("CAMPFIRE");
			}
			return null;
		case "BOOKSHELF":
		case "CLAY":
		case "ENDER_CHEST":
		case "GLASS":
		case "GLASS_PANE":
		case "GLOWSTONE":
		case "GRASS_BLOCK":
		case "MYCELIUM":
		case "BROWN_MUSHROOM_BLOCK":
		case "RED_MUSHROOM_BLOCK":
		case "MUSHROOM_STEM":
		case "MELON":
		case "PODZOL":
		case "SEA_LANTERN":
		case "ICE":
		case "PACKED_ICE":
		case "BLUE_ICE":
		case "ACACIA_LEAVES":
		case "BIRCH_LEAVES":
		case "DARK_OAK_LEAVES":
		case "JUNGLE_LEAVES":
		case "OAK_LEAVES":
		case "SPRUCE_LEAVES":
		case "BLACK_STAINED_GLASS":
		case "BLUE_STAINED_GLASS":
		case "BROWN_STAINED_GLASS":
		case "CYAN_STAINED_GLASS":
		case "GRAY_STAINED_GLASS":
		case "GREEN_STAINED_GLASS":
		case "LIGHT_BLUE_STAINED_GLASS":
		case "LIGHT_GRAY_STAINED_GLASS":
		case "LIME_STAINED_GLASS":
		case "MAGENTA_STAINED_GLASS":
		case "ORANGE_STAINED_GLASS":
		case "PINK_STAINED_GLASS":
		case "PURPLE_STAINED_GLASS":
		case "RED_STAINED_GLASS":
		case "WHITE_STAINED_GLASS":
		case "YELLOW_STAINED_GLASS":
		case "BLACK_STAINED_GLASS_PANE":
		case "BLUE_STAINED_GLASS_PANE":
		case "BROWN_STAINED_GLASS_PANE":
		case "CYAN_STAINED_GLASS_PANE":
		case "GRAY_STAINED_GLASS_PANE":
		case "GREEN_STAINED_GLASS_PANE":
		case "LIGHT_BLUE_STAINED_GLASS_PANE":
		case "LIGHT_GRAY_STAINED_GLASS_PANE":
		case "LIME_STAINED_GLASS_PANE":
		case "MAGENTA_STAINED_GLASS_PANE":
		case "ORANGE_STAINED_GLASS_PANE":
		case "PINK_STAINED_GLASS_PANE":
		case "PURPLE_STAINED_GLASS_PANE":
		case "RED_STAINED_GLASS_PANE":
		case "WHITE_STAINED_GLASS_PANE":
		case "YELLOW_STAINED_GLASS_PANE":
		case "BRAIN_CORAL":
		case "BRAIN_CORAL_FAN":
		case "BUBBLE_CORAL":
		case "BUBBLE_CORAL_FAN":
		case "FIRE_CORAL":
		case "FIRE_CORAL_FAN":
		case "HORN_CORAL":
		case "HORN_CORAL_FAN":
		case "TUBE_CORAL":
		case "TUBE_CORAL_FAN":
			data = new MaterialData(block.getType().name());
		case "BRAIN_CORAL_WALL_FAN":
			data = new MaterialData("BRAIN_CORAL_FAN");
		case "BUBBLE_CORAL_WALL_FAN":
			data = new MaterialData("BUBBLE_CORAL_FAN");
		case "FIRE_CORAL_WALL_FAN":
			data = new MaterialData("FIRE_CORAL_FAN");
		case "HORN_CORAL_WALL_FAN":
			data = new MaterialData("HORN_CORAL_FAN");
		case "TUBE_CORAL_WALL_FAN":
			data = new MaterialData("TUBE_CORAL_FAN");
		case "BRAIN_CORAL_BLOCK":
		case "BUBBLE_CORAL_BLOCK":
		case "FIRE_CORAL_BLOCK":
		case "HORN_CORAL_BLOCK":
		case "TUBE_CORAL_BLOCK":
			if(type.getBreakTypes().contains(block.getType())) {
				data = new MaterialData(block.getType().name());
			}
			break;
		case "TURTLE_EGG":
			data = new MaterialData("TURTLE_EGG");
			break;
		default:
			break;
		}
		if(data != null && data.getMaterial() != null) {
			return new ItemStack(data.getMaterial());
		}
		return null;
	}
}
