package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Loyalty extends CustomEnchantment{

	public Loyalty() {
		addDefaultDisplayName("Loyalty");
		addDefaultDisplayName(Language.GERMAN, "Treue");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(7);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Returns a thrown trident after it hits something.");
		addDefaultDescription(Language.GERMAN, "Gibt einen geworfenen Dreizack zurück, nachdem er etwas getroffen hat.");
	}

	@Override
	public String getName() {
		return "loyalty";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LOYALTY;
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
		return Arrays.asList(Enchantment.RIPTIDE);
	}
}
