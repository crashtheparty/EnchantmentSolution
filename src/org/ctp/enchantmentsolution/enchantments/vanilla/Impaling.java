package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Impaling extends CustomEnchantment{
	
	public Impaling() {
		setDefaultDisplayName("Impaling");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(1);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.RARE);
		setDefaultDescription("Increases melee damage against aquatic mobs." + 
				StringUtils.LF + 
				"Adds 2.5 (half heart) extra damage for each additional level.");
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
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
