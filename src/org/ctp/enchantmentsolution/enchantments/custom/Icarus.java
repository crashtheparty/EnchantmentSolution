package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Icarus extends CustomEnchantment{

	public Icarus() {
		super("Icarus", 8, 5, 12, 10, 20, 1, 5, 3, Weight.VERY_RARE, "Flying upwards will occassionally increase velocity.");
		addDefaultDisplayName(Language.GERMAN, "Ikarus");
		addDefaultDescription(Language.GERMAN, "Aufwärtsfliegen erhöht gelegentlich die Geschwindigkeit.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
	}
	
	@Override
	public String getName() {
		return "icarus";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.ICARUS;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.FREQUENT_FLYER);
	}

}
