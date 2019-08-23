package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class CurseOfBinding extends CustomEnchantment{
	
	public CurseOfBinding() {
		super("Curse of Binding", 25, 25, 0, 0, 1, 1, 1, 1, Weight.VERY_RARE, "Prevents removal of the cursed item.\n" + 
				"The cursed item cannot be removed from any armor slot (outside of Creative mode) unless the player dies or the item breaks.");
		addDefaultDisplayName(Language.GERMAN, "Fluch der Bindung");
		setTreasure(true);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription(Language.GERMAN, "Verhindert das Entfernen des verfluchten Gegenstands." + 
				"\n" + 
				"Der verfluchte Gegenstand kann nicht aus einem Rüstungsplatz (außerhalb des Kreativmodus) " + 
				"entfernt werden, es sei denn, der Spieler stirbt oder der Gegenstand zerbricht.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "绑定诅咒");
		addDefaultDescription(Language.CHINA_SIMPLE, "无法从身上取下装备.\n除非玩家死亡或装备损坏.");
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
