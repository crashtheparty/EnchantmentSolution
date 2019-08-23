package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FireProtection extends CustomEnchantment{
	
	public FireProtection() {
		super("Fire Protection", -8, 2, 15, 8, 1, 1, 4, 4, Weight.UNCOMMON, "Reduces fire damage.");
		addDefaultDisplayName(Language.GERMAN, "Feuerschutz");
		addDefaultDescription(Language.GERMAN, "Reduziert Feuerschäden.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "火焰保护");
		addDefaultDescription(Language.CHINA_SIMPLE, "减少受到的火焰伤害.");
	}

	@Override
	public String getName() {
		return "fire_protection";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_FIRE;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		if(!ConfigUtils.getProtectionConflicts()) return Arrays.asList();
		return Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_PROJECTILE);
	}
}
