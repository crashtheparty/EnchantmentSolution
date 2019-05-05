package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FrequentFlyer extends CustomEnchantment{
	
	public FrequentFlyer() {
		addDefaultDisplayName("Frequent Flyer");
		addDefaultDisplayName(Language.GERMAN, "Vielflieger");
		setDefaultFiftyConstant(20);
		setDefaultThirtyConstant(5);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(30);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.VERY_RARE);
		addDefaultDescription("Allows flight. Durability damage every (3 * level) seconds when below 255 height and every (level) seconds above 255 height. "
				+ "Removes flight at 32 durability.");
		addDefaultDescription(Language.GERMAN, "Erlaubt den Flug. Haltbarkeitsschaden alle (3 * Level) Sekunden, wenn die Höhe unter 255 liegt, "
				+ "und jede (Level) Sekunde über 255 Höhe. Entfernt den Flug bei 32 Haltbarkeit.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.FREQUENT_FLYER;
	}

	@Override
	public String getName() {
		return "frequent_flyer";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ELYTRA);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(DefaultEnchantments.ICARUS);
	}

}
