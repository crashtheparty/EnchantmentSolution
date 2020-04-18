package org.ctp.enchantmentsolution.enums;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;

public enum ItemType {
	HELMETS(), CHESTPLATES(), LEGGINGS(), BOOTS(), SWORDS(), PICKAXES(), SHOVELS(), AXES(), HOES(), BOW(), SHIELD(), FISHING_ROD(), SHEARS(),
	FLINT_AND_STEEL(), FISHING_STICK(), ELYTRA(), TRIDENT(), RANGED(), ARMOR(), TOOLS(), MELEE(), MISC(), WOODEN_TOOLS(), STONE_TOOLS(),
	IRON_TOOLS(), GOLDEN_TOOLS(), DIAMOND_TOOLS(), LEATHER_ARMOR(), GOLDEN_ARMOR(), CHAINMAIL_ARMOR(), IRON_ARMOR(), DIAMOND_ARMOR(),
	CROSSBOW(), BOOK(), ALL(), ENCHANTABLE(), TURTLE_HELMET(), SHULKER_BOXES(), NONE(), OTHER(), NETHERITE_TOOLS(), NETHERITE_ARMOR(),
	DIAMOND_AXE(), DIAMOND_SWORD(), DIAMOND_SHOVEL(), DIAMOND_PICKAXE(), DIAMOND_HOE(), DIAMOND_HELMET(), DIAMOND_CHESTPLATE(),
	DIAMOND_LEGGINGS(), DIAMOND_BOOTS(), IRON_AXE(), IRON_SWORD(), IRON_SHOVEL(), IRON_PICKAXE(), IRON_HOE(), IRON_HELMET(),
	IRON_CHESTPLATE(), IRON_LEGGINGS(), IRON_BOOTS(), GOLDEN_AXE(), GOLDEN_SWORD(), GOLDEN_SHOVEL(), GOLDEN_PICKAXE(), GOLDEN_HOE(),
	GOLDEN_HELMET(), GOLDEN_CHESTPLATE(), GOLDEN_LEGGINGS(), GOLDEN_BOOTS(), STONE_AXE(), STONE_SWORD(), STONE_SHOVEL(), STONE_PICKAXE(),
	STONE_HOE(), CHAINMAIL_HELMET(), CHAINMAIL_CHESTPLATE(), CHAINMAIL_LEGGINGS(), CHAINMAIL_BOOTS(), WOODEN_AXE(), WOODEN_SWORD(),
	WOODEN_SHOVEL(), WOODEN_PICKAXE(), WOODEN_HOE(), LEATHER_HELMET(), LEATHER_CHESTPLATE(), LEATHER_LEGGINGS(), LEATHER_BOOTS(),
	ENCHANTED_BOOK(), CARROT_ON_A_STICK(), WARPED_FUNGUS_ON_A_STICK(), BLACK_SHULKER_BOX(), BLUE_SHULKER_BOX(), BROWN_SHULKER_BOX(),
	CYAN_SHULKER_BOX(), GRAY_SHULKER_BOX(), GREEN_SHULKER_BOX(), LIGHT_BLUE_SHULKER_BOX(), LIME_SHULKER_BOX(), MAGENTA_SHULKER_BOX(),
	ORANGE_SHULKER_BOX(), PINK_SHULKER_BOX(), PURPLE_SHULKER_BOX(), RED_SHULKER_BOX(), LIGHT_GRAY_SHULKER_BOX(), WHITE_SHULKER_BOX(),
	YELLOW_SHULKER_BOX(), SHULKER_BOX(), NETHERITE_AXE(), NETHERITE_SWORD(), NETHERITE_SHOVEL(), NETHERITE_PICKAXE(), NETHERITE_HOE(),
	NETHERITE_HELMET(), NETHERITE_CHESTPLATE(), NETHERITE_LEGGINGS(), NETHERITE_BOOTS(), DIAMOND(), IRON_INGOT(), GOLDEN_INGOT(),
	COBBLESTONE(), ACACIA_PLANKS(), BIRCH_PLANKS(), DARK_OAK_PLANKS(), JUNGLE_PLANKS(), OAK_PLANKS(), SPRUCE_PLANKS(), LEATHER(),
	PHANTOM_MEMBRANE(), STRING(), NETHERITE_INGOT(), CUSTOM();

	private String type, display, customTypeName;
	private List<ItemData> enchantMaterials, anvilMaterials;
	private CustomItemType customType = null;

	ItemType() {
		type = name().toLowerCase();
	}

	private static ItemType getType(Material type) {
		try {
			ItemType base = baseRepairType(type);
			if (base != null) return base;
		} catch (Exception ex) {

		}
		if (type == null || type.isAir()) return null;
		return ItemType.CUSTOM.setCustomType(CustomItemType.VANILLA).setCustomString("minecraft:" + type.name().toLowerCase());
	}

	private static ItemType baseRepairType(Material type) {
		for(ItemType repair: values())
			if (repair != ItemType.CUSTOM) for(ItemData data: repair.getAnvilMaterials())
				if (data.getMaterial() == type) return repair;
		return null;
	}

	public static ItemType mmoRepairType(ItemData data, CustomItemType custom) {
		if (custom == CustomItemType.TYPE) return ItemType.CUSTOM.setCustomType(custom).setCustomString("mmoitems:type:" + data.getMMOType());
		if (custom == CustomItemType.TYPE) return ItemType.CUSTOM.setCustomType(custom).setCustomString("mmoitems:type_set:" + data.getMMOTypeSet());
		return null;
	}

	public String getType() {
		return type;
	}

	public List<ItemData> getEnchantMaterials() {
		if (enchantMaterials == null) enchantMaterials = getEnchantMaterials(getType());
		return enchantMaterials;
	}

