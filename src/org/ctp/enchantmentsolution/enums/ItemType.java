package org.ctp.enchantmentsolution.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;

public enum ItemType {
	HELMETS("helmets"), CHESTPLATES("chestplates"), LEGGINGS("leggings"), BOOTS("boots"), SWORDS("swords"),
	PICKAXES("pickaxes"), SHOVELS("shovels"), AXES("axes"), HOES("hoes"), BOW("bow"), SHIELD("shield"),
	FISHING_ROD("fishing_rod"), SHEARS("shears"), FLINT_AND_STEEL("flint_and_steel"),
	CARROT_ON_A_STICK("carrot_on_a_stick"), ELYTRA("elytra"), TRIDENT("trident"), RANGED("ranged"), ARMOR("armor"),
	TOOLS("tools"), MELEE("melee"), MISC("misc"), WOODEN_TOOLS("wooden_tools"), STONE_TOOLS("stone_tools"),
	IRON_TOOLS("iron_tools"), GOLDEN_TOOLS("golden_tools"), DIAMOND_TOOLS("diamond_tools"),
	LEATHER_ARMOR("leather_armor"), GOLDEN_ARMOR("golden_armor"), CHAINMAIL_ARMOR("chainmail_armor"),
	IRON_ARMOR("iron_armor"), DIAMOND_ARMOR("diamond_armor"), CROSSBOW("crossbow"), BOOK("book"), ALL("all"),
	ENCHANTABLE("enchantable"), TURTLE_HELMET("turtle_helmet"), SHULKER_BOXES("shulker_boxes"), NONE("none");

	private String type, display;
	private List<Material> itemTypes;

	ItemType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public List<Material> getItemTypes() {
		if (itemTypes == null) {
			itemTypes = getItemTypes(getType());
		}
		return itemTypes;
	}

	public String getDisplayName() {
		if (display == null) {
			display = getDisplayType();
		}
		return display;
	}

	public static String getUnlocalizedName(Material material) {
		return (material.isBlock() ? "block" : "item") + ".minecraft." + material.name().toLowerCase();
	}

	public Map<Material, String> getUnlocalizedNames() {
		Map<Material, String> names = new HashMap<Material, String>();
		for(Material material: getItemTypes()) {
			names.put(material, getUnlocalizedName(material));
		}
		return names;
	}

	public static boolean hasItemType(Material mat) {
		for(ItemType type: values()) {
			if (type.getItemTypes().contains(mat)) {
				return true;
			}
		}
		return false;
	}

	private String getDisplayType() {
		return ConfigUtils.getString(Type.LANGUAGE, "item_display_types." + name().toLowerCase());
	}

