package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class CurseOfVanishing extends CustomEnchantment{
	
	public CurseOfVanishing() {
		super("Curse of Vanishing", 25, 25, 0, 0, 1, 1, 1, 1, Weight.VERY_RARE, "Causes the item to disappear on death.\n" + 
				"When the player dies, the item disappears instead of dropping on the ground. The item may still be dropped normally.");
		addDefaultDisplayName(Language.GERMAN, "Fluch des Verschwindens");
		setTreasure(true);
		setMaxLevelOne(true);
		setCurse(true);
		addDefaultDescription(Language.GERMAN, "Bewirkt, dass der Gegenstand beim Tod verschwindet." + 
				"\n" + 
				"Wenn der Spieler stirbt, verschwindet der Gegenstand, anstatt auf den Boden zu fallen. Der Artikel kann noch normal abgeworfen werden.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "消失诅咒");
		addDefaultDescription(Language.CHINA_SIMPLE, "使物品在玩家死亡时消失.");
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.VANISHING_CURSE;
	}

	@Override
	public String getName() {
		return "vanishing_curse";
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
		return Arrays.asList(DefaultEnchantments.SOULBOUND);
	}
}
