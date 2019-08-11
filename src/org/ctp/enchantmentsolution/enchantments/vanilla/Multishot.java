package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Multishot extends CustomEnchantment{
	
	public Multishot() {
		super("Multishot", 35, 20, 0, 0, 20, 1, 1, 1, Weight.RARE, "Shoot multiple arrows at once.");
		addDefaultDisplayName(Language.GERMAN, "Mehrfachschuss");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Schießen Sie mehrere Pfeile gleichzeitig.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.MULTISHOT;
	}

	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.CROSSBOW);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.PIERCING);
	}

	@Override
	public String getName() {
		return "multishot";
	}

}
