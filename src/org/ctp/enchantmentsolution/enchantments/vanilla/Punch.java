package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Punch extends CustomEnchantment{
	
	public Punch() {
		super("Punch", -8, -8, 20, 20, 1, 1, 3, 2, Weight.RARE, "Increases knockback on bows.");
		addDefaultDisplayName(Language.GERMAN, "Schlag");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß an den Bögen.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "冲击");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加弓箭击退距离.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_KNOCKBACK;
	}
	
	@Override
	public String getName() {
		return "punch";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
