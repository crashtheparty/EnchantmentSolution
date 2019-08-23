package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Moisturize extends CustomEnchantment{

	public Moisturize() {
		super("Moisturize", 50, 35, 0, 0, 20, 1, 1, 1, Weight.VERY_RARE, "Waters blocks by right clicking them.");
		addDefaultDisplayName(Language.GERMAN, "Befeuchten");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Befeuchtet Blöcke durch Rechtsklick.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "湿润");
		addDefaultDescription(Language.CHINA_SIMPLE, "右击方块进行湿润.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.MOISTURIZE;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SHEARS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "moisturize";
	}

}
