package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfLag extends CustomEnchantment{
	
	public CurseOfLag() {
		super("Curse of Lag", 25, 25, 0, 0, 1, 1, 1, 1, Weight.RARE, "Ah, fireworks!");
		addDefaultDisplayName(Language.GERMAN, "Fluch der Verzögerung");
		setTreasure(true);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription(Language.GERMAN, "Ah, Feuerwerk!");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.CURSE_OF_LAG;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.MELEE, ItemType.RANGED);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.MELEE, ItemType.RANGED);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "lagging_curse";
	}

}
