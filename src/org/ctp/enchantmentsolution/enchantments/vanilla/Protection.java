package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Protection extends CustomEnchantment{
	
	public Protection() {
		super("Protection", -15, -10, 16, 11, 1, 1, 4, 4, Weight.COMMON, "Reduces all damage, except damage from the Void, the /kill command, or hunger damage.");
		addDefaultDisplayName(Language.GERMAN, "Schutz");
		addDefaultDescription(Language.GERMAN, "Reduziert jeglichen Schaden, mit Ausnahme des Schadens durch die Leere, den Befehl / kill oder den Hunger-Schaden.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "保护");
		addDefaultDescription(Language.CHINA_SIMPLE, "减少受到的所有伤害, 除了虚空伤害、/kill 自杀和饥饿伤害.");
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
