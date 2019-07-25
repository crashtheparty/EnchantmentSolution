package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Channeling extends CustomEnchantment{
	
	public Channeling() {
		super("Channeling", 25, 25, 0, 0, 1, 1, 1, 1, Weight.VERY_RARE, "Summons a lightning bolt when a mob is hit by a thrown trident.");
		addDefaultDisplayName(Language.GERMAN, "Entladung");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Beschwört einen Blitz, wenn ein Mob von einem geworfenen Dreizack getroffen wird.");
	}

	@Override
	public String getName() {
		return "channeling";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.CHANNELING;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.RIPTIDE);
	}
}
