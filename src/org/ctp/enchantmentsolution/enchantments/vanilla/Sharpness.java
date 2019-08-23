package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Sharpness extends CustomEnchantment{
	
	public Sharpness() {
		super("Sharpness", -12, -10, 13, 11, 1, 1, 6, 5, Weight.COMMON, "Increases melee damage.\n" + 
				"Adds 1 (half heart) extra damage for the first level, and 0.5 (half heart) for each additional level.");
		addDefaultDisplayName(Language.GERMAN, "Schärfe");
		addDefaultDescription(Language.GERMAN, "Erhöht Nahkampfschaden." + 
				"\nFügt 1 (halbes Herz) zusätzlichen Schaden für die erste Stufe und 0,5 (halbes Herz) für jede zusätzliche Stufe hinzu.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "锋利");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加近战伤害.\n1级增加1点伤害,每级增加0.5点（半颗心）伤害.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ALL;
	}

	@Override
	public String getName() {
		return "sharpness";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, DefaultEnchantments.QUICK_STRIKE);
	}
}
