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
		super("Sacrifice", 35, 15, 15, 15, 30, 1, 2, 2, Weight.VERY_RARE, "Damage the mob who killed you based upon your experience level.");
		addDefaultDisplayName(Language.GERMAN, "Opferung");
		addDefaultDescription(Language.GERMAN, "Zerstöre den Mob, der dich getötet hat, basierend auf deinem Erfahrungslevel.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
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
