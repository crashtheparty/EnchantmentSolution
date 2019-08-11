package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class BlastProtection extends CustomEnchantment{
	
	public BlastProtection() {
		super("Blast Protection", -8, -3, 14, 8, 1, 1, 4, 4, Weight.RARE, "Reduces explosion damage.\n" + 
				"Also reduces explosion knockback by (15 × level)%. If multiple pieces have the enchantment, only the highest level's reduction is used.");
		addDefaultDisplayName(Language.GERMAN, "Explosionsshutz");
		addDefaultDescription(Language.GERMAN, "Reduziert Explosionsschäden. " + 
				"\n" + 
				"Verringert außerdem den Explosionsrückschlag um (15 × Level)%. Wenn mehrere Teile die Verzauberung besitzen, " + 
				"wird nur die Reduzierung der höchsten Stufe verwendet.");
	}
	
	@Override
	public String getName() {
		return "blast_protection";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.PROTECTION_EXPLOSIONS;
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
		return Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE);
	}
}
