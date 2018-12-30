package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class BlastProtection extends CustomEnchantment{
	
	public BlastProtection() {
		setDefaultDisplayName("Blast Protection");
		setDefaultFiftyConstant(-8);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(14);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(19);
		setDefaultThirtyMaxConstant(12);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public String getName() {
		return "blast_protection";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_EXPLOSIONS;
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
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE));
	}

	@Override
	public String getDescription() {
		return "Reduces explosion damage." + 
				StringUtils.LF + 
				"Also reduces explosion knockback by (15 × level)%. If multiple pieces have the enchantment, only the highest level's reduction is used.";
	}

}
