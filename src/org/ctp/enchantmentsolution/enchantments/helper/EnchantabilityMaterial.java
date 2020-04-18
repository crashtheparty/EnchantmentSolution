package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public enum EnchantabilityMaterial {
	WOODEN_TOOLS(15, Material.WOODEN_PICKAXE, "calc.enchantability_values.wooden_tools", ItemType.WOODEN_TOOLS.getEnchantMaterials()),
	STONE_TOOLS(5, Material.STONE_PICKAXE, "calc.enchantability_values.stone_tools", ItemType.STONE_TOOLS.getEnchantMaterials()),
	GOLDEN_TOOLS(22, Material.GOLDEN_PICKAXE, "calc.enchantability_values.golden_tools", ItemType.GOLDEN_TOOLS.getEnchantMaterials()),
	IRON_TOOLS(14, Material.IRON_PICKAXE, "calc.enchantability_values.iron_tools", ItemType.IRON_TOOLS.getEnchantMaterials()),
	DIAMOND_TOOLS(10, Material.DIAMOND_PICKAXE, "calc.enchantability_values.diamond_tools", ItemType.DIAMOND_TOOLS.getEnchantMaterials()),
	LEATHER_ARMOR(15, Material.LEATHER_CHESTPLATE, "calc.enchantability_values.leather_armor", ItemType.LEATHER_ARMOR.getEnchantMaterials()),
	CHAINMAIL_ARMOR(12, Material.CHAINMAIL_CHESTPLATE, "calc.enchantability_values.chainmail_armor", ItemType.CHAINMAIL_ARMOR.getEnchantMaterials()),
	GOLDEN_ARMOR(25, Material.GOLDEN_CHESTPLATE, "calc.enchantability_values.golden_armor", ItemType.GOLDEN_ARMOR.getEnchantMaterials()),
	IRON_ARMOR(9, Material.IRON_CHESTPLATE, "calc.enchantability_values.iron_armor", ItemType.IRON_ARMOR.getEnchantMaterials()),
	DIAMOND_ARMOR(10, Material.DIAMOND_CHESTPLATE, "calc.enchantability_values.diamond_armor", ItemType.DIAMOND_ARMOR.getEnchantMaterials()),
	OTHER(1, Material.BOOK, "calc.enchantability_values.other", ItemType.OTHER.getEnchantMaterials());

	private final int enchantability;
	private final Material material;
	private final String display;
	private final List<ItemData> types;

	EnchantabilityMaterial(int enchantability, Material material, String display, List<ItemData> types) {
		this.enchantability = enchantability;
		this.material = material;
		this.display = display;
		this.types = types;
	}

	public int getEnchantability() {
		return enchantability;
	}

	public Material getMaterial() {
		return material;
	}

	public String getDisplay() {
		return ChatUtils.getMessage(ChatUtils.getCodes(), display);
	}

	public List<ItemData> getTypes() {
		return types;
	}

	public boolean containsType(Material material) {
		return ItemData.contains(types, material);
	}
}
