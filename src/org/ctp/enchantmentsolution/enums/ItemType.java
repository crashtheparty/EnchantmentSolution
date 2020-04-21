package org.ctp.enchantmentsolution.enums;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;

public class ItemType {
	public static List<ItemType> VALUES = new ArrayList<ItemType>();
	public final static ItemType HELMETS = new ItemType("helmets");
	public final static ItemType CHESTPLATES = new ItemType("chestplates");
	public final static ItemType LEGGINGS = new ItemType("leggings");
	public final static ItemType BOOTS = new ItemType("boots");
	public final static ItemType SWORDS = new ItemType("swords");
	public final static ItemType PICKAXES = new ItemType("pickaxes");
	public final static ItemType SHOVELS = new ItemType("shovels");
	public final static ItemType AXES = new ItemType("axes");
	public final static ItemType HOES = new ItemType("hoes");
	public final static ItemType BOW = new ItemType("bow");
	public final static ItemType SHIELD = new ItemType("shield");
	public final static ItemType FISHING_ROD = new ItemType("fishing_rod");
	public final static ItemType SHEARS = new ItemType("shears");
	public final static ItemType FLINT_AND_STEEL = new ItemType("flint_and_steel");
	public final static ItemType FISHING_STICK = new ItemType("fishing_stick");
	public final static ItemType ELYTRA = new ItemType("elytra");
	public final static ItemType TRIDENT = new ItemType("trident");
	public final static ItemType RANGED = new ItemType("ranged");
	public final static ItemType ARMOR = new ItemType("armor");
	public final static ItemType TOOLS = new ItemType("tools");
	public final static ItemType MELEE = new ItemType("melee");
	public final static ItemType MISC = new ItemType("misc");
	public final static ItemType WOODEN_TOOLS = new ItemType("wooden_tools");
	public final static ItemType STONE_TOOLS = new ItemType("stone_tools");
	public final static ItemType IRON_TOOLS = new ItemType("iron_tools");
	public final static ItemType GOLDEN_TOOLS = new ItemType("golden_tools");
	public final static ItemType DIAMOND_TOOLS = new ItemType("diamond_tools");
	public final static ItemType LEATHER_ARMOR = new ItemType("leather_armor");
	public final static ItemType GOLDEN_ARMOR = new ItemType("golden_armor");
	public final static ItemType CHAINMAIL_ARMOR = new ItemType("chainmail_armor");
	public final static ItemType IRON_ARMOR = new ItemType("iron_armor");
	public final static ItemType DIAMOND_ARMOR = new ItemType("diamond_armor");
	public final static ItemType CROSSBOW = new ItemType("crossbow");
	public final static ItemType BOOK = new ItemType("book");
	public final static ItemType ALL = new ItemType("all");
	public final static ItemType ENCHANTABLE = new ItemType("enchantable");
	public final static ItemType TURTLE_HELMET = new ItemType("turtle_helmet");
	public final static ItemType SHULKER_BOXES = new ItemType("shulker_boxes");
	public final static ItemType NONE = new ItemType("none");
	public final static ItemType OTHER = new ItemType("other");
	public final static ItemType NETHERITE_TOOLS = new ItemType("netherite_tools");
	public final static ItemType NETHERITE_ARMOR = new ItemType("netherite_armor");
	public final static ItemType DIAMOND_AXE = new ItemType("diamond_axe");
	public final static ItemType DIAMOND_SWORD = new ItemType("diamond_sword");
	public final static ItemType DIAMOND_SHOVEL = new ItemType("diamond_shovel");
	public final static ItemType DIAMOND_PICKAXE = new ItemType("diamond_pickaxe");
	public final static ItemType DIAMOND_HOE = new ItemType("diamond_hoe");
	public final static ItemType DIAMOND_HELMET = new ItemType("diamond_helmet");
	public final static ItemType DIAMOND_CHESTPLATE = new ItemType("diamond_chestplate");
	public final static ItemType DIAMOND_LEGGINGS = new ItemType("diamond_leggings");
	public final static ItemType DIAMOND_BOOTS = new ItemType("diamond_boots");
	public final static ItemType IRON_AXE = new ItemType("iron_axe");
	public final static ItemType IRON_SWORD = new ItemType("iron_sword");
	public final static ItemType IRON_SHOVEL = new ItemType("iron_shovel");
	public final static ItemType IRON_PICKAXE = new ItemType("iron_pickaxe");
	public final static ItemType IRON_HOE = new ItemType("iron_hoe");
	public final static ItemType IRON_HELMET = new ItemType("iron_helmet");
	public final static ItemType IRON_CHESTPLATE = new ItemType("iron_chestplate");
	public final static ItemType IRON_LEGGINGS = new ItemType("iron_leggings");
	public final static ItemType IRON_BOOTS = new ItemType("iron_boots");
	public final static ItemType GOLDEN_AXE = new ItemType("golden_axe");
	public final static ItemType GOLDEN_SWORD = new ItemType("golden_sword");
	public final static ItemType GOLDEN_SHOVEL = new ItemType("golden_shovel");
	public final static ItemType GOLDEN_PICKAXE = new ItemType("golden_pickaxe");
	public final static ItemType GOLDEN_HOE = new ItemType("golden_hoe");
	public final static ItemType GOLDEN_HELMET = new ItemType("golden_helmet");
	public final static ItemType GOLDEN_CHESTPLATE = new ItemType("golden_chestplate");
	public final static ItemType GOLDEN_LEGGINGS = new ItemType("golden_leggings");
	public final static ItemType GOLDEN_BOOTS = new ItemType("golden_boots");
	public final static ItemType STONE_AXE = new ItemType("stone_axe");
	public final static ItemType STONE_SWORD = new ItemType("stone_sword");
	public final static ItemType STONE_SHOVEL = new ItemType("stone_shovel");
	public final static ItemType STONE_PICKAXE = new ItemType("stone_pickaxe");
	public final static ItemType STONE_HOE = new ItemType("stone_hoe");
	public final static ItemType CHAINMAIL_HELMET = new ItemType("chainmail_helmet");
	public final static ItemType CHAINMAIL_CHESTPLATE = new ItemType("chainmail_chestplate");
	public final static ItemType CHAINMAIL_LEGGINGS = new ItemType("chainmail_leggings");
	public final static ItemType CHAINMAIL_BOOTS = new ItemType("chainmail_boots");
	public final static ItemType WOODEN_AXE = new ItemType("wooden_axe");
	public final static ItemType WOODEN_SWORD = new ItemType("wooden_sword");
	public final static ItemType WOODEN_SHOVEL = new ItemType("wooden_shovel");
	public final static ItemType WOODEN_PICKAXE = new ItemType("wooden_pickaxe");
	public final static ItemType WOODEN_HOE = new ItemType("wooden_hoe");
	public final static ItemType LEATHER_HELMET = new ItemType("leather_helmet");
	public final static ItemType LEATHER_CHESTPLATE = new ItemType("leather_chestplate");
	public final static ItemType LEATHER_LEGGINGS = new ItemType("leather_leggings");
	public final static ItemType LEATHER_BOOTS = new ItemType("leather_boots");
	public final static ItemType ENCHANTED_BOOK = new ItemType("enchanted_book");
	public final static ItemType CARROT_ON_A_STICK = new ItemType("carrot_on_a_stick");
	public final static ItemType WARPED_FUNGUS_ON_A_STICK = new ItemType("warped_fungus_on_a_stick");
	public final static ItemType BLACK_SHULKER_BOX = new ItemType("black_shulker_box");
	public final static ItemType BLUE_SHULKER_BOX = new ItemType("blue_shulker_box");
	public final static ItemType BROWN_SHULKER_BOX = new ItemType("brown_shulker_box");
	public final static ItemType CYAN_SHULKER_BOX = new ItemType("cyan_shulker_box");
	public final static ItemType GRAY_SHULKER_BOX = new ItemType("gray_shulker_box");
	public final static ItemType GREEN_SHULKER_BOX = new ItemType("green_shulker_box");
	public final static ItemType LIGHT_BLUE_SHULKER_BOX = new ItemType("light_blue_shulker_box");
	public final static ItemType LIME_SHULKER_BOX = new ItemType("lime_shulker_box");
	public final static ItemType MAGENTA_SHULKER_BOX = new ItemType("magenta_shulker_box");
	public final static ItemType ORANGE_SHULKER_BOX = new ItemType("orange_shulker_box");
	public final static ItemType PINK_SHULKER_BOX = new ItemType("pink_shulker_box");
	public final static ItemType PURPLE_SHULKER_BOX = new ItemType("purple_shulker_box");
	public final static ItemType RED_SHULKER_BOX = new ItemType("red_shulker_box");
	public final static ItemType LIGHT_GRAY_SHULKER_BOX = new ItemType("light_gray_shulker_box");
	public final static ItemType WHITE_SHULKER_BOX = new ItemType("white_shulker_box");
	public final static ItemType YELLOW_SHULKER_BOX = new ItemType("yellow_shulker_box");
	public final static ItemType SHULKER_BOX = new ItemType("shulker_box");
	public final static ItemType NETHERITE_AXE = new ItemType("netherite_axe");
	public final static ItemType NETHERITE_SWORD = new ItemType("netherite_sword");
	public final static ItemType NETHERITE_SHOVEL = new ItemType("netherite_shovel");
	public final static ItemType NETHERITE_PICKAXE = new ItemType("netherite_pickaxe");
	public final static ItemType NETHERITE_HOE = new ItemType("netherite_hoe");
	public final static ItemType NETHERITE_HELMET = new ItemType("netherite_helmet");
	public final static ItemType NETHERITE_CHESTPLATE = new ItemType("netherite_chestplate");
	public final static ItemType NETHERITE_LEGGINGS = new ItemType("netherite_leggings");
	public final static ItemType NETHERITE_BOOTS = new ItemType("netherite_boots");
	public final static ItemType DIAMOND = new ItemType("diamond");
	public final static ItemType IRON_INGOT = new ItemType("iron_ingot");
	public final static ItemType GOLDEN_INGOT = new ItemType("golden_ingot");
	public final static ItemType COBBLESTONE = new ItemType("cobblestone");
	public final static ItemType ACACIA_PLANKS = new ItemType("acacia_planks");
	public final static ItemType BIRCH_PLANKS = new ItemType("birch_planks");
	public final static ItemType DARK_OAK_PLANKS = new ItemType("dark_oak_planks");
	public final static ItemType JUNGLE_PLANKS = new ItemType("jungle_planks");
	public final static ItemType OAK_PLANKS = new ItemType("oak_planks");
	public final static ItemType SPRUCE_PLANKS = new ItemType("spruce_planks");
	public final static ItemType LEATHER = new ItemType("leather");
	public final static ItemType PHANTOM_MEMBRANE = new ItemType("phantom_membrane");
	public final static ItemType STRING = new ItemType("string");
	public final static ItemType NETHERITE_INGOT = new ItemType("netherite_ingot");
	
