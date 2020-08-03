package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.List;

import org.bukkit.Material;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;

public enum EnchantabilityMaterial {
	WOODEN_TOOLS(15, "WOODEN_PICKAXE", "calc.enchantability_values.wooden_tools", ItemType.WOODEN_TOOLS.getEnchantMaterials()),
	STONE_TOOLS(5, "STONE_PICKAXE", "calc.enchantability_values.stone_tools", ItemType.STONE_TOOLS.getEnchantMaterials()),
	GOLDEN_TOOLS(22, "GOLDEN_PICKAXE", "calc.enchantability_values.golden_tools", ItemType.GOLDEN_TOOLS.getEnchantMaterials()),
	IRON_TOOLS(14, "IRON_PICKAXE", "calc.enchantability_values.iron_tools", ItemType.IRON_TOOLS.getEnchantMaterials()),
	DIAMOND_TOOLS(10, "DIAMOND_PICKAXE", "calc.enchantability_values.diamond_tools", ItemType.DIAMOND_TOOLS.getEnchantMaterials()),
	LEATHER_ARMOR(15, "LEATHER_CHESTPLATE", "calc.enchantability_values.leather_armor", ItemType.LEATHER_ARMOR.getEnchantMaterials()),
	CHAINMAIL_ARMOR(12, "CHAINMAIL_CHESTPLATE", "calc.enchantability_values.chainmail_armor", ItemType.CHAINMAIL_ARMOR.getEnchantMaterials()),
	GOLDEN_ARMOR(25, "GOLDEN_CHESTPLATE", "calc.enchantability_values.golden_armor", ItemType.GOLDEN_ARMOR.getEnchantMaterials()),
	IRON_ARMOR(9, "IRON_CHESTPLATE", "calc.enchantability_values.iron_armor", ItemType.IRON_ARMOR.getEnchantMaterials()),
	DIAMOND_ARMOR(10, "DIAMOND_CHESTPLATE", "calc.enchantability_values.diamond_armor", ItemType.DIAMOND_ARMOR.getEnchantMaterials()),
	NETHERITE_TOOLS(15, "NETHERITE_PICKAXE", "calc.enchantability_values.netherite_tools", ItemType.NETHERITE_TOOLS.getEnchantMaterials()),
	OTHER(1, "BOOK", "calc.enchantability_values.other", ItemType.OTHER.getEnchantMaterials()),
	NETHERITE_ARMOR(15, "NETHERITE_CHESTPLATE", "calc.enchantability_values.netherite_armor", ItemType.NETHERITE_ARMOR.getEnchantMaterials());

	private final int enchantability;
	private final MatData material;
	private final String display;
	private final List<ItemData> types;

	EnchantabilityMaterial(int enchantability, String material, String display, List<ItemData> types) {
		this.enchantability = enchantability;
		this.material = new MatData(material);
		this.display = display;
		this.types = types;
	}

	public int getEnchantability() {
		return enchantability;
	}

	public Material getMaterial() {
		return material.getMaterial();
	}

	public String getDisplay() {
		return Chatable.get().getMessage(ChatUtils.getCodes(), display);
	}

	public List<ItemData> getTypes() {
		return types;
	}

	public boolean containsType(Material material) {
		return ItemData.contains(types, material);
	}
}
