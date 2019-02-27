package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Sniper extends CustomEnchantment{
	
	public Sniper() {
		addDefaultDisplayName("Sniper");
		addDefaultDisplayName(Language.GERMAN, "Sniper");
		setDefaultFiftyConstant(12);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(18);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(25);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases speed of projectiles.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Geschwindigkeit von Geschossen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SNIPER;
	}

	@Override
	public String getName() {
		return "sniper";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.RANGED);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.RANGED);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
