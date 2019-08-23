package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Thorns extends CustomEnchantment{
	
	public Thorns() {
		super("Thorns", 10, -10, 20, 20, 20, 1, 3, 3, Weight.VERY_RARE, "Attackers are damaged when they attack the wearer. " + 
				"This does additional durability damage to armor.");
		addDefaultDisplayName(Language.GERMAN, "Dornen");
		addDefaultDescription(Language.GERMAN, "Angreifer werden beschädigt, wenn sie den Träger angreifen. " + 
				"Dies führt auch zu einer zusätzlichen Haltbarkeit der Rüstung.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "荆棘");
		addDefaultDescription(Language.CHINA_SIMPLE, "反伤.消耗更多耐久.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.THORNS;
	}

	@Override
	public String getName() {
		return "thorns";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
