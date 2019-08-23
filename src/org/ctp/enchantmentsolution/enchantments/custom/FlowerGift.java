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
		super("Flower Gift", 30, 20, 0, 0, 20, 1, 1, 1, Weight.VERY_RARE, "Has a chance of dropping flowers when right clicking them.");
		addDefaultDisplayName(Language.GERMAN, "Blumengeschenk");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Hat eine Chance, Blumen fallen zu lassen, wenn Sie mit der rechten Maustaste darauf klicken.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "花之礼");
		addDefaultDescription(Language.CHINA_SIMPLE, "右击花有几率获得花.");
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
