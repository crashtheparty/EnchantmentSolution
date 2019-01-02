package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class HardBounce extends CustomEnchantment{

	public HardBounce() {
		setDefaultDisplayName("Hard Bounce");
		setDefaultFiftyConstant(-4);
		setDefaultThirtyConstant(-4);
		setDefaultFiftyModifier(14);
		setDefaultThirtyModifier(12);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(25);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.UNCOMMON);
		setDefaultDescription("Projectiles bounce back from the shield.");
	}
	
	@Override
	public String getName() {
		return "hard_bounce";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.HARD_BOUNCE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.IRON_DEFENSE);
	}

}
