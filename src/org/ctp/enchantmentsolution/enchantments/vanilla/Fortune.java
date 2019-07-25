package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Fortune extends CustomEnchantment{
	
	public Fortune() {
		super("Fortune", 7, 6, 11, 9, 1, 1, 5, 3, Weight.RARE, "Increases block drops.");
		addDefaultDisplayName(Language.GERMAN, "Glück");
		addDefaultDescription(Language.GERMAN, "Erhöht Blocktropfen.");
	}
	
	@Override
	public String getName() {
		return "fortune";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LOOT_BONUS_BLOCKS;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.SILK_TOUCH);
	}
}
