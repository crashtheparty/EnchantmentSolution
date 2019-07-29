package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class MagicGuard extends CustomEnchantment{
	
	public MagicGuard() {
		super("Magic Guard", 30, 25, 0, 0, 25, 1, 1, 1, Weight.VERY_RARE, "Negates bad potion effects.");
		addDefaultDisplayName(Language.GERMAN, "Magischer Schutz");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Negiert schlechte Trankeffekte.");
	}

	@Override
	public String getName() {
		return "magic_guard";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MAGIC_GUARD;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SHIELD);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

}
