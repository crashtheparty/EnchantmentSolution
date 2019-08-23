package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class NoRest extends CustomEnchantment{

	public NoRest() {
		super("No Rest", 15, 1, 0, 0, 15, 1, 1, 1, Weight.RARE, "No phantoms will spawn around you.");
		addDefaultDisplayName(Language.GERMAN, "Keine Pause");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Es werden keine Phantome um Sie herum erscheinen.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
	}

	@Override
	public String getName() {
		return "no_rest";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.NO_REST;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.UNREST, Enchantment.WATER_WORKER);
	}
}
