package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Power extends CustomEnchantment{
	
	public Power() {
		addDefaultDisplayName("Power");
		addDefaultDisplayName(Language.GERMAN, "Stärke");
		setDefaultFiftyConstant(-10);
		setDefaultThirtyConstant(-9);
		setDefaultFiftyModifier(11);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Increases arrow damage by 25% × (level + 1), rounded up to nearest half-heart.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Pfeilschaden um 25% × (Stufe + 1), aufgerundet auf das nächste Herz.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_DAMAGE;
	}

	@Override
	public String getName() {
		return "power";
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
