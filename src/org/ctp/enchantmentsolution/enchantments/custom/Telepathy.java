package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Telepathy extends CustomEnchantment{
	
	public Telepathy() {
		super("Telepathy", 65, 35, 0, 0, 40, 1, 1, 1, Weight.VERY_RARE, "Items mined go straight into your inventory.");
		addDefaultDisplayName(Language.GERMAN, "Telepathie");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Abgebaute Gegenstände gelangen direkt in Ihr Inventar.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.TELEPATHY;
	}

	@Override
	public String getName() {
		return "telepathy";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
