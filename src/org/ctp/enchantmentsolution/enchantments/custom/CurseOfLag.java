package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfLag extends CustomEnchantment{
	
	public CurseOfLag() {
		addDefaultDisplayName("Curse of Lag");
		addDefaultDisplayName(Language.GERMAN, "Fluch der Verzögerung");
		setTreasure(true);
		setDefaultFiftyConstant(25);
		setDefaultThirtyConstant(25);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription("Ah, fireworks!");
		addDefaultDescription(Language.GERMAN, "Ah, Feuerwerk!");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.CURSE_OF_LAG;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.MELEE, ItemType.RANGED);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.MELEE, ItemType.RANGED);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "lagging_curse";
	}

}
