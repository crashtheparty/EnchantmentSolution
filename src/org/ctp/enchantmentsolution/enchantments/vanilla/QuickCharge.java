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
		super("Quick Charge", -15, -8, 20, 20, 1, 1, 4, 3, Weight.UNCOMMON, "Places arrows in the crossbow faster.");
		addDefaultDisplayName(Language.GERMAN, "Schnellladen");
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
