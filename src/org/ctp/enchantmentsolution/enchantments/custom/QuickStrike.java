package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class QuickStrike extends CustomEnchantment{
	
	public QuickStrike() {
		addDefaultDisplayName("Quick Strike");
		addDefaultDisplayName(Language.GERMAN, "Schneller Schlag");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(6);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(9);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases attack speed.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Angriffsgeschwindigkeit");
	}
	
	@Override
	public String getName() {
		return "quick_strike";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.QUICK_STRIKE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, 
				Enchantment.DAMAGE_UNDEAD, Enchantment.FIRE_ASPECT, DefaultEnchantments.SHOCK_ASPECT);
	}
}