	private List<String> getItemStrings(String type) {
		List<String> itemTypes = new ArrayList<String>();
		itemTypes.addAll(Arrays.asList("BOOK", "ENCHANTED_BOOK"));
		if (ALL.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(ARMOR.getType()));
			itemTypes.addAll(getItemStrings(TOOLS.getType()));
			itemTypes.addAll(getItemStrings(MELEE.getType()));
			itemTypes.addAll(getItemStrings(RANGED.getType()));
			itemTypes.addAll(getItemStrings(MISC.getType()));
			return itemTypes;
		} else if (ARMOR.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(HELMETS.getType()));
			itemTypes.addAll(getItemStrings(CHESTPLATES.getType()));
			itemTypes.addAll(getItemStrings(LEGGINGS.getType()));
			itemTypes.addAll(getItemStrings(BOOTS.getType()));
			return itemTypes;
		} else if (AXES.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_AXE", "GOLDEN_AXE", "IRON_AXE", "STONE_AXE", "WOODEN_AXE"));
			return itemTypes;
		} else if (BOOK.getType().equals(type)) {
			return itemTypes;
		} else if (BOOTS.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("DIAMOND_BOOTS", "CHAINMAIL_BOOTS", "GOLDEN_BOOTS", "IRON_BOOTS", "LEATHER_BOOTS"));
			return itemTypes;
		} else if (BOW.getType().equals(type)) {
			itemTypes.add("BOW");
			return itemTypes;
		} else if (CARROT_ON_A_STICK.getType().equals(type)) {
			itemTypes.add("CARROT_ON_A_STICK");
			return itemTypes;
		} else if (CHAINMAIL_ARMOR.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("CHAINMAIL_HELMET", "CHAINMAIL_CHESTPLATE", "CHAINMAIL_LEGGINGS", "CHAINMAIL_BOOTS"));
			return itemTypes;
		} else if (CHESTPLATES.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_CHESTPLATE", "CHAINMAIL_CHESTPLATE", "GOLDEN_CHESTPLATE",
			"IRON_CHESTPLATE", "LEATHER_CHESTPLATE"));
			return itemTypes;
		} else if (CROSSBOW.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("CROSSBOW"));
			return itemTypes;
		} else if (DIAMOND_ARMOR.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("DIAMOND_HELMET", "DIAMOND_CHESTPLATE", "DIAMOND_LEGGINGS", "DIAMOND_BOOTS"));
			return itemTypes;
		} else if (DIAMOND_TOOLS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_AXE", "DIAMOND_SWORD", "DIAMOND_SHOVEL", "DIAMOND_PICKAXE"));
			return itemTypes;
		} else if (ELYTRA.getType().equals(type)) {
			itemTypes.add("ELYTRA");
			return itemTypes;
		} else if (ENCHANTABLE.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(ARMOR.getType()));
			itemTypes.addAll(getItemStrings(TOOLS.getType()));
			itemTypes.addAll(getItemStrings(MELEE.getType()));
			itemTypes.addAll(getItemStrings(RANGED.getType()));
			itemTypes.addAll(getItemStrings(SHIELD.getType()));
			itemTypes.addAll(getItemStrings(FISHING_ROD.getType()));
			itemTypes.addAll(getItemStrings(ELYTRA.getType()));
			itemTypes.addAll(getItemStrings(HOES.getType()));
			return itemTypes;
		} else if (FISHING_ROD.getType().equals(type)) {
			itemTypes.add("FISHING_ROD");
			return itemTypes;
		} else if (FLINT_AND_STEEL.getType().equals(type)) {
			itemTypes.add("FLINT_AND_STEEL");
			return itemTypes;
		} else if (GOLDEN_ARMOR.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("GOLDEN_HELMET", "GOLDEN_CHESTPLATE", "GOLDEN_LEGGINGS", "GOLDEN_BOOTS"));
			return itemTypes;
		} else if (GOLDEN_TOOLS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("GOLDEN_AXE", "GOLDEN_SWORD", "GOLDEN_SHOVEL", "GOLDEN_PICKAXE"));
			return itemTypes;
		} else if (HELMETS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_HELMET", "CHAINMAIL_HELMET", "GOLDEN_HELMET", "IRON_HELMET",
			"LEATHER_HELMET", "TURTLE_HELMET"));
			return itemTypes;
		} else if (HOES.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_HOE", "GOLDEN_HOE", "IRON_HOE", "STONE_HOE", "WOODEN_HOE"));
			return itemTypes;
		} else if (IRON_ARMOR.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("IRON_HELMET", "IRON_CHESTPLATE", "IRON_LEGGINGS", "IRON_BOOTS"));
			return itemTypes;
		} else if (IRON_TOOLS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("IRON_AXE", "IRON_SWORD", "IRON_SHOVEL", "IRON_PICKAXE"));
			return itemTypes;
		} else if (LEATHER_ARMOR.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS", "LEATHER_BOOTS"));
			return itemTypes;
		} else if (LEGGINGS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("DIAMOND_LEGGINGS", "CHAINMAIL_LEGGINGS", "GOLDEN_LEGGINGS", "IRON_LEGGINGS",
			"LEATHER_LEGGINGS"));
			return itemTypes;
		} else if (MELEE.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(AXES.getType()));
			itemTypes.addAll(getItemStrings(SWORDS.getType()));
			return itemTypes;
		} else if (MISC.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(SHIELD.getType()));
			itemTypes.addAll(getItemStrings(FISHING_ROD.getType()));
			itemTypes.addAll(getItemStrings(FLINT_AND_STEEL.getType()));
			itemTypes.addAll(getItemStrings(CARROT_ON_A_STICK.getType()));
			itemTypes.addAll(getItemStrings(ELYTRA.getType()));
			itemTypes.addAll(getItemStrings(SHEARS.getType()));
			itemTypes.addAll(getItemStrings(HOES.getType()));
			return itemTypes;
		} else if (PICKAXES.getType().equals(type)) {
			itemTypes.addAll(
			Arrays.asList("DIAMOND_PICKAXE", "GOLDEN_PICKAXE", "IRON_PICKAXE", "STONE_PICKAXE", "WOODEN_PICKAXE"));
			return itemTypes;
		} else if (RANGED.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(BOW.getType()));
			itemTypes.addAll(getItemStrings(CROSSBOW.getType()));
			itemTypes.addAll(getItemStrings(TRIDENT.getType()));
			return itemTypes;
		} else if (SHEARS.getType().equals(type)) {
			itemTypes.add("SHEARS");
			return itemTypes;
		} else if (SHIELD.getType().equals(type)) {
			itemTypes.add("SHIELD");
			return itemTypes;
		} else if (SHOVELS.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("DIAMOND_SHOVEL", "GOLDEN_SHOVEL", "IRON_SHOVEL", "STONE_SHOVEL", "WOODEN_SHOVEL"));
			return itemTypes;
		} else if (STONE_TOOLS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("STONE_AXE", "STONE_SWORD", "STONE_SHOVEL", "STONE_PICKAXE"));
			return itemTypes;
		} else if (SWORDS.getType().equals(type)) {
			itemTypes
			.addAll(Arrays.asList("DIAMOND_SWORD", "GOLDEN_SWORD", "IRON_SWORD", "STONE_SWORD", "WOODEN_SWORD"));
			return itemTypes;
		} else if (TOOLS.getType().equals(type)) {
			itemTypes.addAll(getItemStrings(PICKAXES.getType()));
			itemTypes.addAll(getItemStrings(AXES.getType()));
			itemTypes.addAll(getItemStrings(SHOVELS.getType()));
			return itemTypes;
		} else if (TRIDENT.getType().equals(type)) {
			itemTypes.add("TRIDENT");
			return itemTypes;
		} else if (TURTLE_HELMET.getType().equals(type)) {
			itemTypes.add("TURTLE_HELMET");
			return itemTypes;
		} else if (WOODEN_TOOLS.getType().equals(type)) {
			itemTypes.addAll(Arrays.asList("WOODEN_AXE", "WOODEN_SWORD", "WOODEN_SHOVEL", "WOODEN_PICKAXE"));
			return itemTypes;
		}
		return null;
	}

	private List<Material> getItemTypes(String type) {
		List<Material> materials = new ArrayList<Material>();

		for(String s: getItemStrings(type)) {
			try {
				materials.add(Material.valueOf(s));
			} catch (Exception ex) {

			}
		}

		return materials;
	}

}
