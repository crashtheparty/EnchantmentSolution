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
		super("Knockup", -15, 5, 20, 10, 1, 1, 4, 2, Weight.UNCOMMON, "Increases knockback upwards.");
		addDefaultDisplayName(Language.GERMAN, "Werfen");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß nach oben.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
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
