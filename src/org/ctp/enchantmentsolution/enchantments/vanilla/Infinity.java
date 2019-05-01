package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Infinity extends CustomEnchantment{
	
	public Infinity() {
		addDefaultDisplayName("Infinity");
		addDefaultDisplayName(Language.GERMAN, "Unendlichkeit");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Shooting doesn't consume regular arrows.");
		addDefaultDescription(Language.GERMAN, "Beim Schieﬂen werden keine normalen Pfeile verbraucht.");
	}

	@Override
	public String getName() {
		return "infinity";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_INFINITE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.MENDING);
	}
}
