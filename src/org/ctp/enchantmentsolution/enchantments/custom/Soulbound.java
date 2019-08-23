package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Soulbound extends CustomEnchantment{
	
	public Soulbound() {
		super("Soulbound", 40, 30, 0, 0, 30, 1, 1, 1, Weight.RARE, "Keep items on death.");
		addDefaultDisplayName(Language.GERMAN, "Seelengebunden");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Behalte den Gegenstand auf dem Tod.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SOULBOUND;
	}
	
	@Override
	public String getName() {
		return "soulbound";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.MELEE, ItemType.RANGED, ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ALL, ItemType.SHULKER_BOXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.VANISHING_CURSE);
	}
}
