package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Infinity extends CustomEnchantment{
	
	public Infinity() {
		setDefaultDisplayName("Infinity");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
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
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.MENDING));
	}

	@Override
	public String getDescription() {
		return "Shooting doesn't consume regular arrows.";
	}

}
