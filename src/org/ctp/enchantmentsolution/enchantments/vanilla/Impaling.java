package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Impaling extends CustomEnchantment{
	
	public Impaling() {
		addDefaultDisplayName("Impaling");
		addDefaultDisplayName(Language.GERMAN, "Harpune");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(1);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(8);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases melee damage against aquatic mobs." + 
				"\n" + 
				"Adds 2.5 (half heart) extra damage for each additional level.");
		addDefaultDescription(Language.GERMAN, "Erhöht Nahkampfschaden gegen Wassermobs." + 
				"\n" + 
				"Fügt für jede zusätzliche Stufe 2,5 (halbes Herz) zusätzlichen Schaden hinzu.");
	}

	@Override
	public String getName() {
		return "impaling";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.IMPALING;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
