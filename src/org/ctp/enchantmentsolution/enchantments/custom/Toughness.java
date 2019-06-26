package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Toughness extends CustomEnchantment{
	
	public Toughness() {
		addDefaultDisplayName("Toughness");
		addDefaultDisplayName(Language.GERMAN, "Rüstungshärte");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(16);
		setDefaultThirtyModifier(11);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Increases armor toughness.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Rüstungsstärke.");
	}

	@Override
	public String getName() {
		return "toughness";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TOUGHNESS;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
