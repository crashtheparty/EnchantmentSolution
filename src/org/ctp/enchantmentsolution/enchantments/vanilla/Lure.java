package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Lure extends CustomEnchantment{
	
	public Lure() {
		setDefaultDisplayName("Lure");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		setDefaultDescription("Increases rate of fish biting your hook while fishing.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LURE;
	}

	@Override
	public String getName() {
		return "lure";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
