package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ProjectileProtection extends CustomEnchantment{
	
	public ProjectileProtection() {
		super("Projectile Protection", -9, -3, 13, 6, 1, 1, 4, 4, Weight.UNCOMMON, "Reduces projectile damage (arrows, ghast/blaze fire charges, etc.).");
		addDefaultDisplayName(Language.GERMAN, "Schusssicher");
		addDefaultDescription(Language.GERMAN, "Reduziert Projektilbeschädigungen (Pfeile, Feuerschläge usw.).");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "弹射物保护");
		addDefaultDescription(Language.CHINA_SIMPLE, "减少受到的弹射物伤害 (弓箭、火球等).");
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
		if(!ConfigUtils.getProtectionConflicts()) return Arrays.asList();
		return Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE);
	}
}