	private String type, display;
	private List<ItemData> enchantMaterials, anvilMaterials;

	public ItemType(String type) {
		this.type = type;
		VALUES.add(this);
	}

	private static ItemType getType(Material type) {
		try {
			ItemType base = baseRepairType(type);
			if (base != null) return base;
		} catch (Exception ex) {

		}
		if (type == null || type.isAir()) return null;
		return new CustomItemType("minecraft:" + type.name().toLowerCase(), VanillaItemType.VANILLA);
	}

	private static ItemType baseRepairType(Material type) {
		for(ItemType repair: VALUES)
			if (!(repair instanceof CustomItemType)) for(ItemData data: repair.getAnvilMaterials())
				if (data.getMaterial() == type) return repair;
		return null;
	}

	public static ItemType mmoRepairType(ItemData data, VanillaItemType vanilla) {
		for (ItemType value : VALUES)
			if(value instanceof CustomItemType) {
				CustomItemType custom = (CustomItemType) value;
				if(custom.getVanilla() == vanilla && custom.getType().equals("mmoitems:" + vanilla.name().toLowerCase() + ":" + data.getMMOType())) return custom;
			}
		return new CustomItemType("mmoitems:" + vanilla.name().toLowerCase() + ":" + data.getMMOType(), vanilla);
	}

