package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Brine extends CustomEnchantment{
	
	public Brine() {
		super("Brine", 30, 25, 0, 0, 25, 1, 1, 1, Weight.RARE, "Doubles damage if opposting mob is below 50% health.");
		addDefaultDisplayName(Language.GERMAN, "Salzlake");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Verdoppelt den Schaden, wenn die gegnerische Menge weniger als 50% Gesundheit hat.");
	}

	@Override
	public String getName() {
		return "brine";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.BRINE;
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
