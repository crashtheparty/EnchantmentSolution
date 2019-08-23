package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class BaneOfArthropods extends CustomEnchantment{
	
	public BaneOfArthropods() {
		super("Bane of Arthropods", -4, -3, 9, 8, 1, 1, 6, 5, Weight.UNCOMMON, "Increases damage to \"arthropod\" mobs "
				+ "(spiders, cave spiders, silverfish and endermites).\nEach level separately adds 2.5 (half heart) extra damage to "
				+ "each hit, to \"arthropods\" only.\nThe enchantment will also cause \"arthropods\" to have the Slowness IV effect when hit.");
		addDefaultDisplayName(Language.GERMAN, "Nemesis der Gliederfüßer");
		addDefaultDescription(Language.GERMAN, "Erhöht den Schaden an \"Arthropoden\" -Mobs (Spinnen, Höhlenspinnen, Silberfischen und Endermiten)." + 
				"\n" + 
				"Jedes Level fügt jedem Treffer separat 2,5 (halbes Herz) Schaden hinzu, nur für \"Arthropoden\"." + 
				"\n" + 
				"Die Verzauberung bewirkt auch, dass \"Arthropoden\" den Effekt von Slowness IV haben, wenn sie getroffen werden");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "节肢杀手");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加对节肢生物的伤害(蜘蛛、洞穴蜘蛛、蠹虫和末影螨).\n每一级增加2.5点伤害.\n该附魔同时会给予节肢生物 缓慢IV 的效果.");
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
		return Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, DefaultEnchantments.QUICK_STRIKE);
	}
}
