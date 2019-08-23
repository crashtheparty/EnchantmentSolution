package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Knockback extends CustomEnchantment{
	
	public Knockback() {
		super("Knockback", -15, -15, 20, 20, 1, 1, 4, 2, Weight.UNCOMMON, "Increases knockback.");
		addDefaultDisplayName(Language.GERMAN, "Rückstoß");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "击退");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加击退距离.");
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
