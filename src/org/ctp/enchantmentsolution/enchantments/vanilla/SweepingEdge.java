package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class SweepingEdge extends CustomEnchantment{
	
	public SweepingEdge() {
		super("Sweeping Edge", -7, -4, 12, 9, 1, 1, 3, 3, Weight.RARE, "Increases sweeping attack damage.");
		addDefaultDisplayName(Language.GERMAN, "Schwungkraft");
		addDefaultDescription(Language.GERMAN, "Erhöht den Angriffsschaden.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "横扫之刃");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加横扫伤害.");
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
