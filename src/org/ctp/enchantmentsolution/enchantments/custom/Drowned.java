package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Drowned extends CustomEnchantment{
	
	public Drowned() {
		addDefaultDisplayName("Drowned");
		addDefaultDisplayName(Language.GERMAN, "Ertrinken");
		setDefaultFiftyConstant(10);
		setDefaultThirtyConstant(0);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(12);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Drown non-water mobs for a short time.");
		addDefaultDescription(Language.GERMAN, "Ertrinken Sie nicht Wasser-Mobs für kurze Zeit.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.DROWNED;
	}

	@Override
	public String getName() {
		return "drowned";
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
