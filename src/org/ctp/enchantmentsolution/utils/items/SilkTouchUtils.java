package org.ctp.enchantmentsolution.utils.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemBreakType;

public class SilkTouchUtils {

	public static List<String> SILK_TOUCH_BLOCKS = Arrays.asList("COAL_ORE", "GILDED_BLACSTONE", "NETHER_QUARTZ_ORE", "STONE", "LAPIS_ORE", "EMERALD_ORE", "DIAMOND_ORE", "REDSTONE_ORE", "SNOW_BLOCK", "SNOW", "CAMPFIRE", "SOUL_CAMPFIRE", "NETHER_GOLD_ORE", "BOOKSHELF", "CLAY", "ENDER_CHEST", "GLASS", "GLASS_PANE", "GLOWSTONE", "GRASS_BLOCK", "MYCELIUM", "BROWN_MUSHROOM_BLOCK", "RED_MUSHROOM_BLOCK", "MUSHROOM_STEM", "MELON", "PODZOL", "SEA_LANTERN", "ICE", "PACKED_ICE", "BLUE_ICE", "ACACIA_LEAVES", "BIRCH_LEAVES", "DARK_OAK_LEAVES", "JUNGLE_LEAVES", "OAK_LEAVES", "SPRUCE_LEAVES", "BLACK_STAINED_GLASS", "BLUE_STAINED_GLASS", "BROWN_STAINED_GLASS", "CYAN_STAINED_GLASS", "GRAY_STAINED_GLASS", "GREEN_STAINED_GLASS", "LIGHT_BLUE_STAINED_GLASS", "LIGHT_GRAY_STAINED_GLASS", "LIME_STAINED_GLASS", "MAGENTA_STAINED_GLASS", "ORANGE_STAINED_GLASS", "PINK_STAINED_GLASS", "PURPLE_STAINED_GLASS", "RED_STAINED_GLASS", "WHITE_STAINED_GLASS", "YELLOW_STAINED_GLASS", "BLACK_STAINED_GLASS_PANE", "BLUE_STAINED_GLASS_PANE", "BROWN_STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE", "GRAY_STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE", "LIGHT_BLUE_STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", "LIME_STAINED_GLASS_PANE", "MAGENTA_STAINED_GLASS_PANE", "ORANGE_STAINED_GLASS_PANE", "PINK_STAINED_GLASS_PANE", "PURPLE_STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE", "WHITE_STAINED_GLASS_PANE", "YELLOW_STAINED_GLASS_PANE", "BRAIN_CORAL", "BRAIN_CORAL_FAN", "BUBBLE_CORAL", "BUBBLE_CORAL_FAN", "FIRE_CORAL", "FIRE_CORAL_FAN", "HORN_CORAL", "HORN_CORAL_FAN", "TUBE_CORAL", "TUBE_CORAL_FAN", "BRAIN_CORAL_WALL_FAN", "BUBBLE_CORAL_WALL_FAN", "FIRE_CORAL_WALL_FAN", "HORN_CORAL_WALL_FAN", "TUBE_CORAL_WALL_FAN", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK", "TUBE_CORAL_BLOCK", "BEE_NEST", "BEEHIVE");

	public static ItemStack getSilkTouchItem(Block block, ItemStack item) {
		ItemBreakType type = ItemBreakType.getType(item.getType());
		switch (block.getType().name()) {
			case "COAL_ORE":
			case "NETHER_QUARTZ_ORE":
			case "STONE":
			case "LAPIS_ORE":
			case "EMERALD_ORE":
			case "DIAMOND_ORE":
			case "REDSTONE_ORE":
			case "SNOW_BLOCK":
			case "SNOW":
			case "CAMPFIRE":
			case "SOUL_CAMPFIRE":
			case "NETHER_GOLD_ORE":
			case "GILDED_BLACKSTONE":
			case "CRIMSON_NYLIUM":
			case "WARPED_NYLIUM":
				if (type != null && type.getBreakTypes().contains(block.getType())) return new ItemStack(block.getType());
				break;
			case "INFESTED_STONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) return new ItemStack(Material.STONE);
				return null;
			case "INFESTED_COBBLESTONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) {
					if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) return new ItemStack(Material.STONE);
					return new ItemStack(Material.COBBLESTONE);
				}
				return null;
			case "INFESTED_STONE_BRICKS":
				if (type != null && type.getBreakTypes().contains(block.getType())) {
					if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) return new ItemStack(Material.CRACKED_STONE_BRICKS);
					return new ItemStack(Material.STONE_BRICKS);
				}
				return null;
			case "INFESTED_CRACKED_STONE_BRICKS":
				if (type != null && type.getBreakTypes().contains(block.getType())) return new ItemStack(Material.CRACKED_STONE_BRICKS);
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
				return new ItemStack(block.getType());
			case "BRAIN_CORAL_WALL_FAN":
				return new ItemStack(Material.BRAIN_CORAL_FAN);
			case "BUBBLE_CORAL_WALL_FAN":
				return new ItemStack(Material.BUBBLE_CORAL_FAN);
			case "FIRE_CORAL_WALL_FAN":
				return new ItemStack(Material.FIRE_CORAL_FAN);
			case "HORN_CORAL_WALL_FAN":
				return new ItemStack(Material.HORN_CORAL_FAN);
			case "TUBE_CORAL_WALL_FAN":
				return new ItemStack(Material.TUBE_CORAL_FAN);
			case "BRAIN_CORAL_BLOCK":
			case "BUBBLE_CORAL_BLOCK":
			case "FIRE_CORAL_BLOCK":
			case "HORN_CORAL_BLOCK":
			case "TUBE_CORAL_BLOCK":
				if (type != null && type.getBreakTypes().contains(block.getType())) return new ItemStack(block.getType());
				break;
			case "BEE_NEST":
			case "BEEHIVE":
				if (type != null && type.getBreakTypes().contains(block.getType())) {
					ItemStack drop = new ItemStack(block.getType());
					BlockStateMeta im = (BlockStateMeta) drop;
					Beehive hive = (Beehive) block.getState();
					im.setBlockState(hive);
					drop.setItemMeta(im);
					return drop;
				}
				break;
			default:
				break;
		}
		return null;
	}
}
