package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Flame extends CustomEnchantment{
	
	public Flame() {
		super("Flame", 35, 20, 0, 0, 20, 1, 1, 1, Weight.RARE, "Flaming arrows.\nArrows are on fire when shot and deal 4 (2 Hearts) fire damage over 5 seconds.");
		addDefaultDisplayName(Language.GERMAN, "Flamme");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Brennende Pfeile.\nPfeile brennen beim Schießen und verursachen 5 Sekunden lang 4 Feuerschaden");
	}

	@Override
	public String getName() {
		return "flame";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.ARROW_FIRE;
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
