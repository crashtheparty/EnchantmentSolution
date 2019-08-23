package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Wand extends CustomEnchantment{
	
	public Wand() {
		super("Wand", 20, 10, 15, 10, 30, 1, 3, 2, Weight.VERY_RARE, "Places blocks from the offhand.");
		addDefaultDisplayName(Language.GERMAN, "Zauberstab");
		setTreasure(true);
		addDefaultDescription(Language.GERMAN, "Platziert Blöcke von der Nebenhand.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "法杖");
		addDefaultDescription(Language.CHINA_SIMPLE, "放置副手的方块.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.WAND;
	}

	@Override
	public String getName() {
		return "wand";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CARROT_ON_A_STICK);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.IRENES_LASSO);
	}
}
