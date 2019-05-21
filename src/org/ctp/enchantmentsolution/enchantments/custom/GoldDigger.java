package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class GoldDigger extends CustomEnchantment{
	
	public GoldDigger() {
		addDefaultDisplayName("Gold Digger");
		addDefaultDisplayName(Language.GERMAN, "Goldgräber");
		setDefaultFiftyConstant(-2);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(11);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Earn experience and gold nuggets for breaking crops.");
		addDefaultDescription(Language.GERMAN, "Sammeln Sie Erfahrung und Gold-Nuggets für das Ernten.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.GOLD_DIGGER;
	}

	@Override
	public String getName() {
		return "gold_digger";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
