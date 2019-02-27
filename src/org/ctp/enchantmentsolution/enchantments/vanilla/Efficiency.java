package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Efficiency extends CustomEnchantment{
	
	public Efficiency() {
		addDefaultDisplayName("Efficiency");
		addDefaultDisplayName(Language.GERMAN, "Effizienz");
		setDefaultFiftyConstant(-11);
		setDefaultThirtyConstant(-9);
		setDefaultFiftyModifier(12);
		setDefaultThirtyModifier(10);
		setDefaultFiftyMaxConstant(45);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.COMMON);
		addDefaultDescription("Increases mining speed." + 
				StringUtils.LF + 
				"One must use the proper tool for a block in order to receive the speed. Does not matter if you mine it with the incorrect tier.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Mining-Geschwindigkeit." + 
				StringUtils.LF + 
				"Man muss das richtige Werkzeug für einen Block verwenden, um die Geschwindigkeit zu erhalten. " + 
				"Es spielt keine Rolle, wenn Sie es mit der falschen Stufe abbauen.");
	}

	@Override
	public String getName() {
		return "efficiency";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DIG_SPEED;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.TOOLS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.TOOLS, ItemType.SHEARS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
