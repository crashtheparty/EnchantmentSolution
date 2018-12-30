package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class KnockUp extends CustomEnchantment{
	
	public KnockUp() {
		setDefaultDisplayName("Knockup");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.UNCOMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.KNOCKUP;
	}

	@Override
	public String getName() {
		return "knockup";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK));
	}

	@Override
	public String getDescription() {
		return "Increases knockback upwards.";
	}

}
