package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class SandVeil extends CustomEnchantment{
	
	public SandVeil() {
		super("Sand Veil", -12, -10, 13, 11, 1, 1, 6, 5, Weight.COMMON, "Lowers accuracy of entity's attacks.");
		addDefaultDisplayName(Language.GERMAN, "Sandschleier");
		addDefaultDescription(Language.GERMAN, "Verringert die Genauigkeit der Angriffe der Entität.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "沙隐");
		addDefaultDescription(Language.CHINA_SIMPLE, "降低实体攻击的精准度.");
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
		return Arrays.asList(ItemType.HOES);
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
