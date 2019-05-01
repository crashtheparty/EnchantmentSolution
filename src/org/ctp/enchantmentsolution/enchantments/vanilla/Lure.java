package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Lure extends CustomEnchantment{
	
	public Lure() {
		addDefaultDisplayName("Lure");
		addDefaultDisplayName(Language.GERMAN, "Köder");
		setDefaultFiftyConstant(7);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases rate of fish biting your hook while fishing.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Rate der Fische, die sich beim Angeln an den Haken beißen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LURE;
	}

	@Override
	public String getName() {
		return "lure";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.FISHING_ROD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
