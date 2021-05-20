package org.ctp.enchantmentsolution.enums;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.utils.CrashConfigUtils;

public enum ItemBreakType {
	NETHERITE_AXE(), NETHERITE_SHOVEL(), NETHERITE_PICKAXE(), NETHERITE_HOE(), DIAMOND_AXE(), DIAMOND_SHOVEL(), DIAMOND_PICKAXE(), DIAMOND_HOE(),
	IRON_AXE(), IRON_SHOVEL(), IRON_PICKAXE(), IRON_HOE(), GOLDEN_AXE(), GOLDEN_SHOVEL(), GOLDEN_PICKAXE(), GOLDEN_HOE(), STONE_AXE(), STONE_SHOVEL(),
	STONE_PICKAXE(), STONE_HOE(), WOODEN_AXE(), WOODEN_SHOVEL(), WOODEN_PICKAXE(), WOODEN_HOE();

	private List<Material> breakTypes, silkBreakTypes, fortuneBreakTypes;
	private static HashMap<String, HashMap<String, List<String>>> TYPES;

	ItemBreakType() {
		breakTypes = getItemBreakMaterials("");
		silkBreakTypes = getItemBreakMaterials("silk");
		fortuneBreakTypes = getItemBreakMaterials("fortune");
	}

	public List<String> getDiamondPickaxeBlocks() {
		return get("break.yml", "diamond_pickaxe");
	}

	public List<Material> getBreakTypes() {
		return breakTypes;
	}

	public List<Material> getSilkBreakTypes() {
		return silkBreakTypes;
	}

	public List<Material> getFortuneBreakTypes() {
		return fortuneBreakTypes;
	}

	public static ItemBreakType getType(Material type) {
		for(ItemBreakType breakType: ItemBreakType.values())
			if (type.name().equals(breakType.name())) return breakType;
		return null;
	}

	public static List<Material> allBreakTypes() {
		List<Material> itemTypes = new ArrayList<Material>();
		for(ItemBreakType type: ItemBreakType.values())
			itemTypes.addAll(type.getBreakTypes());
		return itemTypes;
	}

	private List<Material> getItemBreakMaterials(String type) {
		List<Material> materials = new ArrayList<Material>();
		List<String> strings = type.equals("silk") ? getSilkTouchStrings() : type.equals("fortune") ? getFortuneStrings() : getItemBreakStrings();
		for(String s: strings)
			try {
				materials.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception ex) {

			}

		return materials;
	}

	private List<String> fromTool(String file) {
		List<String> itemTypes = new ArrayList<String>();
		itemTypes.addAll(get(file, "all"));
		switch (name()) {
			case "NETHERITE_PICKAXE":
				itemTypes.addAll(get(file, "netherite_pickaxe"));
			case "DIAMOND_PICKAXE":
				itemTypes.addAll(get(file, "diamond_pickaxe"));
			case "IRON_PICKAXE":
				itemTypes.addAll(get(file, "iron_pickaxe"));
			case "STONE_PICKAXE":
				itemTypes.addAll(get(file, "stone_pickaxe"));
			case "GOLDEN_PICKAXE":
				itemTypes.addAll(get(file, "golden_pickaxe"));
			case "WOODEN_PICKAXE":
				itemTypes.addAll(get(file, "wooden_pickaxe"));
				return itemTypes;
			case "NETHERITE_AXE":
				itemTypes.addAll(get(file, "netherite_axe"));
			case "DIAMOND_AXE":
				itemTypes.addAll(get(file, "diamond_axe"));
			case "IRON_AXE":
				itemTypes.addAll(get(file, "iron_axe"));
			case "STONE_AXE":
				itemTypes.addAll(get(file, "stone_axe"));
			case "GOLDEN_AXE":
				itemTypes.addAll(get(file, "golden_axe"));
			case "WOODEN_AXE":
				itemTypes.addAll(get(file, "wooden_axe"));
				return itemTypes;
			case "NETHERITE_SHOVEL":
				itemTypes.addAll(get(file, "netherite_shovel"));
			case "DIAMOND_SHOVEL":
				itemTypes.addAll(get(file, "diamond_shovel"));
			case "IRON_SHOVEL":
				itemTypes.addAll(get(file, "iron_shovel"));
			case "STONE_SHOVEL":
				itemTypes.addAll(get(file, "stone_shovel"));
			case "GOLDEN_SHOVEL":
				itemTypes.addAll(get(file, "golden_shovel"));
			case "WOODEN_SHOVEL":
				itemTypes.addAll(get(file, "wooden_shovel"));
				return itemTypes;
			case "NETHERITE_HOE":
				itemTypes.addAll(get(file, "netherite_hoe"));
			case "DIAMOND_HOE":
				itemTypes.addAll(get(file, "diamond_hoe"));
			case "IRON_HOE":
				itemTypes.addAll(get(file, "iron_hoe"));
			case "STONE_HOE":
				itemTypes.addAll(get(file, "stone_hoe"));
			case "GOLDEN_HOE":
				itemTypes.addAll(get(file, "golden_hoe"));
			case "WOODEN_HOE":
				itemTypes.addAll(get(file, "wooden_hoe"));
				return itemTypes;
			default:
				break;
		}
		return itemTypes;
	}

