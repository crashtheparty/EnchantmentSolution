package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Sharpness extends CustomEnchantment{
	
	public Sharpness() {
		addDefaultDisplayName("Sharpness");
		addDefaultDisplayName(Language.GERMAN, "Schärfe");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(11);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Increases melee damage." + 
				"\n" + 
				"Adds 1 (half heart) extra damage for the first level, and 0.5 (half heart) for each additional level.");
		addDefaultDescription(Language.GERMAN, "Erhöht Nahkampfschaden." + 
				"\n" + 
				"Fügt 1 (halbes Herz) zusätzlichen Schaden für die erste Stufe und 0,5 (halbes Herz) für jede zusätzliche Stufe hinzu.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ALL;
	}

	@Override
	public String getName() {
		return "sharpness";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD);
	}
}
