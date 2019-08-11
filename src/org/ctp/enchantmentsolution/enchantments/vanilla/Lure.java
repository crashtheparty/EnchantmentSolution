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
		super("Lure", 7, 5, 11, 10, 1, 1, 4, 3, Weight.RARE, "Increases rate of fish biting your hook while fishing.");
		addDefaultDisplayName(Language.GERMAN, "Köder");
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
