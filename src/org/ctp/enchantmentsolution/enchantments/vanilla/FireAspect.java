package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FireAspect extends CustomEnchantment{
	
	public FireAspect() {
		super("Fire Aspect", -25, -10, 30, 20, 5, 1, 3, 2, Weight.RARE, "Sets the target on fire.\n" + 
				"Fire Aspect adds 80 fire ticks (4 seconds of burning) per level to the target.");
		addDefaultDisplayName(Language.GERMAN, "Verbrennung");
		addDefaultDescription(Language.GERMAN, "Setzt das Ziel in Brand.\nFeueraspekt fügt dem Ziel 80 Feuerzecken (4 Sekunden Brennen) pro Stufe hinzu.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "火焰附加");
		addDefaultDescription(Language.CHINA_SIMPLE, "使目标着火.\n火焰附加每级持续4秒.");
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
		return Arrays.asList(DefaultEnchantments.QUICK_STRIKE);
	}
}
