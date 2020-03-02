package org.ctp.enchantmentsolution.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public enum ItemRepairType {
	DIAMOND_AXE(), DIAMOND_SWORD(), DIAMOND_SHOVEL(), DIAMOND_PICKAXE(), DIAMOND_HOE(), DIAMOND_HELMET(),
	DIAMOND_CHESTPLATE(), DIAMOND_LEGGINGS(), DIAMOND_BOOTS(), IRON_AXE(), IRON_SWORD(), IRON_SHOVEL(), IRON_PICKAXE(),
	IRON_HOE(), IRON_HELMET(), IRON_CHESTPLATE(), IRON_LEGGINGS(), IRON_BOOTS(), GOLDEN_AXE(), GOLDEN_SWORD(),
	GOLDEN_SHOVEL(), GOLDEN_PICKAXE(), GOLDEN_HOE(), GOLDEN_HELMET(), GOLDEN_CHESTPLATE(), GOLDEN_LEGGINGS(),
	GOLDEN_BOOTS(), STONE_AXE(), STONE_SWORD(), STONE_SHOVEL(), STONE_PICKAXE(), STONE_HOE(), CHAINMAIL_HELMET(),
	CHAINMAIL_CHESTPLATE(), CHAINMAIL_LEGGINGS(), CHAINMAIL_BOOTS(), WOODEN_AXE(), WOODEN_SWORD(), WOODEN_SHOVEL(),
	WOODEN_PICKAXE(), WOODEN_HOE(), LEATHER_HELMET(), LEATHER_CHESTPLATE(), LEATHER_LEGGINGS(), LEATHER_BOOTS(),
	ELYTRA(), BOW(), FISHING_ROD(), BOOK(), ENCHANTED_BOOK(), TRIDENT(), SHEARS(), FLINT_AND_STEEL(), CROSSBOW(),
	SHIELD(), TURTLE_HELMET(), CARROT_ON_A_STICK(), BLACK_SHULKER_BOX(), BLUE_SHULKER_BOX(), BROWN_SHULKER_BOX(),
	CYAN_SHULKER_BOX(), GRAY_SHULKER_BOX(), GREEN_SHULKER_BOX(), LIGHT_BLUE_SHULKER_BOX(), LIME_SHULKER_BOX(),
	MAGENTA_SHULKER_BOX(), ORANGE_SHULKER_BOX(), PINK_SHULKER_BOX(), PURPLE_SHULKER_BOX(), RED_SHULKER_BOX(),
	LIGHT_GRAY_SHULKER_BOX(), WHITE_SHULKER_BOX(), YELLOW_SHULKER_BOX(), SHULKER_BOX(), NETHERITE_AXE(),
	NETHERITE_SWORD(), NETHERITE_SHOVEL(), NETHERITE_PICKAXE(), NETHERITE_HOE(), NETHERITE_HELMET(), NETHERITE_CHESTPLATE(),
	NETHERITE_LEGGINGS(), NETHERITE_BOOTS();

	private List<Material> repairTypes;

	ItemRepairType() {
		repairTypes = getRepairMaterials();
	}

	public List<Material> getRepairTypes() {
		return repairTypes;
	}

	public static ItemRepairType getType(Material type) {
		try {
			return ItemRepairType.valueOf(type.name());
		} catch (Exception ex) {

		}
		return null;
	}

	private List<Material> getRepairMaterials() {
		List<Material> materials = new ArrayList<Material>();

		for(String s: getRepairStrings()) {
			if (s == null) continue;
			try {
				materials.add(Material.valueOf(s));
			} catch (Exception ex) {

			}
		}

		return materials;
	}

	private List<String> getRepairStrings() {
		List<String> itemTypes = new ArrayList<String>();
		itemTypes.add("BOOK");
		itemTypes.add("ENCHANTED_BOOK");
		switch (name()) {
			case "CARROT_ON_A_STICK":
			case "CROSSBOW":
			case "TRIDENT":
				itemTypes.add(name());
				break;
			case "DIAMOND_AXE":
			case "DIAMOND_BOOTS":
			case "DIAMOND_CHESTPLATE":
			case "DIAMOND_HELMET":
			case "DIAMOND_HOE":
			case "DIAMOND_LEGGINGS":
			case "DIAMOND_PICKAXE":
			case "DIAMOND_SHOVEL":
			case "DIAMOND_SWORD":
				itemTypes.addAll(Arrays.asList(name(), "DIAMOND"));
				break;
			case "ELYTRA":
				itemTypes.addAll(Arrays.asList("ELYTRA", "PHANTOM_MEMBRANE"));
				break;
			case "BOW":
			case "FISHING_ROD":
				itemTypes.addAll(Arrays.asList(name(), "STRING"));
				break;
			case "GOLDEN_AXE":
			case "GOLDEN_BOOTS":
			case "GOLDEN_CHESTPLATE":
			case "GOLDEN_HELMET":
			case "GOLDEN_HOE":
			case "GOLDEN_LEGGINGS":
			case "GOLDEN_PICKAXE":
			case "GOLDEN_SHOVEL":
			case "GOLDEN_SWORD":
				itemTypes.addAll(Arrays.asList(name(), "GOLD_INGOT"));
				break;
			case "CHAINMAIL_BOOTS":
			case "CHAINMAIL_CHESTPLATE":
			case "CHAINMAIL_HELMET":
			case "CHAINMAIL_LEGGINGS":
			case "FLINT_AND_STEEL":
			case "IRON_AXE":
			case "IRON_BOOTS":
			case "IRON_CHESTPLATE":
			case "IRON_HELMET":
			case "IRON_HOE":
			case "IRON_LEGGINGS":
			case "IRON_PICKAXE":
			case "IRON_SHOVEL":
			case "IRON_SWORD":
			case "SHEARS":
				itemTypes.addAll(Arrays.asList(name(), "IRON_INGOT"));
				break;
			case "LEATHER_BOOTS":
			case "LEATHER_CHESTPLATE":
			case "LEATHER_HELMET":
			case "LEATHER_LEGGINGS":
				itemTypes.addAll(Arrays.asList(name(), "LEATHER"));
				break;
			case "NETHERITE_AXE":
			case "NETHERITE_BOOTS":
			case "NETHERITE_CHESTPLATE":
			case "NETHERITE_HELMET":
			case "NETHERITE_HOE":
			case "NETHERITE_LEGGINGS":
			case "NETHERITE_PICKAXE":
			case "NETHERITE_SHOVEL":
			case "NETHERITE_SWORD":
				itemTypes.addAll(Arrays.asList(name(), "NETHERITE_INGOT"));
				break;
			case "STONE_AXE":
			case "STONE_HOE":
			case "STONE_PICKAXE":
			case "STONE_SHOVEL":
			case "STONE_SWORD":
				itemTypes.addAll(Arrays.asList(name(), "COBBLESTONE"));
				break;
			case "TURTLE_HELMET":
				itemTypes.addAll(Arrays.asList("TURTLE_HELMET", "SCUTE"));
				break;
			case "SHIELD":
			case "WOODEN_AXE":
			case "WOODEN_HOE":
			case "WOODEN_PICKAXE":
			case "WOODEN_SHOVEL":
			case "WOODEN_SWORD":
				itemTypes.addAll(Arrays.asList(name(), "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", "JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS"));
				break;
			default:
				break;
		}
		return itemTypes;
	}
}
