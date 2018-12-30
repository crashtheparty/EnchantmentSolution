package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Unrest extends CustomEnchantment{

	public Unrest() {
		setDefaultDisplayName("Unrest");
		setDefaultFiftyConstant(0);
		setDefaultThirtyConstant(0);
		setDefaultFiftyModifier(10);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(25);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
	}

	@Override
	public String getName() {
		return "unrest";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.UNREST;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER),
				DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST));
	}

	@Override
	public String getDescription() {
		return "Night vision at the cost of more phantom spawning.";
	}

}
