package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Smeltery extends CustomEnchantment{
	
	public Smeltery() {
		super("Smeltery", 40, 20, 0, 0, 30, 1, 1, 1, Weight.RARE, "Smelts blocks while mining.");
		addDefaultDisplayName(Language.GERMAN, "Schmelzen");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Schmelzen Blöcke während des Bergbaus.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "熔炼");
		addDefaultDescription(Language.CHINA_SIMPLE, "挖掘方块的同时进行熔炼.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SMELTERY;
	}

	@Override
	public String getName() {
		return "smeltery";
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
