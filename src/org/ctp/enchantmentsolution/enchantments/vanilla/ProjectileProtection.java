package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Weight;

public class ProjectileProtection extends CustomEnchantment{
	
	public ProjectileProtection() {
		setDefaultDisplayName("Projectile Protection");
		setDefaultFiftyConstant(-9);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(6);
		setDefaultFiftyMaxConstant(19);
		setDefaultThirtyMaxConstant(15);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.UNCOMMON);
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
	protected List<CustomEnchantment> getConflictingEnchantments() {
		return Arrays.asList(this, DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 
				DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE));
	}

	@Override
	public String getDescription() {
		return "Reduces projectile damage (arrows, ghast/blaze fire charges, etc.).";
	}

}