	public String getType() {
		return type;
	}

	public List<ItemData> getEnchantMaterials() {
		if (enchantMaterials == null) enchantMaterials = getEnchantMaterials(getType());
		return enchantMaterials;
	}

	public static List<ItemData> getAllEnchantMaterials() {
		List<ItemData> all = new ArrayList<ItemData>();
		all.addAll(ItemType.ALL.getEnchantMaterials());
		for(String s: ConfigString.EXTRA_ENCHANTING_MATERIALS.getStringList()) {
			MatData data = new MatData(s);
			if (data.getMaterial() != null) all.add(new ItemData(data.getMaterial(), null, null));
		}
		return all;
	}

	public List<ItemData> getAnvilMaterials() {
		if (anvilMaterials == null) anvilMaterials = getAnvilMaterials(getType());
		return anvilMaterials;
	}

	public static List<Material> getRepairMaterials() {
		List<Material> repair = new ArrayList<Material>();

		for(String s: getRepairMaterialsStrings())
			try {
				repair.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception ex) {

			}

		return repair;
	}

	public static List<String> getRepairMaterialsStrings() {
		return Arrays.asList(DIAMOND.getType(), IRON_INGOT.getType(), COBBLESTONE.getType(), ACACIA_PLANKS.getType(), BIRCH_PLANKS.getType(), DARK_OAK_PLANKS.getType(), JUNGLE_PLANKS.getType(), OAK_PLANKS.getType(), DARK_OAK_PLANKS.getType(), LEATHER.getType(), PHANTOM_MEMBRANE.getType(), STRING.getType(), GOLDEN_INGOT.getType(), NETHERITE_INGOT.getType());
	}

	public String getDisplayName() {
		if (display == null) display = getDisplayType();
		return display;
	}

	public static String getUnlocalizedName(Material material) {
		return (material.isBlock() ? "block" : "item") + ".minecraft." + material.name().toLowerCase();
	}

