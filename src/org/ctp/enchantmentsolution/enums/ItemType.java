package org.ctp.enchantmentsolution.enums;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;

public class ItemType {
	public static List<ItemType> VALUES = new ArrayList<ItemType>();
	public final static ItemType HELMETS = new ItemType("helmets"), CHESTPLATES = new ItemType("chestplates"), LEGGINGS = new ItemType("leggings"),
	BOOTS = new ItemType("boots"), SWORDS = new ItemType("swords"), PICKAXES = new ItemType("pickaxes"), SHOVELS = new ItemType("shovels"),
	AXES = new ItemType("axes"), HOES = new ItemType("hoes"), BOW = new ItemType("bow"), SHIELD = new ItemType("shield"),
	FISHING_ROD = new ItemType("fishing_rod"), SHEARS = new ItemType("shears"), FLINT_AND_STEEL = new ItemType("flint_and_steel"),
	FISHING_STICK = new ItemType("fishing_stick"), ELYTRA = new ItemType("elytra"), TRIDENT = new ItemType("trident"), RANGED = new ItemType("ranged"),
	ARMOR = new ItemType("armor"), TOOLS = new ItemType("tools"), MELEE = new ItemType("melee"), MISC = new ItemType("misc"),
	WOODEN_TOOLS = new ItemType("wooden_tools"), STONE_TOOLS = new ItemType("stone_tools"), IRON_TOOLS = new ItemType("iron_tools"),
	GOLDEN_TOOLS = new ItemType("golden_tools"), DIAMOND_TOOLS = new ItemType("diamond_tools"), LEATHER_ARMOR = new ItemType("leather_armor"),
	GOLDEN_ARMOR = new ItemType("golden_armor"), CHAINMAIL_ARMOR = new ItemType("chainmail_armor"), IRON_ARMOR = new ItemType("iron_armor"),
	DIAMOND_ARMOR = new ItemType("diamond_armor"), CROSSBOW = new ItemType("crossbow"), BOOK = new ItemType("book"), ALL = new ItemType("all"),
	ENCHANTABLE = new ItemType("enchantable"), TURTLE_HELMET = new ItemType("turtle_helmet"), SHULKER_BOXES = new ItemType("shulker_boxes"),
	NONE = new ItemType("none"), OTHER = new ItemType("other"), NETHERITE_TOOLS = new ItemType("netherite_tools"),
	NETHERITE_ARMOR = new ItemType("netherite_armor"), DIAMOND_AXE = new ItemType("diamond_axe"), DIAMOND_SWORD = new ItemType("diamond_sword"),
	DIAMOND_SHOVEL = new ItemType("diamond_shovel"), DIAMOND_PICKAXE = new ItemType("diamond_pickaxe"), DIAMOND_HOE = new ItemType("diamond_hoe"),
	DIAMOND_HELMET = new ItemType("diamond_helmet"), DIAMOND_CHESTPLATE = new ItemType("diamond_chestplate"),
	DIAMOND_LEGGINGS = new ItemType("diamond_leggings"), DIAMOND_BOOTS = new ItemType("diamond_boots"), IRON_AXE = new ItemType("iron_axe"),
	IRON_SWORD = new ItemType("iron_sword"), IRON_SHOVEL = new ItemType("iron_shovel"), IRON_PICKAXE = new ItemType("iron_pickaxe"),
	IRON_HOE = new ItemType("iron_hoe"), IRON_HELMET = new ItemType("iron_helmet"), IRON_CHESTPLATE = new ItemType("iron_chestplate"),
	IRON_LEGGINGS = new ItemType("iron_leggings"), IRON_BOOTS = new ItemType("iron_boots"), GOLDEN_AXE = new ItemType("golden_axe"),
	GOLDEN_SWORD = new ItemType("golden_sword"), GOLDEN_SHOVEL = new ItemType("golden_shovel"), GOLDEN_PICKAXE = new ItemType("golden_pickaxe"),
	GOLDEN_HOE = new ItemType("golden_hoe"), GOLDEN_HELMET = new ItemType("golden_helmet"), GOLDEN_CHESTPLATE = new ItemType("golden_chestplate"),
	GOLDEN_LEGGINGS = new ItemType("golden_leggings"), GOLDEN_BOOTS = new ItemType("golden_boots"), STONE_AXE = new ItemType("stone_axe"),
	STONE_SWORD = new ItemType("stone_sword"), STONE_SHOVEL = new ItemType("stone_shovel"), STONE_PICKAXE = new ItemType("stone_pickaxe"),
	STONE_HOE = new ItemType("stone_hoe"), CHAINMAIL_HELMET = new ItemType("chainmail_helmet"), CHAINMAIL_CHESTPLATE = new ItemType("chainmail_chestplate"),
	CHAINMAIL_LEGGINGS = new ItemType("chainmail_leggings"), CHAINMAIL_BOOTS = new ItemType("chainmail_boots"), WOODEN_AXE = new ItemType("wooden_axe"),
	WOODEN_SWORD = new ItemType("wooden_sword"), WOODEN_SHOVEL = new ItemType("wooden_shovel"), WOODEN_PICKAXE = new ItemType("wooden_pickaxe"),
	WOODEN_HOE = new ItemType("wooden_hoe"), LEATHER_HELMET = new ItemType("leather_helmet"), LEATHER_CHESTPLATE = new ItemType("leather_chestplate"),
	LEATHER_LEGGINGS = new ItemType("leather_leggings"), LEATHER_BOOTS = new ItemType("leather_boots"), ENCHANTED_BOOK = new ItemType("enchanted_book"),
	CARROT_ON_A_STICK = new ItemType("carrot_on_a_stick"), WARPED_FUNGUS_ON_A_STICK = new ItemType("warped_fungus_on_a_stick"),
	BLACK_SHULKER_BOX = new ItemType("black_shulker_box"), BLUE_SHULKER_BOX = new ItemType("blue_shulker_box"),
	BROWN_SHULKER_BOX = new ItemType("brown_shulker_box"), CYAN_SHULKER_BOX = new ItemType("cyan_shulker_box"),
	GRAY_SHULKER_BOX = new ItemType("gray_shulker_box"), GREEN_SHULKER_BOX = new ItemType("green_shulker_box"),
	LIGHT_BLUE_SHULKER_BOX = new ItemType("light_blue_shulker_box"), LIME_SHULKER_BOX = new ItemType("lime_shulker_box"),
	MAGENTA_SHULKER_BOX = new ItemType("magenta_shulker_box"), ORANGE_SHULKER_BOX = new ItemType("orange_shulker_box"),
	PINK_SHULKER_BOX = new ItemType("pink_shulker_box"), PURPLE_SHULKER_BOX = new ItemType("purple_shulker_box"),
	RED_SHULKER_BOX = new ItemType("red_shulker_box"), LIGHT_GRAY_SHULKER_BOX = new ItemType("light_gray_shulker_box"),
	WHITE_SHULKER_BOX = new ItemType("white_shulker_box"), YELLOW_SHULKER_BOX = new ItemType("yellow_shulker_box"), SHULKER_BOX = new ItemType("shulker_box"),
	NETHERITE_AXE = new ItemType("netherite_axe"), NETHERITE_SWORD = new ItemType("netherite_sword"), NETHERITE_SHOVEL = new ItemType("netherite_shovel"),
	NETHERITE_PICKAXE = new ItemType("netherite_pickaxe"), NETHERITE_HOE = new ItemType("netherite_hoe"), NETHERITE_HELMET = new ItemType("netherite_helmet"),
	NETHERITE_CHESTPLATE = new ItemType("netherite_chestplate"), NETHERITE_LEGGINGS = new ItemType("netherite_leggings"),
	NETHERITE_BOOTS = new ItemType("netherite_boots"), DIAMOND = new ItemType("diamond"), IRON_INGOT = new ItemType("iron_ingot"),
	GOLD_INGOT = new ItemType("gold_ingot"), COBBLESTONE = new ItemType("cobblestone"), ACACIA_PLANKS = new ItemType("acacia_planks"),
	BIRCH_PLANKS = new ItemType("birch_planks"), DARK_OAK_PLANKS = new ItemType("dark_oak_planks"), JUNGLE_PLANKS = new ItemType("jungle_planks"),
	OAK_PLANKS = new ItemType("oak_planks"), SPRUCE_PLANKS = new ItemType("spruce_planks"), LEATHER = new ItemType("leather"),
	PHANTOM_MEMBRANE = new ItemType("phantom_membrane"), STRING = new ItemType("string"), NETHERITE_INGOT = new ItemType("netherite_ingot"),
	BLACKSTONE = new ItemType("blackstone"), CRIMSON_PLANKS = new ItemType("crimson_planks"), WARPED_PLANKS = new ItemType("warped_planks");

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
		if (type == null || MatData.isAir(type)) return null;
		return new CustomItemType("minecraft:" + type.name().toLowerCase(), VanillaItemType.VANILLA);
	}

	private static ItemType baseRepairType(Material type) {
		for(ItemType repair: VALUES)
			if (!(repair instanceof CustomItemType)) for(ItemData data: repair.getAnvilMaterials())
				if (data.getMaterial() == type) return repair;
		return null;
	}

	public static ItemType mmoRepairType(ItemData data, VanillaItemType vanilla) {
		for(ItemType value: VALUES)
			if (value instanceof CustomItemType) {
				CustomItemType custom = (CustomItemType) value;
				if (custom.getVanilla() == vanilla && custom.getType().equals("mmoitems:" + vanilla.name().toLowerCase() + ":" + data.getMMOType())) return custom;
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

	public List<ItemData> getAnvilMaterials(boolean b) {
		List<ItemData> data = getAnvilMaterials();
		if (b) {
			boolean contains = false;
			for(Material m: getRepairMaterials())
				if (ItemData.contains(data, m)) contains = true;
			if (!contains) data.add(new ItemData(new ItemStack(Material.DIAMOND)));
		}
		return data;
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
		return Arrays.asList(CRIMSON_PLANKS.getType(), WARPED_PLANKS.getType(), BLACKSTONE.getType(), DIAMOND.getType(), GOLD_INGOT.getType(), IRON_INGOT.getType(), COBBLESTONE.getType(), ACACIA_PLANKS.getType(), BIRCH_PLANKS.getType(), DARK_OAK_PLANKS.getType(), JUNGLE_PLANKS.getType(), OAK_PLANKS.getType(), DARK_OAK_PLANKS.getType(), LEATHER.getType(), PHANTOM_MEMBRANE.getType(), STRING.getType(), NETHERITE_INGOT.getType());
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
				i.addAll(Arrays.asList(type.toUpperCase(), "COBBLESTONE", "BLACKSTONE"));
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
				i.addAll(Arrays.asList(type.toUpperCase(), "ACACIA_PLANKS", "BIRCH_PLANKS", "DARK_OAK_PLANKS", "JUNGLE_PLANKS", "OAK_PLANKS", "SPRUCE_PLANKS", "CRIMSON_PLANKS", "NYLIUM_PLANKS"));
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

	public static List<String> itemTypesToStrings(List<ItemType> types) {
		List<String> typeStrings = new ArrayList<String>();

		for(ItemType type: types)
			typeStrings.add(type.getType());
		return typeStrings;
	}

	public static ItemType getItemType(String type) {
		for(ItemType value: VALUES)
			if (type.equalsIgnoreCase(value.getType())) return value;
		return null;
	}

	public static CustomItemType getCustomType(VanillaItemType vanilla, String type) {
		for(ItemType value: VALUES)
			if (value instanceof CustomItemType) {
				CustomItemType custom = (CustomItemType) value;
				if (custom.getVanilla() == vanilla && type.equalsIgnoreCase(custom.getType())) return custom;
			}
		return new CustomItemType(type, vanilla);
	}
}
