package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Tank extends CustomEnchantment{
	
	public Tank() {
		setDefaultDisplayName("Tank");
		setDefaultFiftyConstant(10);
		setDefaultThirtyConstant(-5);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(15);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		setDefaultDescription("Gives additional unbreaking protection to armor.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TANK;
	}

	@Override
	public String getName() {
		return "tank";
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
