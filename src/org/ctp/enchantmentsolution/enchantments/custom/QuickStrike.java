package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class QuickStrike extends CustomEnchantment{
	
	public QuickStrike() {
		super("Quick Strike", 7, 6, 11, 9, 1, 1, 5, 3, Weight.RARE, "Increases attack speed.");
		addDefaultDisplayName(Language.GERMAN, "Schneller Schlag");
		addDefaultDescription(Language.GERMAN, "Erhöht die Angriffsgeschwindigkeit");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "迅捷打击");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加攻速.");
	}
	
	@Override
	public String getName() {
		return "quick_strike";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.QUICK_STRIKE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, 
				Enchantment.DAMAGE_UNDEAD, Enchantment.FIRE_ASPECT, DefaultEnchantments.SHOCK_ASPECT);
	}
}
