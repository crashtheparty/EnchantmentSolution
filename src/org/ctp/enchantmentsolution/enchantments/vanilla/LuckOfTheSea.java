package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class LuckOfTheSea extends CustomEnchantment{
	
	public LuckOfTheSea() {
		addDefaultDisplayName("Luck of the Sea");
		addDefaultDisplayName(Language.GERMAN, "Glück des Meeres");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases luck while fishing.");
		addDefaultDescription(Language.GERMAN, "Erhöht das Glück beim Fischen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LUCK;
	}

	@Override
	public String getName() {
		return "luck_of_the_sea";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
