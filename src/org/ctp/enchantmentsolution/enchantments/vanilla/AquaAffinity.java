package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class AquaAffinity extends CustomEnchantment{

	public AquaAffinity() {
		super("Aqua Affinity", 15, 1, 0, 0, 15, 1, 1, 1, Weight.RARE, "Increases underwater mining rate.");
		addDefaultDisplayName(Language.GERMAN, "Wasseraffinität");
		setMaxLevelOne(true);
		addDefaultDescription(Language.GERMAN, "Erhöht die Unterwasserabbaugeschwindigkeit.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "水下速掘");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加水下挖掘速度.");
	}
	
	@Override
	public String getName() {
		return "aqua_affinity";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.WATER_WORKER;
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
		return Arrays.asList(DefaultEnchantments.NO_REST, DefaultEnchantments.UNREST);
	}
}
