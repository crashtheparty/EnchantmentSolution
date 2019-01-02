package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class StoneThrow extends CustomEnchantment{
	
	public StoneThrow() {
		setDefaultDisplayName("Stone Throw");
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
		setDefaultDescription("Increases ranged damage against flying mobs." + 
				StringUtils.LF + 
				"Adds 40% * level + 20% damage against flying mobs.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.STONE_THROW;
	}

	@Override
	public String getName() {
		return "stone_throw";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
