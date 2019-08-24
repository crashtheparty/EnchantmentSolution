package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Unrest extends CustomEnchantment{

	public Unrest() {
		super("Unrest", 15, 1, 0, 0, 15, 1, 1, 1, Weight.RARE, "Night vision at the cost of more phantom spawning.");
		addDefaultDisplayName(Language.GERMAN, "Unruhe");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Nachtsicht auf Kosten eines Phantomlaichens.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "躁动");
		addDefaultDescription(Language.CHINA_SIMPLE, "获得夜视效果，但是会有更多幻翼生成.");
	}

	@Override
	public String getName() {
		return "unrest";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.UNREST;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.HELMETS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.WATER_WORKER, DefaultEnchantments.NO_REST);
	}
}
