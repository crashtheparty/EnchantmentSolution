package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfBinding extends CustomEnchantment{
	
	public CurseOfBinding() {
		addDefaultDisplayName("Curse of Binding");
		addDefaultDisplayName(Language.GERMAN, "Fluch der Bindung");
		setTreasure(true);
		setDefaultFiftyConstant(25);
		setDefaultThirtyConstant(25);
		setDefaultFiftyModifier(0);
		setDefaultThirtyModifier(0);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(25);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(1);
		setDefaultThirtyMaxLevel(1);
		setDefaultWeight(Weight.VERY_RARE);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription("Prevents removal of the cursed item." + 
				StringUtils.LF + 
				"The cursed item cannot be removed from any armor slot (outside of Creative mode) unless the player dies or the item breaks.");
		addDefaultDescription(Language.GERMAN, "Verhindert das Entfernen des verfluchten Gegenstands." + 
				StringUtils.LF + 
				"Der verfluchte Gegenstand kann nicht aus einem Rüstungsplatz (außerhalb des Kreativmodus) " + 
				"entfernt werden, es sei denn, der Spieler stirbt oder der Gegenstand zerbricht.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.BINDING_CURSE;
	}

	@Override
	public String getName() {
		return "binding_curse";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.ELYTRA, ItemType.ARMOR);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.ELYTRA, ItemType.ARMOR);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
