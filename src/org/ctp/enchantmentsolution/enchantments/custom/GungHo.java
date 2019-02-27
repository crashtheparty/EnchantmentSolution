package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class GungHo extends CustomEnchantment{
	
	public GungHo() {
		addDefaultDisplayName("Gung-Ho");
		addDefaultDisplayName(Language.GERMAN, "Gung-Ho");
		setDefaultFiftyConstant(40);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(60);
		setDefaultThirtyMaxConstant(40);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Decreases health to 5 (hearts). All attacks do 3 times the damage.");
		addDefaultDescription(Language.GERMAN, "Verringert die Gesundheit auf 5 (Herz). Alle Angriffe verursachen den dreifachen Schaden.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.GUNG_HO;
	}
	
	@Override
	public String getName() {
		return "gung_ho";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CHESTPLATES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.LIFE);
	}

}
