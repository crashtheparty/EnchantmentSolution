package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class MagicGuard extends CustomEnchantment{
	
	public MagicGuard() {
		addDefaultDisplayName("Magic Guard");
		addDefaultDisplayName(Language.GERMAN, "Magischer Schutz");
		setDefaultFiftyConstant(30);
		setDefaultThirtyConstant(25);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(70);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(25);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(20);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Negates bad potion effects.");
		addDefaultDescription(Language.GERMAN, "Negiert schlechte Trankeffekte.");
	}

	@Override
	public String getName() {
		return "magic_guard";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MAGIC_GUARD;
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
		return Arrays.asList();
	}

}
