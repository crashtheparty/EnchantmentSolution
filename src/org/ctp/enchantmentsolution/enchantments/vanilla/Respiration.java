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
		super("Respiration", -5, 0, 15, 10, 1, 1, 3, 3, Weight.RARE, "Extends underwater breathing time.");
		addDefaultDisplayName(Language.GERMAN, "Atmung");
		addDefaultDescription(Language.GERMAN, "Verlängert die Atmungszeit unter Wasser.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "水下呼吸");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加水下呼吸时间.");
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
