package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Mending extends CustomEnchantment{
	
	public Mending() {
		addDefaultDisplayName("Mending");
		addDefaultDisplayName(Language.GERMAN, "Reparatur");
		setTreasure(true);
		setDefaultFiftyConstant(25);
		setDefaultThirtyConstant(25);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(75);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Repair durability with experience.");
		addDefaultDescription(Language.GERMAN, "Haltbarkeit mit Erfahrung reparieren.");
	}

	@Override
	public String getName() {
		return "mending";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.MENDING;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ENCHANTABLE);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ALL);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.ARROW_INFINITE);
	}
}
