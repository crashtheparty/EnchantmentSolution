package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Protection extends CustomEnchantment{
	
	public Protection() {
		addDefaultDisplayName("Protection");
		addDefaultDisplayName(Language.GERMAN, "Schutz");
		setDefaultFiftyConstant(-15);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(16);
		setDefaultThirtyModifier(11);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(4);
		setDefaultThirtyMaxLevel(4);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Reduces all damage, except damage from the Void, the /kill command, or hunger damage.");
		addDefaultDescription(Language.GERMAN, "Reduziert jeglichen Schaden, mit Ausnahme des Schadens durch die Leere, den Befehl / kill oder den Hunger-Schaden.");
	}

	@Override
	public String getName() {
		return "protection";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_ENVIRONMENTAL;
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
		return Arrays.asList(Enchantment.PROTECTION_PROJECTILE, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE);
	}
}
