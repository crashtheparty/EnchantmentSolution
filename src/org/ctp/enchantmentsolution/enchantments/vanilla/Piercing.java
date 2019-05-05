package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Piercing extends CustomEnchantment{
	
	public Piercing() {
		addDefaultDisplayName("Piercing");
		addDefaultDisplayName(Language.GERMAN, "Durchschuss");
		setTreasure(true);
		setDefaultFiftyConstant(-14);
		setDefaultThirtyConstant(-9);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(5);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Arrows go through and attack multiple mobs.");
		addDefaultDescription(Language.GERMAN, "Pfeile gehen durch und greifen mehrere Mobs an.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PIERCING;
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
		return Arrays.asList(Enchantment.MULTISHOT);
	}

	@Override
	public String getName() {
		return "piercing";
	}

}
