package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Transmutation extends CustomEnchantment{
	
	public Transmutation() {
		super("Transmutation", 65, 35, 0, 0, 40, 1, 1, 1, Weight.VERY_RARE, "On killing mobs, all non-sea drop items become sea drop items.");
		addDefaultDisplayName(Language.GERMAN, "Transmutation");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Beim Töten von Mobs werden alle Gegenstände, die nicht aus dem Meer stammen, zu Teilen aus dem Meer.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TRANSMUTATION;
	}

	@Override
	public String getName() {
		return "transmutation";
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