	public Map<Material, String> getUnlocalizedNames() {
		Map<Material, String> names = new HashMap<Material, String>();
		for(ItemData data: getEnchantMaterials())
			names.put(data.getMaterial(), getUnlocalizedName(data.getMaterial()));
		return names;
	}

	public static boolean hasEnchantMaterial(ItemData mat) {
		for(ItemType type: VALUES)
			for(ItemData data: type.getEnchantMaterials())
				if (data.equals(mat)) return true;
		return false;
	}

	public static boolean hasAnvilMaterial(ItemData mat) {
		for(ItemType type: VALUES)
			for(ItemData data: type.getAnvilMaterials())
				if (data.equals(mat)) return true;
		return false;
	}

	private String getDisplayType() {
		String s = ConfigUtils.getString(Type.LANGUAGE, "item_display_types." + getType().toLowerCase());
		return s == null ? getType() : s;
	}

	public static List<ItemType> getEnchantableTypes() {
		return Arrays.asList(ALL, ARMOR, AXES, BOOK, BOOTS, BOW, CARROT_ON_A_STICK, CHAINMAIL_ARMOR, CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS, CHESTPLATES, CROSSBOW, DIAMOND_ARMOR, DIAMOND_AXE, DIAMOND_BOOTS, DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_HOE, DIAMOND_LEGGINGS, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD, DIAMOND_TOOLS, ELYTRA, ENCHANTABLE, FISHING_ROD, FISHING_STICK, FLINT_AND_STEEL, GOLDEN_ARMOR, GOLDEN_AXE, GOLDEN_BOOTS, GOLDEN_CHESTPLATE, GOLDEN_HELMET, GOLDEN_HOE, GOLDEN_LEGGINGS, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD, GOLDEN_TOOLS, HELMETS, HOES, IRON_ARMOR, IRON_AXE, IRON_BOOTS, IRON_CHESTPLATE, IRON_HELMET, IRON_HOE, IRON_LEGGINGS, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD, IRON_TOOLS, LEATHER_ARMOR, LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS, LEGGINGS, MELEE, MISC, NETHERITE_ARMOR, NETHERITE_AXE, NETHERITE_BOOTS, NETHERITE_CHESTPLATE, NETHERITE_HELMET, NETHERITE_HOE, NETHERITE_LEGGINGS, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD, NETHERITE_TOOLS, OTHER, PICKAXES, RANGED, SHEARS, SHIELD, SHOVELS, STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD, STONE_TOOLS, SWORDS, TOOLS, TRIDENT, TURTLE_HELMET, WARPED_FUNGUS_ON_A_STICK, WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD, WOODEN_TOOLS);
	}

	public static List<ItemType> getAnvilableTypes() {
		return Arrays.asList(BLACK_SHULKER_BOX, BLUE_SHULKER_BOX, BOOK, BOW, BROWN_SHULKER_BOX, CARROT_ON_A_STICK, CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS, CROSSBOW, CYAN_SHULKER_BOX, DIAMOND_AXE, DIAMOND_BOOTS, DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_HOE, DIAMOND_LEGGINGS, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD, ELYTRA, ENCHANTED_BOOK, FISHING_ROD, FLINT_AND_STEEL, GOLDEN_AXE, GOLDEN_BOOTS, GOLDEN_CHESTPLATE, GOLDEN_HELMET, GOLDEN_HOE, GOLDEN_LEGGINGS, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD, GRAY_SHULKER_BOX, GREEN_SHULKER_BOX, IRON_AXE, IRON_BOOTS, IRON_CHESTPLATE, IRON_HELMET, IRON_HOE, IRON_LEGGINGS, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD, LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS, LIGHT_BLUE_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, LIME_SHULKER_BOX, MAGENTA_SHULKER_BOX, NETHERITE_AXE, NETHERITE_BOOTS, NETHERITE_CHESTPLATE, NETHERITE_HELMET, NETHERITE_HOE, NETHERITE_LEGGINGS, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD, ORANGE_SHULKER_BOX, PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX, SHEARS, SHIELD, STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD, TRIDENT, TURTLE_HELMET, WARPED_FUNGUS_ON_A_STICK, WHITE_SHULKER_BOX, WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD, YELLOW_SHULKER_BOX);
	}

	public static List<ItemType> getUniqueEnchantableTypes() {
		return Arrays.asList(HELMETS, CHESTPLATES, LEGGINGS, BOOTS, AXES, HOES, SHOVELS, PICKAXES, SWORDS, BOW, CROSSBOW, TRIDENT, SHIELD, FISHING_ROD, ELYTRA, BOOK);
	}

