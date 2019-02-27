package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Respiration extends CustomEnchantment{
	
	public Respiration() {
		addDefaultDisplayName("Respiration");
		addDefaultDisplayName(Language.GERMAN, "Atmung");
		setDefaultFiftyConstant(-5);
		setDefaultThirtyConstant(0);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(30);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Extends underwater breathing time.");
		addDefaultDescription(Language.GERMAN, "Verlängert die Atmungszeit unter Wasser.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.OXYGEN;
	}

	@Override
	public String getName() {
		return "respiration";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
