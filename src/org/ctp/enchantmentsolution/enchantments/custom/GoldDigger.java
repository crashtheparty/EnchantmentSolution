package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class GoldDigger extends CustomEnchantment{
	
	public GoldDigger() {
		super("Gold Digger", -2, -10, 12, 11, 10, 1, 6, 5, Weight.UNCOMMON, "Earn experience and gold nuggets for breaking crops.");
		addDefaultDisplayName(Language.GERMAN, "Goldgräber");
		addDefaultDescription(Language.GERMAN, "Sammeln Sie Erfahrung und Gold-Nuggets f�r das Ernten.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.GOLD_DIGGER;
	}

	@Override
	public String getName() {
		return "gold_digger";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