	private List<String> getEnchantables(String type) {
		List<String> i = new ArrayList<String>();

		i.addAll(Arrays.asList("BOOK", "ENCHANTED_BOOK"));

		switch (type.toUpperCase()) {
			case "BOOK":
			case "BOW":
			case "CARROT_ON_A_STICK":
			case "CHAINMAIL_BOOTS":
			case "CHAINMAIL_CHESTPLATE":
			case "CHAINMAIL_HELMET":
			case "CHAINMAIL_LEGGINGS":
			case "CROSSBOW":
			case "DIAMOND_AXE":
			case "DIAMOND_BOOTS":
			case "DIAMOND_CHESTPLATE":
			case "DIAMOND_HELMET":
			case "DIAMOND_HOE":
			case "DIAMOND_LEGGINGS":
			case "DIAMOND_PICKAXE":
			case "DIAMOND_SHOVEL":
			case "DIAMOND_SWORD":
			case "ELYTRA":
			case "FISHING_ROD":
			case "FLINT_AND_STEEL":
			case "GOLDEN_AXE":
			case "GOLDEN_BOOTS":
			case "GOLDEN_CHESTPLATE":
			case "GOLDEN_HELMET":
			case "GOLDEN_HOE":
			case "GOLDEN_LEGGINGS":
			case "GOLDEN_PICKAXE":
			case "GOLDEN_SHOVEL":
			case "GOLDEN_SWORD":
			case "IRON_AXE":
			case "IRON_BOOTS":
			case "IRON_CHESTPLATE":
			case "IRON_HELMET":
			case "IRON_HOE":
			case "IRON_LEGGINGS":
			case "IRON_PICKAXE":
			case "IRON_SHOVEL":
			case "IRON_SWORD":
			case "LEATHER_BOOTS":
			case "LEATHER_CHESTPLATE":
			case "LEATHER_HELMET":
			case "LEATHER_LEGGINGS":
			case "NETHERITE_AXE":
			case "NETHERITE_BOOTS":
			case "NETHERITE_CHESTPLATE":
			case "NETHERITE_HELMET":
			case "NETHERITE_HOE":
			case "NETHERITE_LEGGINGS":
			case "NETHERITE_PICKAXE":
			case "NETHERITE_SHOVEL":
			case "NETHERITE_SWORD":
			case "SHEARS":
			case "SHIELD":
			case "STONE_AXE":
			case "STONE_HOE":
			case "STONE_PICKAXE":
			case "STONE_SHOVEL":
			case "STONE_SWORD":
			case "TRIDENT":
			case "TURTLE_HELMET":
			case "WARPED_FUNGUS_ON_A_STICK":
			case "WOODEN_AXE":
			case "WOODEN_HOE":
			case "WOODEN_PICKAXE":
			case "WOODEN_SHOVEL":
			case "WOODEN_SWORD":
				i.add(type.toUpperCase());
				break;
			case "ALL":
				i.addAll(getEnchantables(ARMOR.getType()));
				i.addAll(getEnchantables(TOOLS.getType()));
				i.addAll(getEnchantables(MELEE.getType()));
				i.addAll(getEnchantables(RANGED.getType()));
				i.addAll(getEnchantables(MISC.getType()));
				break;
			case "ARMOR":
				i.addAll(getEnchantables(HELMETS.getType()));
				i.addAll(getEnchantables(CHESTPLATES.getType()));
				i.addAll(getEnchantables(LEGGINGS.getType()));
				i.addAll(getEnchantables(BOOTS.getType()));
				break;
			case "AXES":
				i.addAll(getEnchantables(DIAMOND_AXE.getType()));
				i.addAll(getEnchantables(GOLDEN_AXE.getType()));
				i.addAll(getEnchantables(IRON_AXE.getType()));
				i.addAll(getEnchantables(NETHERITE_AXE.getType()));
				i.addAll(getEnchantables(STONE_AXE.getType()));
				i.addAll(getEnchantables(WOODEN_AXE.getType()));
				break;
			case "BOOTS":
				i.addAll(getEnchantables(CHAINMAIL_BOOTS.getType()));
				i.addAll(getEnchantables(DIAMOND_BOOTS.getType()));
				i.addAll(getEnchantables(GOLDEN_BOOTS.getType()));
				i.addAll(getEnchantables(IRON_BOOTS.getType()));
				i.addAll(getEnchantables(LEATHER_BOOTS.getType()));
				i.addAll(getEnchantables(NETHERITE_BOOTS.getType()));
				break;
			case "CHAINMAIL_ARMOR":
				i.addAll(getEnchantables(CHAINMAIL_BOOTS.getType()));
				i.addAll(getEnchantables(CHAINMAIL_CHESTPLATE.getType()));
				i.addAll(getEnchantables(CHAINMAIL_HELMET.getType()));
				i.addAll(getEnchantables(CHAINMAIL_LEGGINGS.getType()));
				break;
			case "CHESTPLATES":
				i.addAll(getEnchantables(CHAINMAIL_CHESTPLATE.getType()));
				i.addAll(getEnchantables(DIAMOND_CHESTPLATE.getType()));
				i.addAll(getEnchantables(GOLDEN_CHESTPLATE.getType()));
				i.addAll(getEnchantables(IRON_CHESTPLATE.getType()));
				i.addAll(getEnchantables(LEATHER_CHESTPLATE.getType()));
				i.addAll(getEnchantables(NETHERITE_CHESTPLATE.getType()));
				break;
			case "CUSTOM":
				break;
			case "DIAMOND_ARMOR":
				i.addAll(getEnchantables(DIAMOND_BOOTS.getType()));
				i.addAll(getEnchantables(DIAMOND_CHESTPLATE.getType()));
				i.addAll(getEnchantables(DIAMOND_HELMET.getType()));
				i.addAll(getEnchantables(DIAMOND_LEGGINGS.getType()));
				break;
			case "DIAMOND_TOOLS":
				i.addAll(getEnchantables(DIAMOND_AXE.getType()));
				i.addAll(getEnchantables(DIAMOND_HOE.getType()));
				i.addAll(getEnchantables(DIAMOND_PICKAXE.getType()));
				i.addAll(getEnchantables(DIAMOND_SHOVEL.getType()));
				break;
			case "ENCHANTABLE":
				i.addAll(getEnchantables(ARMOR.getType()));
				i.addAll(getEnchantables(TOOLS.getType()));
				i.addAll(getEnchantables(MELEE.getType()));
				i.addAll(getEnchantables(RANGED.getType()));
				i.addAll(getEnchantables(SHIELD.getType()));
				i.addAll(getEnchantables(FISHING_ROD.getType()));
				i.addAll(getEnchantables(ELYTRA.getType()));
				break;
			case "FISHING_STICK":
				i.addAll(getEnchantables(CARROT_ON_A_STICK.getType()));
				i.addAll(getEnchantables(WARPED_FUNGUS_ON_A_STICK.getType()));
				break;
			case "GOLDEN_ARMOR":
				i.addAll(getEnchantables(GOLDEN_BOOTS.getType()));
				i.addAll(getEnchantables(GOLDEN_CHESTPLATE.getType()));
				i.addAll(getEnchantables(GOLDEN_HELMET.getType()));
				i.addAll(getEnchantables(GOLDEN_LEGGINGS.getType()));
				break;
			case "GOLDEN_TOOLS":
				i.addAll(getEnchantables(GOLDEN_AXE.getType()));
				i.addAll(getEnchantables(GOLDEN_HOE.getType()));
				i.addAll(getEnchantables(GOLDEN_PICKAXE.getType()));
				i.addAll(getEnchantables(GOLDEN_SHOVEL.getType()));
				break;
			case "HELMETS":
				i.addAll(getEnchantables(CHAINMAIL_HELMET.getType()));
				i.addAll(getEnchantables(DIAMOND_HELMET.getType()));
				i.addAll(getEnchantables(GOLDEN_HELMET.getType()));
				i.addAll(getEnchantables(IRON_HELMET.getType()));
				i.addAll(getEnchantables(LEATHER_HELMET.getType()));
				i.addAll(getEnchantables(NETHERITE_HELMET.getType()));
				break;
			case "HOES":
				i.addAll(getEnchantables(DIAMOND_HOE.getType()));
				i.addAll(getEnchantables(GOLDEN_HOE.getType()));
				i.addAll(getEnchantables(IRON_HOE.getType()));
				i.addAll(getEnchantables(NETHERITE_HOE.getType()));
				i.addAll(getEnchantables(STONE_HOE.getType()));
				i.addAll(getEnchantables(WOODEN_HOE.getType()));
				break;
			case "IRON_ARMOR":
				i.addAll(getEnchantables(IRON_BOOTS.getType()));
				i.addAll(getEnchantables(IRON_CHESTPLATE.getType()));
				i.addAll(getEnchantables(IRON_HELMET.getType()));
				i.addAll(getEnchantables(IRON_LEGGINGS.getType()));
				break;
			case "IRON_TOOLS":
				i.addAll(getEnchantables(IRON_AXE.getType()));
				i.addAll(getEnchantables(IRON_HOE.getType()));
				i.addAll(getEnchantables(IRON_PICKAXE.getType()));
				i.addAll(getEnchantables(IRON_SHOVEL.getType()));
				break;
			case "LEATHER_ARMOR":
				i.addAll(getEnchantables(LEATHER_BOOTS.getType()));
				i.addAll(getEnchantables(LEATHER_CHESTPLATE.getType()));
				i.addAll(getEnchantables(LEATHER_HELMET.getType()));
				i.addAll(getEnchantables(LEATHER_LEGGINGS.getType()));
				break;
			case "LEGGINGS":
				i.addAll(getEnchantables(CHAINMAIL_LEGGINGS.getType()));
				i.addAll(getEnchantables(DIAMOND_LEGGINGS.getType()));
				i.addAll(getEnchantables(GOLDEN_LEGGINGS.getType()));
				i.addAll(getEnchantables(IRON_LEGGINGS.getType()));
				i.addAll(getEnchantables(LEATHER_LEGGINGS.getType()));
				i.addAll(getEnchantables(NETHERITE_LEGGINGS.getType()));
				break;
			case "MELEE":
				i.addAll(getEnchantables(AXES.getType()));
				i.addAll(getEnchantables(SWORDS.getType()));
				break;
			case "MISC":
				i.addAll(getEnchantables(SHIELD.getType()));
				i.addAll(getEnchantables(FISHING_ROD.getType()));
				i.addAll(getEnchantables(FLINT_AND_STEEL.getType()));
				i.addAll(getEnchantables(FISHING_STICK.getType()));
				i.addAll(getEnchantables(ELYTRA.getType()));
				i.addAll(getEnchantables(SHEARS.getType()));
				i.addAll(getEnchantables(HOES.getType()));
				break;
			case "NETHERITE_ARMOR":
				i.addAll(getEnchantables(NETHERITE_BOOTS.getType()));
				i.addAll(getEnchantables(NETHERITE_CHESTPLATE.getType()));
				i.addAll(getEnchantables(NETHERITE_HELMET.getType()));
				i.addAll(getEnchantables(NETHERITE_LEGGINGS.getType()));
				break;
			case "NETHERITE_TOOLS":
				i.addAll(getEnchantables(NETHERITE_AXE.getType()));
				i.addAll(getEnchantables(NETHERITE_HOE.getType()));
				i.addAll(getEnchantables(NETHERITE_PICKAXE.getType()));
				i.addAll(getEnchantables(NETHERITE_SHOVEL.getType()));
				break;
			case "OTHER":
				i.addAll(getEnchantables(ALL.getType()));
				i.removeAll(getEnchantables(ARMOR.getType()));
				i.removeAll(getEnchantables(TOOLS.getType()));
				i.removeAll(getEnchantables(SWORDS.getType()));
				break;
			case "PICKAXES":
				i.addAll(getEnchantables(DIAMOND_PICKAXE.getType()));
				i.addAll(getEnchantables(GOLDEN_PICKAXE.getType()));
				i.addAll(getEnchantables(IRON_PICKAXE.getType()));
				i.addAll(getEnchantables(NETHERITE_PICKAXE.getType()));
				i.addAll(getEnchantables(STONE_PICKAXE.getType()));
				i.addAll(getEnchantables(WOODEN_PICKAXE.getType()));
				break;
			case "RANGED":
				i.addAll(getEnchantables(BOW.getType()));
				i.addAll(getEnchantables(CROSSBOW.getType()));
				i.addAll(getEnchantables(TRIDENT.getType()));
				break;
			case "SHOVELS":
				i.addAll(getEnchantables(DIAMOND_SHOVEL.getType()));
				i.addAll(getEnchantables(GOLDEN_SHOVEL.getType()));
				i.addAll(getEnchantables(IRON_SHOVEL.getType()));
				i.addAll(getEnchantables(NETHERITE_SHOVEL.getType()));
				i.addAll(getEnchantables(STONE_SHOVEL.getType()));
				i.addAll(getEnchantables(WOODEN_SHOVEL.getType()));
				break;
			case "STONE_TOOLS":
				i.addAll(getEnchantables(STONE_AXE.getType()));
				i.addAll(getEnchantables(STONE_HOE.getType()));
				i.addAll(getEnchantables(STONE_PICKAXE.getType()));
				i.addAll(getEnchantables(STONE_SHOVEL.getType()));
				break;
			case "SWORDS":
				i.addAll(getEnchantables(DIAMOND_SWORD.getType()));
				i.addAll(getEnchantables(GOLDEN_SWORD.getType()));
				i.addAll(getEnchantables(IRON_SWORD.getType()));
				i.addAll(getEnchantables(NETHERITE_SWORD.getType()));
				i.addAll(getEnchantables(STONE_SWORD.getType()));
				i.addAll(getEnchantables(WOODEN_SWORD.getType()));
				break;
			case "TOOLS":
				i.addAll(getEnchantables(AXES.getType()));
				i.addAll(getEnchantables(HOES.getType()));
				i.addAll(getEnchantables(SHOVELS.getType()));
				i.addAll(getEnchantables(PICKAXES.getType()));
				break;
			case "WOODEN_TOOLS":
				i.addAll(getEnchantables(WOODEN_AXE.getType()));
				i.addAll(getEnchantables(WOODEN_HOE.getType()));
				i.addAll(getEnchantables(WOODEN_PICKAXE.getType()));
				i.addAll(getEnchantables(WOODEN_SHOVEL.getType()));
				break;
		}
		Set<String> set = new LinkedHashSet<>();
		set.addAll(i);
		i.clear();
		i.addAll(set);
		return i;
	}

