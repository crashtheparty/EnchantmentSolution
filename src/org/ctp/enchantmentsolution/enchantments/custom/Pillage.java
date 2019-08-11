package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Pillage extends CustomEnchantment{
	
	public Pillage() {
		super("Pillage", 7, 6, 11, 9, 1, 1, 5, 3, Weight.RARE, "Increases mob drops.");
		addDefaultDisplayName(Language.GERMAN, "Plündern");
		addDefaultDescription(Language.GERMAN, "Erhöht Mob-Tropfen.");
	}
	
	@Override
	public String getName() {
		return "pillage";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.PILLAGE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
