package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FeatherFalling extends CustomEnchantment{
	
	public FeatherFalling() {
		super("Feather Falling", -7, -1, 12, 6, 1, 1, 4, 4, Weight.UNCOMMON, "Reduces fall damage.");
		addDefaultDisplayName(Language.GERMAN, "Federfall");
		addDefaultDescription(Language.GERMAN, "Reduziert Sturzschäden.");
	}

	@Override
	public String getName() {
		return "feather_falling";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_FALL;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
