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
		super("Gung-Ho", 40, 20, 0, 0, 30, 1, 1, 1, Weight.RARE, "Decreases health by half. All attacks do 3 times the damage.");
		addDefaultDisplayName(Language.GERMAN, "Gung-Ho");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Verringert die Gesundheit um die H�lfte. Alle Angriffe verursachen das Dreifache des Schadens.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
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
