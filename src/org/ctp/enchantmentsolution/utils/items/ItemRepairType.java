package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public enum ItemRepairType{
	DIAMOND_AXE(), DIAMOND_SWORD(), DIAMOND_SHOVEL(), DIAMOND_PICKAXE(), DIAMOND_HOE(), DIAMOND_HELMET(), DIAMOND_CHESTPLATE(), DIAMOND_LEGGINGS(), DIAMOND_BOOTS(), 
	IRON_AXE(), IRON_SWORD(), IRON_SHOVEL(), IRON_PICKAXE(), IRON_HOE(), IRON_HELMET(), IRON_CHESTPLATE(), IRON_LEGGINGS(), IRON_BOOTS(), GOLDEN_AXE(), 
	GOLDEN_SWORD(), GOLDEN_SHOVEL(), GOLDEN_PICKAXE(), GOLDEN_HOE(), GOLDEN_HELMET(), GOLDEN_CHESTPLATE(), GOLDEN_LEGGINGS(), GOLDEN_BOOTS(), STONE_AXE(), 
	STONE_SWORD(), STONE_SHOVEL(), STONE_PICKAXE(), STONE_HOE(), CHAINMAIL_HELMET(), CHAINMAIL_CHESTPLATE(), CHAINMAIL_LEGGINGS(), CHAINMAIL_BOOTS(), WOODEN_AXE(), 
	WOODEN_SWORD(), WOODEN_SHOVEL(), WOODEN_PICKAXE(), WOODEN_HOE(), LEATHER_HELMET(), LEATHER_CHESTPLATE(), LEATHER_LEGGINGS(), LEATHER_BOOTS(), ELYTRA(), BOW(), 
	FISHING_ROD(), BOOK(), ENCHANTED_BOOK(), TRIDENT(), SHEARS(), FLINT_AND_STEEL(),CROSSBOW(), SHIELD(), TURTLE_HELMET(), CARROT_ON_A_STICK(), BLACK_SHULKER_BOX(), 
	BLUE_SHULKER_BOX(), BROWN_SHULKER_BOX(), CYAN_SHULKER_BOX(), GRAY_SHULKER_BOX(), GREEN_SHULKER_BOX(), LIGHT_BLUE_SHULKER_BOX(), LIME_SHULKER_BOX(), 
	MAGENTA_SHULKER_BOX(), ORANGE_SHULKER_BOX(), PINK_SHULKER_BOX(), PURPLE_SHULKER_BOX(), RED_SHULKER_BOX(), LIGHT_GRAY_SHULKER_BOX(), WHITE_SHULKER_BOX(), 
	YELLOW_SHULKER_BOX(), SHULKER_BOX();
	
	private List<Material> repairTypes;
	
	ItemRepairType(){
		this.repairTypes = getRepairMaterials();
	}
	
	public List<Material> getRepairTypes(){
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
		
		for(String s : getRepairStrings()) {
			try {
				materials.add(Material.valueOf(s));
			} catch (Exception ex) {
				
			}
		}
		
		return materials;
	}
	
	private List<String> getRepairStrings() {
		List<String> itemTypes = new ArrayList<String>();
		switch(this.name()) {
		case "BLACK_SHULKER_BOX":
		case "BLUE_SHULKER_BOX":
		case "BROWN_SHULKER_BOX":
		case "CYAN_SHULKER_BOX":
		case "GRAY_SHULKER_BOX":
		case "GREEN_SHULKER_BOX":
		case "LIGHT_BLUE_SHULKER_BOX":
		case "LIGHT_GRAY_SHULKER_BOX":
		case "LIME_SHULKER_BOX":
		case "MAGENTA_SHULKER_BOX":
		case "ORANGE_SHULKER_BOX":
		case "PINK_SHULKER_BOX":
		case "PURPLE_SHULKER_BOX":
		case "RED_SHULKER_BOX":
		case "WHITE_SHULKER_BOX":
		case "YELLOW_SHULKER_BOX":
		case "SHULKER_BOX":
			return Arrays.asList("BOOK", "ENCHANTED_BOOK");
		case "BOOK":
			itemTypes.addAll(Arrays.asList("BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "BOW":
			itemTypes.addAll(Arrays.asList("BOW", "STRING", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CARROT_ON_A_STICK":
			itemTypes.addAll(Arrays.asList("CARROT_ON_A_STICK", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CHAINMAIL_BOOTS":
			itemTypes.addAll(Arrays.asList("CHAINMAIL_BOOTS", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CHAINMAIL_CHESTPLATE":
			itemTypes.addAll(Arrays.asList("CHAINMAIL_CHESTPLATE", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CHAINMAIL_HELMET":
			itemTypes.addAll(Arrays.asList("CHAINMAIL_HELMET", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CHAINMAIL_LEGGINGS":
			itemTypes.addAll(Arrays.asList("CHAINMAIL_LEGGINGS", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "CROSSBOW":
			itemTypes.addAll(Arrays.asList("CROSSBOW", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_AXE":
			itemTypes.addAll(Arrays.asList("DIAMOND_AXE", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_BOOTS":
			itemTypes.addAll(Arrays.asList("DIAMOND_BOOTS", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_CHESTPLATE":
			itemTypes.addAll(Arrays.asList("DIAMOND_CHESTPLATE", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_HELMET":
			itemTypes.addAll(Arrays.asList("DIAMOND_HELMET", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_HOE":
			itemTypes.addAll(Arrays.asList("DIAMOND_HOE", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_LEGGINGS":
			itemTypes.addAll(Arrays.asList("DIAMOND_LEGGINGS", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_PICKAXE":
			itemTypes.addAll(Arrays.asList("DIAMOND_PICKAXE", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_SHOVEL":
			itemTypes.addAll(Arrays.asList("DIAMOND_SHOVEL", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "DIAMOND_SWORD":
			itemTypes.addAll(Arrays.asList("DIAMOND_SWORD", "DIAMOND", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "ELYTRA":
			itemTypes.addAll(Arrays.asList("ELYTRA", "PHANTOM_MEMBRANE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "ENCHANTED_BOOK":
			itemTypes.addAll(Arrays.asList("BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "FISHING_ROD":
			itemTypes.addAll(Arrays.asList("FISHING_ROD", "STRING", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "FLINT_AND_STEEL":
			itemTypes.addAll(Arrays.asList("FLINT_AND_STEEL", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_AXE":
			itemTypes.addAll(Arrays.asList("GOLDEN_AXE", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_BOOTS":
			itemTypes.addAll(Arrays.asList("GOLDEN_BOOTS", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_CHESTPLATE":
			itemTypes.addAll(Arrays.asList("GOLDEN_CHESTPLATE", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_HELMET":
			itemTypes.addAll(Arrays.asList("GOLDEN_HELMET", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_HOE":
			itemTypes.addAll(Arrays.asList("GOLDEN_HOE", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_LEGGINGS":
			itemTypes.addAll(Arrays.asList("GOLDEN_LEGGINGS", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_PICKAXE":
			itemTypes.addAll(Arrays.asList("GOLDEN_PICKAXE", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_SHOVEL":
			itemTypes.addAll(Arrays.asList("GOLDEN_SHOVEL", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "GOLDEN_SWORD":
			itemTypes.addAll(Arrays.asList("GOLDEN_SWORD", "GOLD_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_AXE":
			itemTypes.addAll(Arrays.asList("IRON_AXE", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_BOOTS":
			itemTypes.addAll(Arrays.asList("IRON_BOOTS", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_CHESTPLATE":
			itemTypes.addAll(Arrays.asList("IRON_CHESTPLATE", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_HELMET":
			itemTypes.addAll(Arrays.asList("IRON_HELMET", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_HOE":
			itemTypes.addAll(Arrays.asList("IRON_HOE", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_LEGGINGS":
			itemTypes.addAll(Arrays.asList("IRON_LEGGINGS", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_PICKAXE":
			itemTypes.addAll(Arrays.asList("IRON_PICKAXE", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_SHOVEL":
			itemTypes.addAll(Arrays.asList("IRON_SHOVEL", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "IRON_SWORD":
			itemTypes.addAll(Arrays.asList("IRON_SWORD", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "LEATHER_BOOTS":
			itemTypes.addAll(Arrays.asList("LEATHER_BOOTS", "LEATHER", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "LEATHER_CHESTPLATE":
			itemTypes.addAll(Arrays.asList("LEATHER_CHESTPLATE", "LEATHER", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "LEATHER_HELMET":
			itemTypes.addAll(Arrays.asList("LEATHER_HELMET", "LEATHER", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "LEATHER_LEGGINGS":
			itemTypes.addAll(Arrays.asList("LEATHER_LEGGINGS", "LEATHER", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "SHEARS":
			itemTypes.addAll(Arrays.asList("SHEARS", "IRON_INGOT", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "SHIELD":
			itemTypes.addAll(Arrays.asList("SHIELD", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "STONE_AXE":
			itemTypes.addAll(Arrays.asList("STONE_AXE", "COBBLESTONE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "STONE_HOE":
			itemTypes.addAll(Arrays.asList("STONE_HOE", "COBBLESTONE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "STONE_PICKAXE":
			itemTypes.addAll(Arrays.asList("STONE_PICKAXE", "COBBLESTONE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "STONE_SHOVEL":
			itemTypes.addAll(Arrays.asList("STONE_SHOVEL", "COBBLESTONE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "STONE_SWORD":
			itemTypes.addAll(Arrays.asList("STONE_SWORD", "COBBLESTONE", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "TRIDENT":
			itemTypes.addAll(Arrays.asList("BOOK", "TRIDENT", "ENCHANTED_BOOK"));
			return itemTypes;
		case "TURTLE_HELMET":
			itemTypes.addAll(Arrays.asList("BOOK", "TURTLE_HELMET", "SCUTE", "ENCHANTED_BOOK"));
			return itemTypes;
		case "WOODEN_AXE":
			itemTypes.addAll(Arrays.asList("WOODEN_AXE", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "WOODEN_HOE":
			itemTypes.addAll(Arrays.asList("WOODEN_HOE", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "WOODEN_PICKAXE":
			itemTypes.addAll(Arrays.asList("WOODEN_PICKAXE", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "WOODEN_SHOVEL":
			itemTypes.addAll(Arrays.asList("WOODEN_SHOVEL", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		case "WOODEN_SWORD":
			itemTypes.addAll(Arrays.asList("WOODEN_SWORD", "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", 
					"JUNGLE_PLANKS", "OAK_PLANKS", "DARK_OAK_PLANKS", "BOOK", "ENCHANTED_BOOK"));
			return itemTypes;
		default:
			break;
		}
		return null;
	}
}
