package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class WidthPlusPlus extends CustomEnchantment{

	public WidthPlusPlus() {
		super("Width++", 0, -10, 20, 20, 15, 1, 3, 2, Weight.RARE, "Increase left/right break radius by 1 per level.");
		addDefaultDisplayName(Language.GERMAN, "Breite++");
		addDefaultDescription(Language.GERMAN, "Erhöhen Sie den linken / rechten Umbruchradius um 1 pro Ebene.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.WIDTH_PLUS_PLUS;
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
		return "width_plus_plus";
	}

}
