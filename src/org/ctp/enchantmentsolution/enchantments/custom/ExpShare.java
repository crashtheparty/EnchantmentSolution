package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ExpShare extends CustomEnchantment{
	
	public ExpShare() {
		super("Exp. Share", 0, -2, 20, 12, 1, 1, 3, 3, Weight.UNCOMMON, "Increase experience earned from killing mobs and breaking blocks.");
		addDefaultDisplayName(Language.GERMAN, "Erfahrung");
		addDefaultDescription(Language.GERMAN, "Erhöhen Sie die Erfahrung durch das Töten von Mobs und Blockaden.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.EXP_SHARE;
	}

	@Override
	public String getName() {
		return "exp_share";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.TOOLS, ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.TOOLS, ItemType.TRIDENT);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
