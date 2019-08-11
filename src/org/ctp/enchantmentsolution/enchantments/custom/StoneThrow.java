package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class StoneThrow extends CustomEnchantment{
	
	public StoneThrow() {
		super("Stone Throw", -12, -10, 13, 11, 1, 1, 6, 5, Weight.COMMON, "Increases ranged damage against flying mobs.\n"
				+ "Adds 40% * level + 20% damage against flying mobs.");
		addDefaultDisplayName(Language.GERMAN, "Steinwurf");
		addDefaultDescription(Language.GERMAN, "Erhöht den Distanzschaden gegen fliegende Mobs." + 
				"\n" + 
				"Fügt 40% * Level + 20% Schaden gegen fliegende Mobs hinzu.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.STONE_THROW;
	}

	@Override
	public String getName() {
		return "stone_throw";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
