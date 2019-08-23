package org.ctp.enchantmentsolution.enchantments.custom;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfExhaustion extends CustomEnchantment{
	
	public CurseOfExhaustion() {
		super("Curse of Exhaustion", 25, 25, 0, 0, 1, 1, 1, 1, Weight.VERY_RARE, "Increases exhaustion when equipped.");
		addDefaultDisplayName(Language.GERMAN, "Fluch der Erschöpfung");
		setTreasure(true);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription(Language.GERMAN, "Erhöht die Erschöpfung, wenn ausgerüstet.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "");
		addDefaultDescription(Language.CHINA_SIMPLE, "");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return DefaultEnchantments.CURSE_OF_EXHAUSTION;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ENCHANTABLE);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ALL);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}

	@Override
	public String getName() {
		return "exhaustion_curse";
	}

}
