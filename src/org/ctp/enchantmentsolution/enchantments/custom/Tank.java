package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Tank extends CustomEnchantment{
	
	public Tank() {
		super("Tank", 10, -5, 20, 15, 20, 1, 3, 3, Weight.RARE, "Gives additional unbreaking protection to armor.");
		addDefaultDisplayName(Language.GERMAN, "Panzer");
		addDefaultDescription(Language.GERMAN, "Verleiht der Rüstung zusätzlichen Schutz.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "坦克");
		addDefaultDescription(Language.CHINA_SIMPLE, "给予装备额外的耐久保护.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TANK;
	}

	@Override
	public String getName() {
		return "tank";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
