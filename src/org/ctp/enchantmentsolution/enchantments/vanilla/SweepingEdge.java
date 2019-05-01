package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SweepingEdge extends CustomEnchantment{
	
	public SweepingEdge() {
		addDefaultDisplayName("Sweeping Edge");
		addDefaultDisplayName(Language.GERMAN, "Schwungkraft");
		setDefaultFiftyConstant(-7);
		setDefaultThirtyConstant(-4);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(9);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases sweeping attack damage.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Angriffsschaden.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.SWEEPING_EDGE;
	}

	@Override
	public String getName() {
		return "sweeping_edge";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