	public List<ItemData> getAnvilMaterials() {
		if (anvilMaterials == null) anvilMaterials = getAnvilMaterials(getType());
		return anvilMaterials;
	}

	public static List<Material> getRepairMaterials() {
		List<Material> repair = new ArrayList<Material>();

		for(String s: getRepairMaterialsStrings())
			try {
				repair.add(Material.valueOf(s));
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
		for(ItemType type: values())
			for(ItemData data: type.getEnchantMaterials())
				if (data.equals(mat)) return true;
		return false;
	}

	public static boolean hasAnvilMaterial(ItemData mat) {
		for(ItemType type: values())
			for(ItemData data: type.getAnvilMaterials())
				if (data.equals(mat)) return true;
		return false;
	}

	private String getDisplayType() {
		String s = ConfigUtils.getString(Type.LANGUAGE, "item_display_types." + name().toLowerCase());
		return s == null ? getType() : s;
	}

	public static List<ItemType> getEnchantableTypes() {
		return Arrays.asList(ALL, ARMOR, AXES, BOOK, BOOTS, BOW, CARROT_ON_A_STICK, CHAINMAIL_ARMOR, CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS, CHESTPLATES, CROSSBOW, CUSTOM, DIAMOND_ARMOR, DIAMOND_AXE, DIAMOND_BOOTS, DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_HOE, DIAMOND_LEGGINGS, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD, DIAMOND_TOOLS, ELYTRA, ENCHANTABLE, FISHING_ROD, FISHING_STICK, FLINT_AND_STEEL, GOLDEN_ARMOR, GOLDEN_AXE, GOLDEN_BOOTS, GOLDEN_CHESTPLATE, GOLDEN_HELMET, GOLDEN_HOE, GOLDEN_LEGGINGS, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD, GOLDEN_TOOLS, HELMETS, HOES, IRON_ARMOR, IRON_AXE, IRON_BOOTS, IRON_CHESTPLATE, IRON_HELMET, IRON_HOE, IRON_LEGGINGS, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD, IRON_TOOLS, LEATHER_ARMOR, LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS, LEGGINGS, MELEE, MISC, NETHERITE_ARMOR, NETHERITE_AXE, NETHERITE_BOOTS, NETHERITE_CHESTPLATE, NETHERITE_HELMET, NETHERITE_HOE, NETHERITE_LEGGINGS, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD, NETHERITE_TOOLS, OTHER, PICKAXES, RANGED, SHEARS, SHIELD, SHOVELS, STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD, STONE_TOOLS, SWORDS, TOOLS, TRIDENT, TURTLE_HELMET, WARPED_FUNGUS_ON_A_STICK, WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD, WOODEN_TOOLS);
	}

	public static List<ItemType> getAnvilableTypes() {
		return Arrays.asList(BLACK_SHULKER_BOX, BLUE_SHULKER_BOX, BOOK, BOW, BROWN_SHULKER_BOX, CARROT_ON_A_STICK, CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS, CROSSBOW, CUSTOM, CYAN_SHULKER_BOX, DIAMOND_AXE, DIAMOND_BOOTS, DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_HOE, DIAMOND_LEGGINGS, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD, ELYTRA, ENCHANTED_BOOK, FISHING_ROD, FLINT_AND_STEEL, GOLDEN_AXE, GOLDEN_BOOTS, GOLDEN_CHESTPLATE, GOLDEN_HELMET, GOLDEN_HOE, GOLDEN_LEGGINGS, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD, GRAY_SHULKER_BOX, GREEN_SHULKER_BOX, IRON_AXE, IRON_BOOTS, IRON_CHESTPLATE, IRON_HELMET, IRON_HOE, IRON_LEGGINGS, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD, LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS, LIGHT_BLUE_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, LIME_SHULKER_BOX, MAGENTA_SHULKER_BOX, NETHERITE_AXE, NETHERITE_BOOTS, NETHERITE_CHESTPLATE, NETHERITE_HELMET, NETHERITE_HOE, NETHERITE_LEGGINGS, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD, ORANGE_SHULKER_BOX, PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX, SHEARS, SHIELD, STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD, TRIDENT, TURTLE_HELMET, WARPED_FUNGUS_ON_A_STICK, WHITE_SHULKER_BOX, WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD, YELLOW_SHULKER_BOX);
	}
	
	public static List<ItemType> getUniqueEnchantableTypes(){
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
			if (type.name().equals(data.getMaterial().name())) return type;
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

	public ItemType setCustomType(CustomItemType customType) {
		this.customType = customType;
		return this;
	}

	public ItemType setCustomString(String customTypeName) {
		this.customTypeName = customTypeName;
		return this;
	}

	public CustomItemType getCustomType() {
		return customType;
	}

	public String getCustomString() {
		return customTypeName;
	}

	public static String itemTypeToString(ItemType type) {
		if (type.getCustomType() == null) return type.getType();
		else if (type.getCustomType() == CustomItemType.VANILLA) return "minecraft:" + type.getCustomString();
		else
			return type.getCustomString();
	}

	public static List<String> itemTypesToStrings(List<ItemType> types) {
		List<String> typeStrings = new ArrayList<String>();

		for(ItemType type: types)
			typeStrings.add(itemTypeToString(type));
		return typeStrings;
	}

	public static ItemType createCustomType(ItemData data) {
		String s = "minecraft:" + data.getMaterial().name();
		if (data.getMMOType() != null && data.getMMOTypeSet() != null) s = "mmoitems:" + data.getMMOType() + ":" + data.getMMOTypeSet();
		return ItemType.CUSTOM.setCustomType(CustomItemType.get(s.toUpperCase())).setCustomString(s.toUpperCase());
	}

}
