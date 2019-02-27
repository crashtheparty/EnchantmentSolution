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
		addDefaultDisplayName("Feather Falling");
		addDefaultDisplayName(Language.GERMAN, "Federfall");
		setDefaultFiftyConstant(-7);
		setDefaultThirtyConstant(-1);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(6);
		setDefaultFiftyMaxConstant(18);
		setDefaultThirtyMaxConstant(10);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Reduces fall damage.");
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
