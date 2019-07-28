package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class HardBounce extends CustomEnchantment{

	public HardBounce() {
		super("Hard Bounce", -4, -4, 14, 12, 1, 1, 5, 3, Weight.RARE, "Projectiles bounce back from the shield.");
		addDefaultDisplayName(Language.GERMAN, "Harter Aufprall");
		addDefaultDescription(Language.GERMAN, "Projektile springen vom Schild zurück.");
	}
	
	@Override
	public String getName() {
		return "hard_bounce";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.HARD_BOUNCE;
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
		return Arrays.asList(DefaultEnchantments.IRON_DEFENSE);
	}

}
