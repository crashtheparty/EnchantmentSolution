package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class NoRest extends CustomEnchantment{

	public NoRest() {
		addDefaultDisplayName("No Rest");
		addDefaultDisplayName(Language.GERMAN, "Keine Pause");
		setDefaultFiftyConstant(15);
		setDefaultThirtyConstant(1);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyStartLevel(15);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		addDefaultDescription("No phantoms will spawn around you.");
		addDefaultDescription(Language.GERMAN, "Es werden keine Phantome um Sie herum erscheinen.");
	}

	@Override
	public String getName() {
		return "no_rest";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.NO_REST;
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
		return Arrays.asList(DefaultEnchantments.UNREST, Enchantment.WATER_WORKER);
	}
}
