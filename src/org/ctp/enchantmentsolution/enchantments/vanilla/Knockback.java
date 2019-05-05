package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Knockback extends CustomEnchantment{
	
	public Knockback() {
		addDefaultDisplayName("Knockback");
		addDefaultDisplayName(Language.GERMAN, "Rückstoß");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-15);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Increases knockback.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß.");
	}

	@Override
	public String getName() {
		return "knockback";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.KNOCKBACK;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.KNOCKUP);
	}
}
