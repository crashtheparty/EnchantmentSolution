package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class MagmaWalker extends CustomEnchantment{
	
	public MagmaWalker() {
		setTreasure(true);
		addDefaultDisplayName("Magma Walker");
		addDefaultDisplayName(Language.GERMAN, "Lavaläufer");
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
		addDefaultDescription("Allows players to walk on lava, turning it into magma.");
		addDefaultDescription(Language.GERMAN, "Erlaubt es den Spielern, auf Lava zu gehen und sie in Magma umzuwandeln.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MAGMA_WALKER;
	}
	
	@Override
	public String getName() {
		return "magma_walker";
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
		return Arrays.asList(Enchantment.FROST_WALKER, Enchantment.DEPTH_STRIDER, DefaultEnchantments.VOID_WALKER);
	}

}
