package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SandVeil extends CustomEnchantment{
	
	public SandVeil() {
		addDefaultDisplayName("Sand Veil");
		addDefaultDisplayName(Language.GERMAN, "Sandschleier");
		setDefaultFiftyConstant(-12);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(13);
		setDefaultThirtyModifier(11);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Lowers accuracy of entity's attacks.");
		addDefaultDescription(Language.GERMAN, "Verringert die Genauigkeit der Angriffe der Entität.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SAND_VEIL;
	}

	@Override
	public String getName() {
		return "sand_veil";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOK);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HOES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
