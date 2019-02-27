package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Wand extends CustomEnchantment{
	
	public Wand() {
		addDefaultDisplayName("Wand");
		addDefaultDisplayName(Language.GERMAN, "Zauberstab");
		setDefaultFiftyConstant(20);
		setDefaultThirtyConstant(10);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(35);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.VERY_RARE);
		setTreasure(true);
		addDefaultDescription("Places blocks from the offhand.");
		addDefaultDescription(Language.GERMAN, "Platziert Blöcke von der Nebenhand.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.WAND;
	}

	@Override
	public String getName() {
		return "wand";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CARROT_ON_A_STICK);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
