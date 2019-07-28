package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Unbreaking extends CustomEnchantment{
	
	public Unbreaking() {
		super("Unbreaking", -10, -3, 15, 8, 1, 1, 5, 3, Weight.UNCOMMON, "Increases effective durability.");
		addDefaultDisplayName(Language.GERMAN, "Haltbarkeit");
		addDefaultDescription(Language.GERMAN, "Erhöht die effektive Haltbarkeit.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DURABILITY;
	}
	
	@Override
	public String getName() {
		return "unbreaking";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ENCHANTABLE);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ALL);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
