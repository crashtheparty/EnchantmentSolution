package org.ctp.enchantmentsolution.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile.ItemBreakFileType;

public enum ItemBreakType {
	ALL(), WOODEN_AXE(), WOODEN_SHOVEL(), WOODEN_PICKAXE(), WOODEN_HOE(), GOLDEN_AXE(WOODEN_AXE), GOLDEN_SHOVEL(WOODEN_SHOVEL), GOLDEN_PICKAXE(WOODEN_PICKAXE),
	GOLDEN_HOE(WOODEN_HOE), STONE_AXE(GOLDEN_AXE), STONE_SHOVEL(GOLDEN_SHOVEL), STONE_PICKAXE(GOLDEN_PICKAXE), STONE_HOE(GOLDEN_HOE), IRON_AXE(STONE_AXE),
	IRON_SHOVEL(STONE_SHOVEL), IRON_PICKAXE(STONE_PICKAXE), IRON_HOE(STONE_HOE), DIAMOND_AXE(IRON_AXE), DIAMOND_SHOVEL(IRON_SHOVEL),
	DIAMOND_PICKAXE(IRON_PICKAXE), DIAMOND_HOE(IRON_HOE), NETHERITE_AXE(DIAMOND_AXE), NETHERITE_SHOVEL(DIAMOND_SHOVEL), NETHERITE_PICKAXE(DIAMOND_PICKAXE),
	NETHERITE_HOE(DIAMOND_HOE);

	private final ItemBreakType subType;

	ItemBreakType() {
		this(null);
	}

	ItemBreakType(ItemBreakType subType) {
		this.subType = subType;
	}

	public List<Material> getBreakTypes() {
		return ItemBreakFile.getFile(ItemBreakFileType.BREAK, this).getMaterials(false);
	}

	public List<Material> getSilkBreakTypes() {
		return ItemBreakFile.getFile(ItemBreakFileType.SILK_TOUCH, this).getMaterials(false);
	}

	public List<Material> getFortuneBreakTypes() {
		return ItemBreakFile.getFile(ItemBreakFileType.FORTUNE, this).getMaterials(false);
	}

	public ItemBreakType getSubType() {
		return subType;
	}

	public static List<Material> getBasicTypes(ItemBreakFileType fileType) {
		List<Material> materials = new ArrayList<Material>();
		List<ItemBreakType> types = Arrays.asList(ALL, WOODEN_AXE, WOODEN_SHOVEL, WOODEN_HOE);
		for(ItemBreakType type: types)
			for(Material m: ItemBreakFile.getFile(fileType, type).getMaterials(true))
				if (!materials.contains(m)) materials.add(m);
		return materials;
	}

	public static List<Material> getDiamondPickaxeBlocks() {
		return ItemBreakFile.getFile(ItemBreakFileType.BREAK, DIAMOND_PICKAXE).getMaterials(true);
	}

	public static ItemBreakType getType(Material type) {
		for(ItemBreakType breakType: ItemBreakType.values())
			if (type.name().equals(breakType.name())) return breakType;
		return null;
	}
}
