package org.ctp.enchantmentsolution.utils.items.nms.itemrepair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.items.nms.ItemRepairType;

public enum ItemRepair_v1_13 implements ItemRepairType{
	DIAMOND_AXE(Material.DIAMOND_AXE), DIAMOND_SWORD(Material.DIAMOND_SWORD), DIAMOND_SHOVEL(Material.DIAMOND_SHOVEL), DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE), 
	DIAMOND_HOE(Material.DIAMOND_HOE), DIAMOND_HELMET(Material.DIAMOND_HELMET), DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE), 
	DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS), DIAMOND_BOOTS(Material.DIAMOND_BOOTS), IRON_AXE(Material.IRON_AXE), IRON_SWORD(Material.IRON_SWORD), 
	IRON_SHOVEL(Material.IRON_SHOVEL), IRON_PICKAXE(Material.IRON_PICKAXE), IRON_HOE(Material.IRON_HOE), IRON_HELMET(Material.IRON_HELMET), 
	IRON_CHESTPLATE(Material.IRON_CHESTPLATE), IRON_LEGGINGS(Material.IRON_LEGGINGS), IRON_BOOTS(Material.IRON_BOOTS), GOLDEN_AXE(Material.GOLDEN_AXE), 
	GOLDEN_SWORD(Material.GOLDEN_SWORD), GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL), GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE), GOLDEN_HOE(Material.GOLDEN_HOE), 
	GOLDEN_HELMET(Material.GOLDEN_HELMET), GOLDEN_CHESTPLATE(Material.GOLDEN_CHESTPLATE), GOLDEN_LEGGINGS(Material.GOLDEN_LEGGINGS), 
	GOLDEN_BOOTS(Material.GOLDEN_BOOTS), STONE_AXE(Material.STONE_AXE), STONE_SWORD(Material.STONE_SWORD), STONE_SHOVEL(Material.STONE_SHOVEL), 
	STONE_PICKAXE(Material.STONE_PICKAXE), STONE_HOE(Material.STONE_HOE), CHAINMAIL_HELMET(Material.CHAINMAIL_HELMET), 
	CHAINMAIL_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE), CHAINMAIL_LEGGINGS(Material.CHAINMAIL_LEGGINGS), CHAINMAIL_BOOTS(Material.CHAINMAIL_BOOTS),
	WOODEN_AXE(Material.WOODEN_AXE), WOODEN_SWORD(Material.WOODEN_SWORD), WOODEN_SHOVEL(Material.WOODEN_SHOVEL), WOODEN_PICKAXE(Material.WOODEN_PICKAXE), 
	WOODEN_HOE(Material.WOODEN_HOE), LEATHER_HELMET(Material.LEATHER_HELMET), LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE), 
	LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS), LEATHER_BOOTS(Material.LEATHER_BOOTS), ELYTRA(Material.ELYTRA), BOW(Material.BOW), FISHING_ROD(Material.FISHING_ROD), 
	BOOK(Material.BOOK), ENCHANTED_BOOK(Material.ENCHANTED_BOOK), TRIDENT(Material.TRIDENT), SHEARS(Material.SHEARS), FLINT_AND_STEEL(Material.FLINT_AND_STEEL),
	SHIELD(Material.SHIELD), TURTLE_HELMET(Material.TURTLE_HELMET), CARROT_ON_A_STICK(Material.CARROT_ON_A_STICK), BLACK_SHULKER_BOX(Material.BLACK_SHULKER_BOX), 
	BLUE_SHULKER_BOX(Material.BLUE_SHULKER_BOX), BROWN_SHULKER_BOX(Material.BROWN_SHULKER_BOX), CYAN_SHULKER_BOX(Material.CYAN_SHULKER_BOX), 
	GRAY_SHULKER_BOX(Material.GRAY_SHULKER_BOX), GREEN_SHULKER_BOX(Material.GREEN_SHULKER_BOX), LIGHT_BLUE_SHULKER_BOX(Material.LIGHT_BLUE_SHULKER_BOX), 
	LIME_SHULKER_BOX(Material.LIME_SHULKER_BOX), MAGENTA_SHULKER_BOX(Material.MAGENTA_SHULKER_BOX), ORANGE_SHULKER_BOX(Material.ORANGE_SHULKER_BOX), 
	PINK_SHULKER_BOX(Material.PINK_SHULKER_BOX), PURPLE_SHULKER_BOX(Material.PURPLE_SHULKER_BOX), RED_SHULKER_BOX(Material.RED_SHULKER_BOX),
	LIGHT_GRAY_SHULKER_BOX(Material.LIGHT_GRAY_SHULKER_BOX), WHITE_SHULKER_BOX(Material.WHITE_SHULKER_BOX), YELLOW_SHULKER_BOX(Material.YELLOW_SHULKER_BOX), 
	SHULKER_BOX(Material.SHULKER_BOX);
	
	private Material material;
	private List<Material> repairTypes;
	
	ItemRepair_v1_13(Material material){
		this.material = material;
		this.repairTypes = getItemRepairTypes(material);
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public List<Material> getRepairTypes(){
		return repairTypes;
	}
	
	public static ItemRepairType getType(Material type) {
		for(ItemRepairType repairType : ItemRepair_v1_13.values()) {
			if(repairType.getMaterial().equals(type)) {
				return repairType;
			}
		}
		return null;
	}
	
	private List<Material> getItemRepairTypes(Material type){
		List<Material> itemTypes = new ArrayList<Material>();
		switch(type) {
		case BLACK_SHULKER_BOX:
		case BLUE_SHULKER_BOX:
		case BROWN_SHULKER_BOX:
		case CYAN_SHULKER_BOX:
		case GRAY_SHULKER_BOX:
		case GREEN_SHULKER_BOX:
		case LIGHT_BLUE_SHULKER_BOX:
		case LIGHT_GRAY_SHULKER_BOX:
		case LIME_SHULKER_BOX:
		case MAGENTA_SHULKER_BOX:
		case ORANGE_SHULKER_BOX:
		case PINK_SHULKER_BOX:
		case PURPLE_SHULKER_BOX:
		case RED_SHULKER_BOX:
		case WHITE_SHULKER_BOX:
		case YELLOW_SHULKER_BOX:
		case SHULKER_BOX:
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case BOOK:
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case BOW:
			itemTypes.addAll(Arrays.asList(Material.BOW, Material.STRING, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case CARROT_ON_A_STICK:
			itemTypes.addAll(Arrays.asList(Material.CARROT_ON_A_STICK, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case CHAINMAIL_BOOTS:
			itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_BOOTS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case CHAINMAIL_CHESTPLATE:
			itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_CHESTPLATE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case CHAINMAIL_HELMET:
			itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_HELMET, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case CHAINMAIL_LEGGINGS:
			itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_LEGGINGS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_AXE:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_AXE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_BOOTS:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_BOOTS, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_CHESTPLATE:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_CHESTPLATE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_HELMET:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_HOE:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_HOE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_LEGGINGS:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_LEGGINGS, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_PICKAXE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_SHOVEL, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case DIAMOND_SWORD:
			itemTypes.addAll(Arrays.asList(Material.DIAMOND_SWORD, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case ELYTRA:
			itemTypes.addAll(Arrays.asList(Material.ELYTRA, Material.PHANTOM_MEMBRANE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case ENCHANTED_BOOK:
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case FISHING_ROD:
			itemTypes.addAll(Arrays.asList(Material.FISHING_ROD, Material.STRING, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case FLINT_AND_STEEL:
			itemTypes.addAll(Arrays.asList(Material.FLINT_AND_STEEL, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_AXE:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_AXE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_BOOTS:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_BOOTS, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_CHESTPLATE:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_CHESTPLATE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_HELMET:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_HELMET, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_HOE:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_HOE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_LEGGINGS:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_LEGGINGS, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_PICKAXE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_SHOVEL, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case GOLDEN_SWORD:
			itemTypes.addAll(Arrays.asList(Material.GOLDEN_SWORD, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_AXE:
			itemTypes.addAll(Arrays.asList(Material.IRON_AXE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_BOOTS:
			itemTypes.addAll(Arrays.asList(Material.IRON_BOOTS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_CHESTPLATE:
			itemTypes.addAll(Arrays.asList(Material.IRON_CHESTPLATE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_HELMET:
			itemTypes.addAll(Arrays.asList(Material.IRON_HELMET, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_HOE:
			itemTypes.addAll(Arrays.asList(Material.IRON_HOE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_LEGGINGS:
			itemTypes.addAll(Arrays.asList(Material.IRON_LEGGINGS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.IRON_PICKAXE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.IRON_SHOVEL, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case IRON_SWORD:
			itemTypes.addAll(Arrays.asList(Material.IRON_SWORD, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case LEATHER_BOOTS:
			itemTypes.addAll(Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case LEATHER_CHESTPLATE:
			itemTypes.addAll(Arrays.asList(Material.LEATHER_CHESTPLATE, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case LEATHER_HELMET:
			itemTypes.addAll(Arrays.asList(Material.LEATHER_HELMET, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case LEATHER_LEGGINGS:
			itemTypes.addAll(Arrays.asList(Material.LEATHER_LEGGINGS, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case SHEARS:
			itemTypes.addAll(Arrays.asList(Material.SHEARS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case SHIELD:
			itemTypes.addAll(Arrays.asList(Material.SHIELD, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case STONE_AXE:
			itemTypes.addAll(Arrays.asList(Material.STONE_AXE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case STONE_HOE:
			itemTypes.addAll(Arrays.asList(Material.STONE_HOE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case STONE_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.STONE_PICKAXE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case STONE_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.STONE_SHOVEL, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case STONE_SWORD:
			itemTypes.addAll(Arrays.asList(Material.STONE_SWORD, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case TRIDENT:
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.TRIDENT, Material.ENCHANTED_BOOK));
			return itemTypes;
		case TURTLE_HELMET:
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.TURTLE_HELMET, Material.SCUTE, Material.ENCHANTED_BOOK));
			return itemTypes;
		case WOODEN_AXE:
			itemTypes.addAll(Arrays.asList(Material.WOODEN_AXE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case WOODEN_HOE:
			itemTypes.addAll(Arrays.asList(Material.WOODEN_HOE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case WOODEN_PICKAXE:
			itemTypes.addAll(Arrays.asList(Material.WOODEN_PICKAXE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case WOODEN_SHOVEL:
			itemTypes.addAll(Arrays.asList(Material.WOODEN_SHOVEL, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		case WOODEN_SWORD:
			itemTypes.addAll(Arrays.asList(Material.WOODEN_SWORD, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
			return itemTypes;
		default:
			break;
		}
		return null;
	}
	
}
