package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ShockAspect extends CustomEnchantment{
	
	public ShockAspect() {
		addDefaultDisplayName("Shock Aspect");
		addDefaultDisplayName(Language.GERMAN, "Schock");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(5);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Has a chance to strike lightning on attacked mobs.");
		addDefaultDescription(Language.GERMAN, "Hat eine Chance, angegriffene Mobs mit Blitzen zu treffen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SHOCK_ASPECT;
	}
	
	@Override
	public String getName() {
		return "shock_aspect";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.AXES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
