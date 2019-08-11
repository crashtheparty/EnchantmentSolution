package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Riptide extends CustomEnchantment{
	
	public Riptide() {
		super("Riptide", 7, 7, 11, 19, 1, 1, 5, 3, Weight.RARE, "Allows the trident to be used as a means of fast transportation.");
		addDefaultDisplayName(Language.GERMAN, "Sog");
		addDefaultDescription(Language.GERMAN, "Ermöglicht die Verwendung des Dreizackes als schnelles Transportmittel.");
	}

	@Override
	public String getName() {
		return "riptide";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.RIPTIDE;
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
		return Arrays.asList(Enchantment.CHANNELING, Enchantment.LOYALTY);
	}
}
