package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class Fortune extends CustomEnchantment{
	
	public Fortune() {
		setDefaultDisplayName("Fortune");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(6);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(9);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public String getName() {
		return "fortune";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LOOT_BONUS_BLOCKS;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.HOES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.HOES);
	}

	@Override
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.SILK_TOUCH));
	}

	@Override
	public String getDescription() {
		return "Increases block drops.";
	}

}
