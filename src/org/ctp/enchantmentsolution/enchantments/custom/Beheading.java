package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Beheading extends CustomEnchantment{
	
	public Beheading() {
		super("Beheading", 10, 5, 20, 12, 20, 1, 3, 3, Weight.RARE, "Adds a chance to drop mob heads on death.");
		addDefaultDisplayName(Language.GERMAN, "Beheading");
		addDefaultDescription(Language.GERMAN, "Fügt dem Tod die Chance hinzu, Mobköpfe abzulegen.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "斩首");
		addDefaultDescription(Language.CHINA_SIMPLE, "在击杀怪物时有几率获得怪物的头.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.BEHEADING;
	}

	@Override
	public String getName() {
		return "beheading";
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
		return Arrays.asList();
	}

}
