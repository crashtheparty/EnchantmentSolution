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
		addDefaultDisplayName("Life");
		addDefaultDisplayName(Language.GERMAN, "Leben");
		setDefaultFiftyConstant(-5);
		setDefaultThirtyConstant(-5);
		setDefaultFiftyModifier(25);
		setDefaultThirtyModifier(15);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases maximum health by 4 (half hearts) per level when worn.");
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
