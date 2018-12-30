package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class SilkTouch extends CustomEnchantment{
	
	public SilkTouch() {
		setDefaultDisplayName("Silk Touch");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(15);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.SILK_TOUCH;
	}

	@Override
	public String getName() {
		return "silk_touch";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, 
				DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 
				DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SMELTERY));
	}

	@Override
	public String getDescription() {
		return "Mined blocks drop themselves instead of the usual items." + 
				StringUtils.LF + 
				"Allows collection of blocks that are normally unobtainable.";
	}

}
