package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class IrenesLasso extends CustomEnchantment{

	public IrenesLasso() {
		addDefaultDisplayName("Irene's Lasso");
		addDefaultDisplayName(Language.GERMAN, "Lasso von Irene");
		setDefaultFiftyConstant(0);
		setDefaultThirtyConstant(15);
		setDefaultFiftyModifier(30);
		setDefaultThirtyModifier(0);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(2);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Grabs animals to carry around with you and place later.");
		addDefaultDescription(Language.GERMAN, "Greift Tiere zum Mitnehmen und Platzieren später.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.IRENES_LASSO;
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
		return Arrays.asList(DefaultEnchantments.WAND);
	}

	@Override
	public String getName() {
		return "lasso_of_irene";
	}

}
