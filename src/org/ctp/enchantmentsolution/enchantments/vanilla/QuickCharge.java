package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class QuickCharge extends CustomEnchantment{
	
	public QuickCharge() {
		addDefaultDisplayName("Quick Charge");
		addDefaultDisplayName(Language.GERMAN, "Schnellladen");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-8);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Place arrows in the crossbow faster.");
		addDefaultDescription(Language.GERMAN, "Setzen Sie die Pfeile schneller in die Armbrust.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.QUICK_CHARGE;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "quick_charge";
	}

}
