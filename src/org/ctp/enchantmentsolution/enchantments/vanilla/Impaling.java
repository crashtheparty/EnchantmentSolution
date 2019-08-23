package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Impaling extends CustomEnchantment{
	
	public Impaling() {
		super("Impaling", -12, 1, 13, 8, 1, 1, 6, 5, Weight.RARE, "Increases melee damage against aquatic mobs.\n" + 
				"Adds 2.5 (half heart) extra damage for each additional level.");
		addDefaultDisplayName(Language.GERMAN, "Harpune");
		addDefaultDescription(Language.GERMAN, "Erhöht Nahkampfschaden gegen Wassermobs.\n" + 
				"Fügt für jede zusätzliche Stufe 2,5 (halbes Herz) zusätzlichen Schaden hinzu.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "穿刺");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加对水生怪物的近战伤害.\n每级增加2.5的伤害.");
	}

	@Override
	public String getName() {
		return "impaling";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.IMPALING;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
