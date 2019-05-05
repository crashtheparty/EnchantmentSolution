package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Thorns extends CustomEnchantment{
	
	public Thorns() {
		addDefaultDisplayName("Thorns");
		addDefaultDisplayName(Language.GERMAN, "Dornen");
		setDefaultFiftyConstant(10);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Attackers are damaged when they attack the wearer. This also does additional durability damage to armor.");
		addDefaultDescription(Language.GERMAN, "Angreifer werden beschädigt, wenn sie den Träger angreifen. Dies führt auch zu einer zusätzlichen Haltbarkeit der Rüstung.");
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
