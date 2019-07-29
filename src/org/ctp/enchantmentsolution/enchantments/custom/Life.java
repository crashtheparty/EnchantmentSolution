package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Life extends CustomEnchantment{
	
	public Life() {
		super("Life", -5, -5, 25, 15, 10, 1, 3, 3, Weight.RARE, "Increases maximum health by 4 (half hearts) per level when worn.");
		addDefaultDisplayName(Language.GERMAN, "Leben");
		addDefaultDescription(Language.GERMAN, "Erhöht die maximale Gesundheit um 4 (halbe Herzen) pro Level, wenn er getragen wird.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.LIFE;
	}
	
	@Override
	public String getName() {
		return "life";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.GUNG_HO);
	}

}
