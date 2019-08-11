package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Angler extends CustomEnchantment{
	
	public Angler() {
		super("Angler", 0, 0, 10, 8, 1, 1, 5, 3, Weight.RARE, "Catch one additional fish per level if you caught a fish.");
		addDefaultDisplayName(Language.GERMAN, "Profiangler");
		addDefaultDescription(Language.GERMAN, "Fangen Sie einen zusätzlichen Fisch pro Level, wenn Sie einen Fisch fangen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.ANGLER;
	}

	@Override
	public String getName() {
		return "angler";
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
