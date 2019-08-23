package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class SplatterFest extends CustomEnchantment{
	
	public SplatterFest() {
		super("Splatter Fest", 25, 25, 0, 0, 20, 1, 1, 1, Weight.RARE, "Shoots eggs out of hoe on left click.");
		addDefaultDisplayName(Language.GERMAN, "Ei Splatter");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Schießt mit dem Linksklick Eier aus der Hacke.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "飞蛋");
		addDefaultDescription(Language.CHINA_SIMPLE, "左击发射鸡蛋.");
	}

	@Override
	public String getName() {
		return "splatter_fest";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.SPLATTER_FEST;
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
