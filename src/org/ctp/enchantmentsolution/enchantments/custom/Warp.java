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
		super("Warp", -5, -7, 20, 14, 10, 1, 3, 3, Weight.RARE, "Gives a chance of teleporting a small distance away on hit.");
		addDefaultDisplayName(Language.GERMAN, "Warp");
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
