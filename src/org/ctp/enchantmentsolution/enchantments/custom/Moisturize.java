package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Moisturize extends CustomEnchantment{

	public Moisturize() {
		addDefaultDisplayName("Moisturize");
		addDefaultDisplayName(Language.GERMAN, "Befeuchten");
		setDefaultFiftyConstant(50);
		setDefaultThirtyConstant(35);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(25);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setMaxLevelOne(true);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Waters blocks by right-clicking them.");
		addDefaultDescription(Language.GERMAN, "Befeuchtet Blöcke durch Rechtsklick.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MOISTURIZE;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SHEARS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "moisturize";
	}

}
