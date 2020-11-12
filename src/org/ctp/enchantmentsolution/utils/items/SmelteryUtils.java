package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;

public class SmelteryUtils {

	public static SmelteryMaterial getSmelteryItem(BlockData data, ItemStack from, ItemStack item) {
		String material = null;
		Material f = from.getType();
		ItemBreakType type = ItemBreakType.getType(item.getType());
		switch (data.getMaterial().name()) {
			case "ANCIENT_DEBRIS":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "NETHERITE_SCRAP";
				break;
			case "GILDED_BLACKSTONE":
			case "NETHER_GOLD_ORE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "GOLD_NUGGET";
				break;
			case "IRON_ORE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "IRON_INGOT";
				break;
			case "GOLD_ORE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "GOLD_INGOT";
				break;
			case "SAND":
				material = "GLASS";
				break;
			case "COBBLESTONE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "STONE";
				break;
			case "STONE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) {
					f = Material.COBBLESTONE;
					material = "SMOOTH_STONE";
				}
				break;
			case "STONE_BRICKS":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "CRACKED_STONE_BRICKS";
				break;
			case "NETHERRACK":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "NETHER_BRICK";
				break;
			case "CLAY":
				material = "TERRACOTTA";
				break;
			case "CACTUS":
				if (VersionUtils.getBukkitVersionNumber() > 3) material = "GREEN_DYE";
				else
					material = "CACTUS_GREEN";
				break;
			case "SEA_PICKLE":
				material = "LIME_DYE";
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
				material = "CHARCOAL";
				break;
			case "CHORUS_FRUIT":
				material = "POPPED_CHORUS_FRUIT";
				break;
			case "WET_SPONGE":
				material = "SPONGE";
				break;
			case "BLACK_TERRACOTTA":
			case "BLUE_TERRACOTTA":
			case "BROWN_TERRACOTTA":
			case "CYAN_TERRACOTTA":
			case "GRAY_TERRACOTTA":
			case "GREEN_TERRACOTTA":
			case "LIGHT_BLUE_TERRACOTTA":
			case "LIGHT_GRAY_TERRACOTTA":
			case "LIME_TERRACOTTA":
			case "MAGENTA_TERRACOTTA":
			case "ORANGE_TERRACOTTA":
			case "PINK_TERRACOTTA":
			case "PURPLE_TERRACOTTA":
			case "RED_TERRACOTTA":
			case "WHITE_TERRACOTTA":
			case "YELLOW_TERRACOTTA":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) {
					String[] terra = data.getMaterial().name().split("_");
					String[] newTerra = new String[terra.length + 1];
					for(int i = 0; i < terra.length - 1; i++)
						newTerra[i] = terra[i];
					newTerra[terra.length - 1] = "GLAZED";
					newTerra[terra.length] = terra[terra.length - 1];
					material = String.join("_", newTerra);
				}
				break;
			case "QUARTZ_BLOCK":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "SMOOTH_QUARTZ";
				break;
			case "SANDSTONE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "SMOOTH_SANDSTONE";
				break;
			case "RED_SANDSTONE":
				if (type != null && type.getBreakTypes().contains(data.getMaterial())) material = "SMOOTH_RED_SANDSTONE";
				break;
			default:

		}
		if (material != null) {
			MatData mat = new MatData(material);
			if (mat.getMaterial() != null) return new SmelteryMaterial(new ItemStack(mat.getMaterial()), f, mat.getMaterial());
		}
		return null;
	}

	public static int getFortuneForSmeltery(ItemStack smelted, ItemStack item, Material original) {
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			int level = EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS);
			switch (original.name()) {
				case "ANCIENT_DEBRIS":
					double extraAmount = Math.random() * (level * 0.15);
					double rand = Math.random();
					int amount = 1;
					while (extraAmount >= 1) {
						extraAmount--;
						amount++;
					}
					if (extraAmount > rand) amount++;
					return amount;
				case "IRON_ORE":
				case "GOLD_ORE":
				case "NETHER_GOLD_ORE":
					int multiply = (int) (Math.random() * (level + 2));
					return smelted.getAmount() * multiply;
			}
		}
		return smelted.getAmount();
	}
}
