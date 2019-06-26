package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Armored extends CustomEnchantment{
	
	public Armored() {
		addDefaultDisplayName("Armored");
		addDefaultDisplayName(Language.GERMAN, "Gepanzert");
		setDefaultFiftyConstant(20);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Adds armor points for wearing elytra.");
		addDefaultDescription(Language.GERMAN, "Fügt Rüstungspunkte für das Tragen von Elytren hinzu.");
	}

	@Override
	public String getName() {
		return "armored";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.ARMORED;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
