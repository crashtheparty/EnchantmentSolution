package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Punch extends CustomEnchantment{
	
	public Punch() {
		super("Punch", -8, -8, 20, 20, 1, 1, 3, 2, Weight.RARE, "Increases knockback on bows.");
		addDefaultDisplayName(Language.GERMAN, "Schlag");
		addDefaultDescription(Language.GERMAN, "Erhöht den Rückstoß an den Bögen.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_KNOCKBACK;
	}
	
	@Override
	public String getName() {
		return "punch";
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
