package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Smite extends CustomEnchantment{
	
	public Smite() {
		addDefaultDisplayName("Smite");
		addDefaultDisplayName(Language.GERMAN, "Bann");
		setDefaultFiftyConstant(-4);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(9);
		setDefaultThirtyModifier(8);
		setDefaultFiftyMaxConstant(18);
		setDefaultThirtyMaxConstant(20);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Increases damage to \"undead\" mobs (skeletons, zombies, withers, wither skeletons, zombie pigmen, skeleton horses and zombie horses)" + 
				StringUtils.LF + 
				"Each level separately adds 2.5 (half heart) extra damage to each hit, to \"undead\" mobs only.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Schaden \"untoter\" Mobs " + 
				"(Skelette, Zombies, Widerrist, Widerrist-Skelette, Zombie-Schweiner, Skelettpferde und Zombie-Pferde). " + 
				StringUtils.LF + 
				"Jeder Level fügt jedem Treffer separat 2,5 (halbes Herz) zusätzlichen Schaden hinzu, nur \"Untoten\" -Mobs.");
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_UNDEAD;
	}
	
	@Override
	public String getName() {
		return "smite";
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS, ItemType.AXES);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_ALL);
	}
}
