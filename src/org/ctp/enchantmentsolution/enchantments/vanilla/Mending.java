package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Mending extends CustomEnchantment{
	
	public Mending() {
		super("Mending", 25, 25, 0, 0, 20, 1, 1, 1, Weight.RARE, "Repair durability with experience.");
		addDefaultDisplayName(Language.GERMAN, "Reparatur");
		setTreasure(true);
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Haltbarkeit mit Erfahrung reparieren.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "经验修补");
		addDefaultDescription(Language.CHINA_SIMPLE, "使用经验修复物品.");
	}

	@Override
	public String getName() {
		return "mending";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.MENDING;
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
		return Arrays.asList(Enchantment.ARROW_INFINITE);
	}
}
