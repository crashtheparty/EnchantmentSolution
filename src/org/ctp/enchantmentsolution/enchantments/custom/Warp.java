package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Warp extends CustomEnchantment{
	
	public Warp() {
		setDefaultDisplayName("Warp");
		setDefaultFiftyConstant(-5);
		setDefaultThirtyConstant(-7);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(14);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.WARP;
	}

	@Override
	public String getName() {
		return "warp";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.LEGGINGS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.LEGGINGS);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this);
	}

	@Override
	public String getDescription() {
		return "Gives a chance of teleporting a small distance away on hit.";
	}

}
