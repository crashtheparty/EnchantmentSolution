package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FlowerGift extends CustomEnchantment{
	
	public FlowerGift() {
		addDefaultDisplayName("Flower Gift");
		addDefaultDisplayName(Language.GERMAN, "Blumengeschenk");
		setDefaultFiftyConstant(30);
		setDefaultThirtyConstant(20);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(20);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
		addDefaultDescription("Has a chance of dropping flowers when right clicking them.");
		addDefaultDescription(Language.GERMAN, "Hat eine Chance, Blumen fallen zu lassen, wenn Sie mit der rechten Maustaste darauf klicken.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.FLOWER_GIFT;
	}

	@Override
	public String getName() {
		return "flower_gift";
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
		return Arrays.asList(DefaultEnchantments.GOLD_DIGGER, DefaultEnchantments.SOUL_REAPER);
	}

}
