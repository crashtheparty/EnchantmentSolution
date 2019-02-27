package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FireAspect extends CustomEnchantment{
	
	public FireAspect() {
		addDefaultDisplayName("Fire Aspect");
		addDefaultDisplayName(Language.GERMAN, "Verbrennung");
		setDefaultFiftyConstant(-25);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(30);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(5);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Sets the target on fire." + 
				StringUtils.LF + 
				"Fire Aspect adds 80 fire ticks (4 seconds of burning) per level to the target.");
		addDefaultDescription(Language.GERMAN, "Setzt das Ziel in Brand." + 
				StringUtils.LF + 
				"Feueraspekt fügt dem Ziel 80 Feuerzecken (4 Sekunden Brennen) pro Stufe hinzu.");
	}

	@Override
	public String getName() {
		return "fire_aspect";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FIRE_ASPECT;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
