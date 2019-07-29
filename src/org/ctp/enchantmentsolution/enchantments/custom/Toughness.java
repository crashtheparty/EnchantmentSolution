package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Toughness extends CustomEnchantment{
	
	public Toughness() {
		super("Toughness", -15, -10, 16, 11, 1, 1, 4, 4, Weight.UNCOMMON, "Increases armor toughness.");
		addDefaultDisplayName(Language.GERMAN, "Rüstungshärte");
		addDefaultDescription(Language.GERMAN, "Erhöht die Rüstungsstärke.");
	}

	@Override
	public String getName() {
		return "toughness";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TOUGHNESS;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
