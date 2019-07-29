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
		super("Frost Walker", 5, 5, 15, 10, 10, 1, 2, 2, Weight.RARE, "Creates frosted ice blocks when walking over water.");
		addDefaultDisplayName(Language.GERMAN, "Eisläufer");
		setTreasure(true);
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
