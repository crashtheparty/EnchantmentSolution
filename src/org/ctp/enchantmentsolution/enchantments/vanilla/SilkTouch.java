package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SilkTouch extends CustomEnchantment{
	
	public SilkTouch() {
		addDefaultDisplayName("Silk Touch");
		addDefaultDisplayName(Language.GERMAN, "Behutsamkeit");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(15);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Mined blocks drop themselves instead of the usual items." + 
				"\n" + 
				"Allows collection of blocks that are normally unobtainable.");
		addDefaultDescription(Language.GERMAN, "Abgebaute Blöcke lassen sich anstelle der üblichen Gegenstände fallen." + 
				"\n" + 
				"Ermöglicht das Sammeln von Blöcken, die normalerweise nicht erreichbar sind.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.SILK_TOUCH;
	}

	@Override
	public String getName() {
		return "silk_touch";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.LOOT_BONUS_BLOCKS, DefaultEnchantments.SMELTERY);
	}
}
