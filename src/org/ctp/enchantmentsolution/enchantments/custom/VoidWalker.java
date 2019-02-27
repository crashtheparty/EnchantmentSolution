package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class VoidWalker extends CustomEnchantment{
	
	public VoidWalker() {
		setTreasure(true);
		addDefaultDisplayName("Void Walker");
		addDefaultDisplayName(Language.GERMAN, "Ungültiger Läufer");
		setDefaultFiftyConstant(5);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(20);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(2);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Allows players to walk on air, turning it into obsidian.");
		addDefaultDescription(Language.GERMAN, "Erlaubt es den Spielern, in der Luft zu gehen und es in Obsidian umzuwandeln.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.VOID_WALKER;
	}
	
	@Override
	public String getName() {
		return "void_walker";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.FROST_WALKER, Enchantment.DEPTH_STRIDER, DefaultEnchantments.MAGMA_WALKER);
	}
}
