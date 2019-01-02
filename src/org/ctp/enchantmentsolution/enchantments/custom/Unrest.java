package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Unrest extends CustomEnchantment{

	public Unrest() {
		setDefaultDisplayName("Unrest");
		setDefaultFiftyConstant(15);
		setDefaultThirtyConstant(1);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(60);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(15);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		setDefaultDescription("Night vision at the cost of more phantom spawning.");
	}

	@Override
	public String getName() {
		return "unrest";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.UNREST;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.WATER_WORKER, DefaultEnchantments.NO_REST);
	}
}
