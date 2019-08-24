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
		super("Shock Aspect", -15, -10, 20, 20, 5, 1, 3, 2, Weight.RARE, "Has a chance to strike lightning on attacked mobs.");
		addDefaultDisplayName(Language.GERMAN, "Schock");
		addDefaultDescription(Language.GERMAN, "Hat eine Chance, angegriffene Mobs mit Blitzen zu treffen.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "雷击");
		addDefaultDescription(Language.CHINA_SIMPLE, "攻击时有几率雷击怪物.");
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
		return Arrays.asList(DefaultEnchantments.QUICK_STRIKE);
	}
}
