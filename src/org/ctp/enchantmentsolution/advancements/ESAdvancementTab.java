package org.ctp.enchantmentsolution.advancements;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.ctp.crashapi.resources.advancements.Advancement;
import org.ctp.crashapi.resources.advancements.AdvancementFactory;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class ESAdvancementTab {

	public static ESAdvancementTab AGRICULTURAL_REVOLUTION = new ESAdvancementTab(new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false), Arrays.asList(ESAdvancement.AGRICULTURAL_REVOLUTION, ESAdvancement.FISH_STICKS, ESAdvancement.FED_FOR_A_LIFETIME, ESAdvancement.FISHY_BUSINESS, ESAdvancement.NEMO_ENIM_COQUIT, ESAdvancement.BONEMEAL_PLUS, ESAdvancement.JUST_AS_SWEET, ESAdvancement.NOT_THAT_KIND, ESAdvancement.HIGH_METABOLISM, ESAdvancement.FOURTY_NINERS, ESAdvancement.CHICKEN_OR_THE_EGG, ESAdvancement.EGGED_BY_MYSELF, ESAdvancement.THORGY, ESAdvancement.FREE_PETS, ESAdvancement.WILDLIFE_CONSERVATION, ESAdvancement.MEAT_READY_TO_EAT, ESAdvancement.YUMMY_REPAIRS, ESAdvancement.HUNGRY_HIPPOS, ESAdvancement.THUMBS_UP, ESAdvancement.REFORESTATION), "block/hay_block_side");

	public static ESAdvancementTab INDUSTRIAL_REVOLUTION = new ESAdvancementTab(new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false), Arrays.asList(ESAdvancement.INDUSTRIAL_REVOLUTION, ESAdvancement.EASY_OUT, ESAdvancement.JUST_ADD_WATER, ESAdvancement.REPAIRED, ESAdvancement.FLAME_KEEPER, ESAdvancement.DETERMINED_CHEATER, ESAdvancement.IRONT_YOU_GLAD, ESAdvancement.FAST_AND_FURIOUS, ESAdvancement.CARPET_BOMBS, ESAdvancement.OVER_9000, ESAdvancement.HEY_IT_WORKS, ESAdvancement.NO_PANIC, ESAdvancement.STAINLESS_STEEL, ESAdvancement.NETHER_DULL, ESAdvancement.SCOURGE_OF_THE_FOREST, ESAdvancement.DEFORESTATION), "block/hopper_outside");

	public static ESAdvancementTab SCIENTIFIC_REVOLUTION = new ESAdvancementTab(new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false), Arrays.asList(ESAdvancement.SCIENTIFIC_REVOLUTION, ESAdvancement.BREAKER_BREAKER, ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT, ESAdvancement.SHARING_IS_CARING, ESAdvancement.LAAAGGGGGG, ESAdvancement.KEPT_ON_HAND, ESAdvancement.FEAR_THE_REAPER, ESAdvancement.READY_AFTER_DEATH, ESAdvancement.REAPED_THE_REAPER, ESAdvancement.PLAGUE_INC, ESAdvancement.EXTERMINATION, ESAdvancement.BROKEN_DREAMS, ESAdvancement.ENVIRONMENTAL_PROTECTION, ESAdvancement.BLIND_AS_A_BAT, ESAdvancement.LIGHT_AS_A_FEATHER, ESAdvancement.WORLD_RECORD, ESAdvancement.REPLENISHED, ESAdvancement.THE_SNOWMAN), "block/bookshelf");

	public static ESAdvancementTab IMPERIAL_REVOLUTION = new ESAdvancementTab(new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false), Arrays.asList(ESAdvancement.IMPERIAL_REVOLUTION, ESAdvancement.HEX_BAG, ESAdvancement.SUPER_CHARGED, ESAdvancement.DOUBLE_DAMAGE, ESAdvancement.SEVEN_POINT_EIGHT, ESAdvancement.NOT_VERY_EFFECTIVE, ESAdvancement.SUPER_EFFECTIVE, ESAdvancement.PRE_COMBAT_UPDATE, ESAdvancement.HEADHUNTER, ESAdvancement.DOUBLE_HEADER, ESAdvancement.SPOOKY_SCARY_SKELETON, ESAdvancement.MOTHERLOAD, ESAdvancement.POSEIDONS_DAY_OFF, ESAdvancement.POSEIDON_REBORN, ESAdvancement.CERBERUS, ESAdvancement.MISSED, ESAdvancement.SAVING_GRACE, ESAdvancement.LOOK_WHAT_YOU_MADE_ME_DO, ESAdvancement.JUST_DIE_ALREADY, ESAdvancement.UNDERKILL, ESAdvancement.WHERE_DID_THAT_COME_FROM, ESAdvancement.KILL_THE_MESSENGER, ESAdvancement.BLAST_OFF, ESAdvancement.PENETRATION, ESAdvancement.KILIMANJARO, ESAdvancement.KNOCKBACK_REVERSED, ESAdvancement.BEFORE_I_MELT_AWAY), "block/diamond_block");

	public static ESAdvancementTab MECHANICAL_REVOLUTION = new ESAdvancementTab(new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false), Arrays.asList(ESAdvancement.MECHANICAL_REVOLUTION, ESAdvancement.COFFEE_BREAK, ESAdvancement.TOO_HIGH, ESAdvancement.EVENING_STROLL, ESAdvancement.SLACKIN, ESAdvancement.SPIDER_SENSES, ESAdvancement.I_AINT_AFRAID_OF_NO_GHOSTS, ESAdvancement.THIS_GIRL_IS_ON_FIRE, ESAdvancement.MADE_FOR_WALKING, ESAdvancement.IM_YOU_BUT_SHORTER, ESAdvancement.PANZER_SOLDIER, ESAdvancement.ARMORED_EVOLUTION, ESAdvancement.GRAPHENE_ARMOR, ESAdvancement.DANGER_DEFEATED, ESAdvancement.EXTRA_POWER, ESAdvancement.DIVINE_RETRIBUTION, ESAdvancement.TOO_CLOSE, ESAdvancement.CRUISING_ALTITUDE, ESAdvancement.DEFLECTION, ESAdvancement.IRON_MAN, ESAdvancement.SIMPLE_REPAIR, ESAdvancement.STICKY_BEES), "block/piston_top");

	public static List<ESAdvancementTab> getAllTabs() {
		return Arrays.asList(AGRICULTURAL_REVOLUTION, INDUSTRIAL_REVOLUTION, SCIENTIFIC_REVOLUTION, IMPERIAL_REVOLUTION, MECHANICAL_REVOLUTION);
	}

	private final AdvancementFactory factory;
	private final List<ESAdvancement> advancements;
	private LinkedHashMap<ESAdvancement, Advancement> registered;
	private final String background;

	private ESAdvancementTab(AdvancementFactory factory, List<ESAdvancement> advancements, String background) {
		this.factory = factory;
		this.advancements = advancements;
		registered = new LinkedHashMap<>();
		this.background = background;
	}

	public AdvancementFactory getFactory() {
		return factory;
	}

	public List<ESAdvancement> getAdvancements() {
		return advancements;
	}

	public void register(ESAdvancement esa, Advancement ad) {
		registered.put(esa, ad);
	}

	public Advancement getRegistered(ESAdvancement esa) {
		if (registered.containsKey(esa)) return registered.get(esa);
		return null;
	}

	public String getBackground() {
		return background;
	}
}
