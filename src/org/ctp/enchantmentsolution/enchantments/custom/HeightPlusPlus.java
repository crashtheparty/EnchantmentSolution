package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class HeightPlusPlus extends CustomEnchantment{

	public HeightPlusPlus() {
		super("Height++", 0, -10, 20, 20, 15, 1, 3, 2, Weight.RARE, "Increase up/down break radius by 1 per level.");
		addDefaultDisplayName(Language.GERMAN, "Höhe++");
		addDefaultDescription(Language.GERMAN, "Erhöhen Sie den Aufwärts- / Abwärtsbruch-Radius um 1 pro Ebene.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.HEIGHT_PLUS_PLUS;
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
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "height_plus_plus";
	}

}