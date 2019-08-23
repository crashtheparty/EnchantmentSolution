package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Looting extends CustomEnchantment{
	
	public Looting() {
		super("Looting", 7, 6, 11, 9, 1, 1, 5, 3, Weight.RARE, "Mobs can drop more loot.");
		addDefaultDisplayName(Language.GERMAN, "Plünderung");
		addDefaultDescription(Language.GERMAN, "Mobs können mehr Beute fallen lassen.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "抢夺");
		addDefaultDescription(Language.CHINA_SIMPLE, "怪物掉落更多物品.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LOOT_BONUS_MOBS;
	}

	@Override
	public String getName() {
		return "looting";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