	public List<Material> getBasicTypes() {
		List<String> itemTypes = new ArrayList<String>();
		List<Material> materials = new ArrayList<Material>();
		String file = "break.yml";
		itemTypes.addAll(get(file, "all"));
		itemTypes.addAll(get(file, "wooden_axe"));
		itemTypes.addAll(get(file, "wooden_shovel"));
		itemTypes.addAll(get(file, "wooden_hoe"));
		for(String s: itemTypes)
			try {
				materials.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception ex) {

			}
		return materials;
	}

	public List<String> getAllTypes() {
		List<String> itemTypes = new ArrayList<String>();
		String file = "break.yml";
		itemTypes.addAll(get(file, "wooden_pickaxe"));
		itemTypes.addAll(get(file, "wooden_axe"));
		itemTypes.addAll(get(file, "wooden_shovel"));
		itemTypes.addAll(get(file, "wooden_hoe"));

		itemTypes.addAll(get(file, "golden_pickaxe"));
		itemTypes.addAll(get(file, "golden_axe"));
		itemTypes.addAll(get(file, "golden_shovel"));
		itemTypes.addAll(get(file, "golden_hoe"));

		itemTypes.addAll(get(file, "stone_pickaxe"));
		itemTypes.addAll(get(file, "stone_axe"));
		itemTypes.addAll(get(file, "stone_shovel"));
		itemTypes.addAll(get(file, "stone_hoe"));

		itemTypes.addAll(get(file, "iron_pickaxe"));
		itemTypes.addAll(get(file, "iron_axe"));
		itemTypes.addAll(get(file, "iron_shovel"));
		itemTypes.addAll(get(file, "iron_hoe"));

		itemTypes.addAll(get(file, "diamond_pickaxe"));
		itemTypes.addAll(get(file, "diamond_axe"));
		itemTypes.addAll(get(file, "diamond_shovel"));
		itemTypes.addAll(get(file, "diamond_hoe"));

		itemTypes.addAll(get(file, "netherite_pickaxe"));
		itemTypes.addAll(get(file, "netherite_axe"));
		itemTypes.addAll(get(file, "netherite_shovel"));
		itemTypes.addAll(get(file, "netherite_hoe"));

		return itemTypes;
	}

	private List<String> getItemBreakStrings() {
		return fromTool("break.yml");
	}

	private List<String> getSilkTouchStrings() {
		return fromTool("silk_touch.yml");
	}

	public List<String> getFortuneStrings() {
		return fromTool("fortune.yml");
	}

	private List<String> get(String fileName, String location) {
		if (TYPES == null) TYPES = new HashMap<String, HashMap<String, List<String>>>();
		if (TYPES.containsKey(fileName)) {
			HashMap<String, List<String>> fromFile = TYPES.get(location);
			if (fromFile == null) fromFile = new HashMap<String, List<String>>();
			if (fromFile.containsKey(location)) return fromFile.get(location);
		}
		List<String> value = getFromFile(fileName, location);
		HashMap<String, List<String>> fromFile = TYPES.get(location);
		if (fromFile == null) fromFile = new HashMap<String, List<String>>();
		fromFile.put(location, value);
		TYPES.put(fileName, fromFile);
		return value;
	}

	private List<String> getFromFile(String fileName, String location) {
		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/blocks/" + fileName);
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();
		if (config.getStringList(location) == null) return new ArrayList<String>();
		return config.getStringList(location);
	}
}
