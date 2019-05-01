package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class IronDefense extends CustomEnchantment{

	public IronDefense() {
		addDefaultDisplayName("Iron Defense");
		addDefaultDisplayName(Language.GERMAN, "Eiserne Verteidigung");
		setDefaultFiftyConstant(-4);
		setDefaultThirtyConstant(-4);
		setDefaultFiftyModifier(14);
		setDefaultThirtyModifier(12);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Having the shield equipped will redirect damage to the shield.");
		addDefaultDescription(Language.GERMAN, "Wenn der Schild ausgerüstet ist, werden Schäden am Schild umgeleitet.");
	}
	
	@Override
	public String getName() {
		return "iron_defense";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.IRON_DEFENSE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.HARD_BOUNCE);
	}

}
