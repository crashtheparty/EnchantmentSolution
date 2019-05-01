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
		addDefaultDisplayName("Icarus");
		addDefaultDisplayName(Language.GERMAN, "Ikarus");
		setDefaultFiftyConstant(8);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Flying upwards will occasionally increase velocity.");
		addDefaultDescription(Language.GERMAN, "Aufwärtsfliegen erhöht gelegentlich die Geschwindigkeit.");
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
