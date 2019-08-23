package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class Loyalty extends CustomEnchantment{

	public Loyalty() {
		super("Loyalty", 7, 5, 11, 7, 1, 1, 3, 3, Weight.UNCOMMON, "Returns a thrown trident after it hits something.");
		addDefaultDisplayName(Language.GERMAN, "Treue");
		addDefaultDescription(Language.GERMAN, "Gibt einen geworfenen Dreizack zurück, nachdem er etwas getroffen hat.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "忠诚");
		addDefaultDescription(Language.CHINA_SIMPLE, "三叉戟击中后会返回到你手中.");
	}

	@Override
	public String getName() {
		return "loyalty";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.LOYALTY;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TRIDENT);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.RIPTIDE);
	}
}
