package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Riptide extends CustomEnchantment{
	
	public Riptide() {
		setDefaultDisplayName("Riptide");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(9);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(9);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}

	@Override
	public String getName() {
		return "riptide";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.RIPTIDE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, 
				DefaultEnchantments.getCustomEnchantment(Enchantment.CHANNELING), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY));
	}

	@Override
	public String getDescription() {
		return "Returns a thrown trident after it hits something.";
	}
}