	private List<String> getAnvilables(String type) {
		List<String> i = new ArrayList<String>();
		i.add("BOOK");
		i.add("ENCHANTED_BOOK");
		switch (type.toUpperCase()) {
			case "CARROT_ON_A_STICK":
			case "WARPED_FUNGUS_ON_A_STICK":
			case "CROSSBOW":
			case "TRIDENT":
				i.add(type.toUpperCase());
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
				i.addAll(Arrays.asList(type.toUpperCase(), "DIAMOND"));
				break;
			case "ELYTRA":
				i.addAll(Arrays.asList("ELYTRA", "PHANTOM_MEMBRANE"));
				break;
			case "BOW":
			case "FISHING_ROD":
				i.addAll(Arrays.asList(type.toUpperCase(), "STRING"));
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
				i.addAll(Arrays.asList(type.toUpperCase(), "GOLD_INGOT"));
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
				i.addAll(Arrays.asList(type.toUpperCase(), "IRON_INGOT"));
				break;
			case "LEATHER_BOOTS":
			case "LEATHER_CHESTPLATE":
			case "LEATHER_HELMET":
			case "LEATHER_LEGGINGS":
				i.addAll(Arrays.asList(type.toUpperCase(), "LEATHER"));
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
				i.addAll(Arrays.asList(type.toUpperCase(), "NETHERITE_INGOT"));
				break;
			case "STONE_AXE":
			case "STONE_HOE":
			case "STONE_PICKAXE":
			case "STONE_SHOVEL":
			case "STONE_SWORD":
				i.addAll(Arrays.asList(type.toUpperCase(), "COBBLESTONE"));
				break;
			case "TURTLE_HELMET":
				i.addAll(Arrays.asList("TURTLE_HELMET", "SCUTE"));
				break;
			case "SHIELD":
			case "WOODEN_AXE":
			case "WOODEN_HOE":
			case "WOODEN_PICKAXE":
			case "WOODEN_SHOVEL":
			case "WOODEN_SWORD":
				i.addAll(Arrays.asList(type.toUpperCase(), "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", "JUNGLE_PLANKS", "OAK_PLANKS", "SPRUCE_PLANKS"));
				break;
			case "CUSTOM":
				break;
			default:
				break;
		}
		return i;
	}

