package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ProjectileProtection extends CustomEnchantment{
	
	public ProjectileProtection() {
		addDefaultDisplayName("Projectile Protection");
		addDefaultDisplayName(Language.GERMAN, "Schusssicher");
		setDefaultFiftyConstant(-9);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(6);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Reduces projectile damage (arrows, ghast/blaze fire charges, etc.).");
		addDefaultDescription(Language.GERMAN, "Reduziert Projektilbeschädigungen (Pfeile, Feuerschläge usw.).");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_PROJECTILE;
	}

	@Override
	public String getName() {
		return "projectile_protection";
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
		if(!Enchantments.getProtectionConflicts()) return Arrays.asList();
		return Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE);
	}
}
