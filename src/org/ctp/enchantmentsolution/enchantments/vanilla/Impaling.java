package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Impaling extends CustomEnchantment{
	
	public Impaling() {
		setDefaultDisplayName("Impaling");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(-11);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.RARE);
	}

	@Override
	public String getName() {
		return "impaling";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.IMPALING;
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
		return Arrays.asList(this);
	}

	@Override
	public String getDescription() {
		return "Increases melee damage against aquatic mobs." + 
				StringUtils.LF + 
				"Adds 2.5 (half heart) extra damage for each additional level.";
	}
}
