package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class SweepingEdge extends CustomEnchantment{
	
	public SweepingEdge() {
		setDefaultDisplayName("Sweeping Edge");
		setDefaultFiftyConstant(-7);
		setDefaultThirtyConstant(-4);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(9);
		setDefaultFiftyMaxConstant(26);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.SWEEPING_EDGE;
	}

	@Override
	public String getName() {
		return "sweeping_edge";
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
		return Arrays.asList(this);
	}

	@Override
	public String getDescription() {
		return "Increases sweeping attack damage.";
	}

}
