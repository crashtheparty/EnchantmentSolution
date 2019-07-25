package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SoulReaper extends CustomEnchantment{
	
	public SoulReaper() {
		super("Soul Reaper", 30, 20, 0, 0, 25, 1, 1, 1, Weight.VERY_RARE, "Has a chance of stealing soulbounded items from the killed player.");
		addDefaultDisplayName(Language.GERMAN, "Seelendieb");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Hat eine Chance, vom getöteten Spieler seelengebundene Gegenstände zu stehlen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SOUL_REAPER;
	}

	@Override
	public String getName() {
		return "soul_reaper";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
