package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Efficiency extends CustomEnchantment{
	
	public Efficiency() {
		setDefaultDisplayName("Efficiency");
		setDefaultFiftyConstant(-11);
		setDefaultThirtyConstant(-9);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(45);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
	}

	@Override
	public String getName() {
		return "efficiency";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DIG_SPEED;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.SHEARS);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this);
	}

	@Override
	public String getDescription() {
		return "Increases mining speed." + 
				StringUtils.LF + 
				"One must use the proper tool for a block in order to receive the speed. Does not matter if you mine it with the incorrect tier.";
	}

}
