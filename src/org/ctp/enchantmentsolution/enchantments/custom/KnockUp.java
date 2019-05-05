package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class KnockUp extends CustomEnchantment{
	
	public KnockUp() {
		addDefaultDisplayName("Knockup");
		addDefaultDisplayName(Language.GERMAN, "Werfen");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Increases knockback upwards.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß nach oben.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.KNOCKUP;
	}

	@Override
	public String getName() {
		return "knockup";
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
		return Arrays.asList(Enchantment.KNOCKBACK);
	}

}