	public static ItemType getAnvilType(ItemData data) {
		for(ItemType type: getAnvilableTypes())
			if (type.getType().equals(data.getMaterial().name())) return type;
		return getType(data.getMaterial());
	}

	private List<ItemData> getEnchantMaterials(String type) {
		List<ItemData> materials = new ArrayList<ItemData>();

		for(String s: getEnchantables(type))
			try {
				ItemData d = returnData(s);
				if (d != null) materials.add(d);
			} catch (Exception ex) {

			}

		return materials;
	}

	private List<ItemData> getAnvilMaterials(String type) {
		List<ItemData> materials = new ArrayList<ItemData>();

		for(String s: getAnvilables(type))
			try {
				ItemData d = returnData(s);
				if (d != null) materials.add(d);
			} catch (Exception ex) {

			}

		return materials;
	}

	private ItemData returnData(String s) {
		try {
			String[] split = s.split(":");
			if (split.length == 2) return new ItemData(Material.valueOf(split[0]), "VANILLA", split[1]);
			else if (split.length == 3) return new ItemData(Material.valueOf(split[0]), split[1], split[2]);
			else
				return new ItemData(new ItemStack(Material.valueOf(s)));
		} catch (Exception ex) {

		}
		return null;
	}

	public static String itemTypeToString(ItemType type) {
		return type.getType();
	}

	public static List<String> itemTypesToStrings(List<ItemType> types) {
		List<String> typeStrings = new ArrayList<String>();

		for(ItemType type: types)
			typeStrings.add(itemTypeToString(type));
		return typeStrings;
	}

	public static ItemType getItemType(String type) {
		for(ItemType value : VALUES)
			if(type.equalsIgnoreCase(value.getType())) return value;
		return null;
	}

	public static CustomItemType getCustomType(VanillaItemType vanilla, String type) {
		for(ItemType value : VALUES)
			if(value instanceof CustomItemType) {
				CustomItemType custom = (CustomItemType) value;
				if(custom.getVanilla() == vanilla && type.equalsIgnoreCase(custom.getType())) return custom;
			}
		return new CustomItemType(type, vanilla);
	}
}
