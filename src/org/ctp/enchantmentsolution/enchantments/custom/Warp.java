package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Warp extends CustomEnchantment{
	
	public Warp() {
		addDefaultDisplayName("Warp");
		addDefaultDisplayName(Language.GERMAN, "Warp");
		setDefaultFiftyConstant(-5);
		setDefaultThirtyConstant(-7);
		setDefaultFiftyModifier(20);
		setDefaultThirtyModifier(14);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Gives a chance of teleporting a small distance away on hit.");
		addDefaultDescription(Language.GERMAN, "Gibt eine Chance, bei einem Treffer eine kleine Entfernung zu teleportieren.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.WARP;
	}

	@Override
	public String getName() {
		return "warp";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.LEGGINGS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.LEGGINGS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
