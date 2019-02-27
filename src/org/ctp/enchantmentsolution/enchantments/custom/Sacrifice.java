package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Sacrifice extends CustomEnchantment{
	
	public Sacrifice() {
		addDefaultDisplayName("Sacrifice");
		addDefaultDisplayName(Language.GERMAN, "Opferung");
		setDefaultFiftyConstant(35);
		setDefaultThirtyConstant(15);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(15);
		setDefaultFiftyMaxConstant(40);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(2);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Damage the mob who killed you based upon your experience level.");
		addDefaultDescription(Language.GERMAN, "Zerstöre den Mob, der dich getötet hat, basierend auf deinem Erfahrungslevel.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SACRIFICE;
	}
	
	@Override
	public String getName() {
		return "sacrifice";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
