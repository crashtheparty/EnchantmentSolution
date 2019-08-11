package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Infinity extends CustomEnchantment{
	
	public Infinity() {
		super("Infinity", 35, 20, 0, 0, 20, 1, 1, 1, Weight.RARE, "Shooting doesn't consume regular arrows.");
		addDefaultDisplayName(Language.GERMAN, "Unendlichkeit");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Beim Schießen werden keine normalen Pfeile verbraucht.");
	}

	@Override
	public String getName() {
		return "infinity";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_INFINITE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.MENDING);
	}
}
