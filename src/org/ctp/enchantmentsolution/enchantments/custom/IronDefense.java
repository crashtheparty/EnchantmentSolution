package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class IronDefense extends CustomEnchantment{

	public IronDefense() {
		super("Iron Defense", -4, -4, 14, 12, 1, 1, 5, 3, Weight.RARE, "Having the shield equipped will redirect damage to the shield.");
		addDefaultDisplayName(Language.GERMAN, "Eiserne Verteidigung");
		addDefaultDescription(Language.GERMAN, "Wenn der Schild ausgerüstet ist, werden Schäden am Schild umgeleitet.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "钢铁之护");
		addDefaultDescription(Language.CHINA_SIMPLE, "使用盾牌可反伤.");
	}
	
	@Override
	public String getName() {
		return "iron_defense";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.IRON_DEFENSE;
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
		return Arrays.asList(DefaultEnchantments.HARD_BOUNCE);
	}

}
