package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Sharpness extends CustomEnchantment{
	
	public Sharpness() {
		setDefaultDisplayName("Sharpness");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(11);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ALL;
	}

	@Override
	public String getName() {
		return "sharpness";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, 
				DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD));
	}

	@Override
	public String getDescription() {
		return "Increases melee damage." + 
				StringUtils.LF + 
				"Adds 1 (half heart) extra damage for the first level, and 0.5 (half heart) for each additional level.";
	}

}
