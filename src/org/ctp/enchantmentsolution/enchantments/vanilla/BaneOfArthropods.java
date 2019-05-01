package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class BaneOfArthropods extends CustomEnchantment{
	
	public BaneOfArthropods() {
		addDefaultDisplayName("Bane of Arthropods");
		addDefaultDisplayName(Language.GERMAN, "Nemesis der Gliederfüßer");
		setDefaultFiftyConstant(-4);
		setDefaultThirtyConstant(-3);
		setDefaultFiftyModifier(9);
		setDefaultThirtyModifier(8);
		setDefaultFiftyStartLevel(1);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(6);
		setDefaultThirtyMaxLevel(5);
		setDefaultWeight(Weight.UNCOMMON);
		addDefaultDescription("Increases damage to \"arthropod\" mobs (spiders, cave spiders, silverfish and endermites)." + 
				"\n" + 
				"Each level separately adds 2.5 (half heart) extra damage to each hit, to \"arthropods\" only." + 
				"\n" + 
				"The enchantment will also cause \"arthropods\" to have the Slowness IV effect when hit.");
		addDefaultDescription(Language.GERMAN, "Erhöht den Schaden an \"Arthropoden\" -Mobs (Spinnen, Höhlenspinnen, Silberfischen und Endermiten)." + 
				"\n" + 
				"Jedes Level fügt jedem Treffer separat 2,5 (halbes Herz) Schaden hinzu, nur für \"Arthropoden\"." + 
				"\n" + 
				"Die Verzauberung bewirkt auch, dass \"Arthropoden\" den Effekt von Slowness IV haben, wenn sie getroffen werden");
	}

	@Override
	public String getName() {
		return "bane_of_arthropods";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DAMAGE_ARTHROPODS;
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
		return Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD);
	}
}
