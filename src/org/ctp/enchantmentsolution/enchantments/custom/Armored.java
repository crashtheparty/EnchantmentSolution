package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Armored extends CustomEnchantment{
	
	public Armored() {
		super("Armored", 20, 5, 15, 10, 30, 1, 3, 3, Weight.RARE, "Adds armor points for wearing elytra.");
		addDefaultDisplayName(Language.GERMAN, "Gepanzert");
		addDefaultDescription(Language.GERMAN, "Fügt Rüstungspunkte für das Tragen von Elytren hinzu.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "守护之翼");
		addDefaultDescription(Language.CHINA_SIMPLE, "穿戴鞘翅增加护甲值.");
	}

	@Override
	public String getName() {
		return "armored";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.ARMORED;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
