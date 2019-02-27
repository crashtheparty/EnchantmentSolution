package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Flame extends CustomEnchantment{
	
	public Flame() {
		addDefaultDisplayName("Flame");
		addDefaultDisplayName(Language.GERMAN, "Flamme");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(30);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Flaming arrows." + 
				StringUtils.LF + 
				"Arrows are on fire when shot and deal 4 (2 Hearts) fire damage over 5 seconds.");
		addDefaultDescription("Brennende Pfeile." + 
				StringUtils.LF + 
				"Pfeile brennen beim Schieﬂen und verursachen 5 Sekunden lang 4 Feuerschaden");
	}

	@Override
	public String getName() {
		return "flame";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_FIRE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
