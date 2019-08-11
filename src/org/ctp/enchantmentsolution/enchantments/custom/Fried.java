package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Fried extends CustomEnchantment{
	
	public Fried() {
		super("Fried", 40, 15, 0, 0, 30, 1, 1, 1, Weight.RARE, "Cooks fish upon catching them.");
		addDefaultDisplayName(Language.GERMAN, "Fritieren");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Köche Fische, wenn sie gefangen werden.");
	}
	
	@Override 
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.FRIED;
	}
	
	@Override
	public String getName() {
		return "fried";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
