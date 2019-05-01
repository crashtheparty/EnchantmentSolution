package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FrostWalker extends CustomEnchantment{
	
	public FrostWalker() {
		addDefaultDisplayName("Frost Walker");
		addDefaultDisplayName(Language.GERMAN, "Eisläufer");
		setTreasure(true);
		setDefaultFiftyConstant(5);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(2);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Creates frosted ice blocks when walking over water.");
		addDefaultDescription(Language.GERMAN, "Erzeugt beim Gehen über Wasser gefrorene Eisblöcke.");
	}

	@Override
	public String getName() {
		return "frost_walker";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FROST_WALKER;
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
		return Arrays.asList(Enchantment.DEPTH_STRIDER, DefaultEnchantments.MAGMA_WALKER, DefaultEnchantments.VOID_WALKER);
	}
}
