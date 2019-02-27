package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfVanishing extends CustomEnchantment{
	
	public CurseOfVanishing() {
		addDefaultDisplayName("Curse of Vanishing");
		addDefaultDisplayName(Language.GERMAN, "Fluch des Verschwindens");
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
		addDefaultDescription("Causes the item to disappear on death." + 
				StringUtils.LF + 
				"When the player dies, the item disappears instead of dropping on the ground. The item may still be dropped normally.");
		addDefaultDescription(Language.GERMAN, "Bewirkt, dass der Gegenstand beim Tod verschwindet." + 
				StringUtils.LF + 
				"Wenn der Spieler stirbt, verschwindet der Gegenstand, anstatt auf den Boden zu fallen. Der Artikel kann noch normal abgeworfen werden.");
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
		return Arrays.asList();
	}
}
