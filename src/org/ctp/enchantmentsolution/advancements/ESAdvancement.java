package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.Advancement.Frame;
import org.ctp.enchantmentsolution.advancements.Rewards;

public enum ESAdvancement {

	ENCHANTMENT_SOLUTION("enchantment_solution", "ENCHANTED_BOOK", Arrays.asList(), 0), FISH_STICKS(
			ENCHANTMENT_SOLUTION, "fish_sticks", "COOKED_COD", Arrays.asList(new ESTrigger("cooked")),
			10), FED_FOR_A_LIFETIME(FISH_STICKS, "fed_for_a_lifetime", "COD",
					Arrays.asList(new ESTrigger("cod"), new ESTrigger("salmon"), new ESTrigger("tropical_fish"),
							new ESTrigger("pufferfish")),
					15), NEMO_ENIM_COQUIT(FISH_STICKS, "nemo_enim_coquit", "TROPICAL_FISH",
							Arrays.asList(new ESTrigger("tropical_fish")), 25), BREAKER_BREAKER(NEMO_ENIM_COQUIT,
									"breaker_breaker", "TORCH", Arrays.asList(new ESTrigger("torch")),
									25), THORGY(BREAKER_BREAKER, "thorgy", "WOLF_SPAWN_EGG",
											Arrays.asList(new ESTrigger("wolf")), 50), DID_YOU_REALLY_WAND_TO_DO_THAT(
													THORGY, "did_you_really_wand_to_do_that", "CARROT_ON_A_STICK",
													Arrays.asList(new ESTrigger("break")), 100,
													Frame.GOAL), FREE_PETS(DID_YOU_REALLY_WAND_TO_DO_THAT, "free_pets",
															"CARROT_ON_A_STICK",
															Arrays.asList(new ESTrigger("wolf"),
																	new ESTrigger("ocelot", 0, 0, 3),
																	new ESTrigger("cat", 0, 4, 0),
																	new ESTrigger("horse"), new ESTrigger("donkey"),
																	new ESTrigger("mule"), new ESTrigger("parrot"),
																	new ESTrigger("llama")),
															500,
															Frame.CHALLENGE), SHARING_IS_CARING(ENCHANTMENT_SOLUTION,
																	"sharing_is_caring", "EXPERIENCE_BOTTLE",
																	Arrays.asList(new ESTrigger("player")),
																	10), NOT_THAT_KIND(SHARING_IS_CARING,
																			"not_that_kind", "DIAMOND_SWORD",
																			Arrays.asList(new ESTrigger("chicken"),
																					new ESTrigger("cat", 0, 4, 0),
																					new ESTrigger("cow"),
																					new ESTrigger("fox", 0, 4, 0),
																					new ESTrigger("horse"),
																					new ESTrigger("llama"),
																					new ESTrigger("mushroom_cow"),
																					new ESTrigger("ocelot", 0, 0, 3),
																					new ESTrigger("panda", 0, 4, 0),
																					new ESTrigger("pig"),
																					new ESTrigger("rabbit"),
																					new ESTrigger("sheep"),
																					new ESTrigger("wolf"),
																					new ESTrigger("turtle")),
																			25), SUPER_CHARGED(NOT_THAT_KIND,
																					"super_charged", "DIAMOND_AXE",
																					Arrays.asList(
																							new ESTrigger("lightning")),
																					35), NOT_VERY_EFFECTIVE(
																							SUPER_CHARGED,
																							"not_very_effective",
																							"NAUTILUS_SHELL",
																							Arrays.asList(new ESTrigger(
																									"drowned")),
																							50), PRE_COMBAT_UPDATE(
																									NOT_VERY_EFFECTIVE,
																									"pre_combat_update",
																									"IRON_SWORD",
																									Arrays.asList(
																											new ESTrigger(
																													"combat_update")),
																									80), HEADHUNTER(
																											PRE_COMBAT_UPDATE,
																											"headhunter",
																											"PLAYER_HEAD",
																											Arrays.asList(
																													new ESTrigger(
																															"player_head")),
																											100,
																											Frame.GOAL), DOUBLE_HEADER(
																													HEADHUNTER,
																													"double_header",
																													"WITHER_SKELETON_SKULL",
																													Arrays.asList(
																															new ESTrigger(
																																	"wither_skull")),
																													150,
																													Frame.GOAL), SUPER_EFFECTIVE(
																															DOUBLE_HEADER,
																															"super_effective",
																															"HEART_OF_THE_SEA",
																															Arrays.asList(
																																	new ESTrigger(
																																			"boss")),
																															250,
																															Frame.GOAL), MOTHERLOAD(
																																	SUPER_EFFECTIVE,
																																	"motherload",
																																	"DRAGON_EGG",
																																	Arrays.asList(
																																			new ESTrigger(
																																					"dragon")),
																																	0,
																																	Frame.CHALLENGE), HEX_BAG(
																																			ENCHANTMENT_SOLUTION,
																																			"hex_bag",
																																			"WITCH_SPAWN_EGG",
																																			Arrays.asList(
																																					new ESTrigger(
																																							"player")),
																																			25), SEVEN_POINT_EIGHT(
																																					HEX_BAG,
																																					"seven_point_eight",
																																					"WATER_BUCKET",
																																					Arrays.asList(
																																							new ESTrigger(
																																									"drowning")),
																																					40), POSEIDONS_DAY_OFF(
																																							SEVEN_POINT_EIGHT,
																																							"poseidons_day_off",
																																							"PRISMARINE_SHARD",
																																							Arrays.asList(
																																									new ESTrigger(
																																											"day_off")),
																																							50), FISHIER_BUSINESS(
																																									POSEIDONS_DAY_OFF,
																																									"fishy_business",
																																									"COD",
																																									Arrays.asList(
																																											new ESTrigger(
																																													"cod"),
																																											new ESTrigger(
																																													"salmon"),
																																											new ESTrigger(
																																													"tropical_fish"),
																																											new ESTrigger(
																																													"pufferfish")),
																																									75), POSEIDON_REBORN(
																																											FISHIER_BUSINESS,
																																											"poseidon_reborn",
																																											"TRIDENT",
																																											Arrays.asList(
																																													new ESTrigger(
																																															"trident")),
																																											200,
																																											Frame.CHALLENGE), CERBERUS(
																																													POSEIDON_REBORN,
																																													"cerberus",
																																													"SOUL_SAND",
																																													Arrays.asList(
																																															new ESTrigger(
																																																	"obsidian")),
																																													1000,
																																													Frame.CHALLENGE), BONEMEAL_PLUS(
																																															ENCHANTMENT_SOLUTION,
																																															"bonemeal_plus",
																																															"BONE_MEAL",
																																															Arrays.asList(
																																																	new ESTrigger(
																																																			"bonemeal")),
																																															10), FOURTY_NINERS(
																																																	BONEMEAL_PLUS,
																																																	"fourty_niners",
																																																	"GOLD_BLOCK",
																																																	Arrays.asList(
																																																			new ESTrigger(
																																																					"goldblock",
																																																					81)),
																																																	20), CHICKEN_OR_THE_EGG(
																																																			FOURTY_NINERS,
																																																			"chicken_or_the_egg",
																																																			"EGG",
																																																			Arrays.asList(
																																																					new ESTrigger(
																																																							"egg")),
																																																			25), EGGED_BY_MYSELF(
																																																					CHICKEN_OR_THE_EGG,
																																																					"egged_by_myself",
																																																					"EGG",
																																																					Arrays.asList(
																																																							new ESTrigger(
																																																									"egg")),
																																																					40), MISSED(
																																																							EGGED_BY_MYSELF,
																																																							"missed",
																																																							"SAND",
																																																							Arrays.asList(
																																																									new ESTrigger(
																																																											"sand",
																																																											16)),
																																																							60), JUST_AS_SWEET(
																																																									MISSED,
																																																									"just_as_sweet",
																																																									"WITHER_ROSE",
																																																									Arrays.asList(
																																																											new ESTrigger(
																																																													"wither_rose")),
																																																									100,
																																																									Frame.GOAL,
																																																									4), LAAAGGGGGG(
																																																											ENCHANTMENT_SOLUTION,
																																																											"laaagggggg",
																																																											"FIREWORK_ROCKET",
																																																											Arrays.asList(
																																																													new ESTrigger(
																																																															"lag")),
																																																											10), EASY_OUT(
																																																													LAAAGGGGGG,
																																																													"easy_out",
																																																													"CAMPFIRE",
																																																													Arrays.asList(
																																																															new ESTrigger(
																																																																	"campfire")),
																																																													20,
																																																													Frame.TASK,
																																																													4), JUST_ADD_WATER(
																																																															EASY_OUT,
																																																															"just_add_water",
																																																															"WHITE_CONCRETE_POWDER",
																																																															Arrays.asList(
																																																																	new ESTrigger(
																																																																			"concrete")),
																																																															20), REPAIRED(
																																																																	JUST_ADD_WATER,
																																																																	"repaired",
																																																																	"STONE_BRICKS",
																																																																	Arrays.asList(
																																																																			new ESTrigger(
																																																																					"broken_bricks")),
																																																																	25), KEPT_ON_HAND(
																																																																			REPAIRED,
																																																																			"kept_on_hand",
																																																																			"ENCHANTED_BOOK",
																																																																			Arrays.asList(
																																																																					new ESTrigger(
																																																																							"soulbound")),
																																																																			50), FEAR_THE_REAPER(
																																																																					KEPT_ON_HAND,
																																																																					"fear_the_reaper",
																																																																					"DIAMOND_HOE",
																																																																					Arrays.asList(
																																																																							new ESTrigger(
																																																																									"reaper")),
																																																																					150,
																																																																					Frame.GOAL), HIGH_METABOLISM(
																																																																							FEAR_THE_REAPER,
																																																																							"high_metabolism",
																																																																							"GOLDEN_CARROT",
																																																																							Arrays.asList(
																																																																									new ESTrigger(
																																																																											"exhaustion")),
																																																																							200,
																																																																							Frame.GOAL), READY_AFTER_DEATH(
																																																																									HIGH_METABOLISM,
																																																																									"ready_after_death",
																																																																									"DIAMOND_CHESTPLATE",
																																																																									Arrays.asList(
																																																																											new ESTrigger(
																																																																													"soulbound")),
																																																																									400,
																																																																									Frame.GOAL), REAPED_THE_REAPER(
																																																																											READY_AFTER_DEATH,
																																																																											"reaped_the_reaper",
																																																																											"DIAMOND_HOE",
																																																																											Arrays.asList(
																																																																													new ESTrigger(
																																																																															"reaper")),
																																																																											1200,
																																																																											Frame.CHALLENGE), COFFEE_BREAK(
																																																																													ENCHANTMENT_SOLUTION,
																																																																													"coffee_break",
																																																																													"SUGAR",
																																																																													Arrays.asList(
																																																																															new ESTrigger(
																																																																																	"coffee")),
																																																																													20), THIS_GIRL_IS_ON_FIRE(
																																																																															COFFEE_BREAK,
																																																																															"this_girl_is_on_fire",
																																																																															"LAVA_BUCKET",
																																																																															Arrays.asList(
																																																																																	new ESTrigger(
																																																																																			"lava")),
																																																																															25), FLAME_KEEPER(
																																																																																	THIS_GIRL_IS_ON_FIRE,
																																																																																	"flame_keeper",
																																																																																	"FLINT_AND_STEEL",
																																																																																	Arrays.asList(
																																																																																			new ESTrigger(
																																																																																					"flame")),
																																																																																	40), MADE_FOR_WALKING(
																																																																																			FLAME_KEEPER,
																																																																																			"made_for_walking",
																																																																																			"DIAMOND_BOOTS",
																																																																																			Arrays.asList(
																																																																																					new ESTrigger(
																																																																																							"boots")),
																																																																																			60), DETERMINED_CHEATER(
																																																																																					MADE_FOR_WALKING,
																																																																																					"determined_cheater",
																																																																																					"OBSIDIAN",
																																																																																					Arrays.asList(
																																																																																							new ESTrigger(
																																																																																									"cheater")),
																																																																																					100,
																																																																																					Frame.GOAL), I_AINT_AFRAID_OF_NO_GHOSTS(
																																																																																							DETERMINED_CHEATER,
																																																																																							"i_aint_afraid_of_no_ghosts",
																																																																																							"WHITE_BED",
																																																																																							Arrays.asList(
																																																																																									new ESTrigger(
																																																																																											"unrest",
																																																																																											10)),
																																																																																							150,
																																																																																							Frame.GOAL), IM_YOU_BUT_SHORTER(
																																																																																									ENCHANTMENT_SOLUTION,
																																																																																									"im_you_but_shorter",
																																																																																									"ENDER_PEARL",
																																																																																									Arrays.asList(
																																																																																											new ESTrigger(
																																																																																													"enderpearl")),
																																																																																									30), PANZER_SOLDIER(
																																																																																											IM_YOU_BUT_SHORTER,
																																																																																											"panzer_soldier",
																																																																																											"OBSIDIAN",
																																																																																											Arrays.asList(
																																																																																													new ESTrigger(
																																																																																															"tank")),
																																																																																											60), TOO_CLOSE(
																																																																																													PANZER_SOLDIER,
																																																																																													"too_close",
																																																																																													"ELYTRA",
																																																																																													Arrays.asList(
																																																																																															new ESTrigger(
																																																																																																	"failure")),
																																																																																													75), ARMORED_EVOLUTION(
																																																																																															TOO_CLOSE,
																																																																																															"armored_evolution",
																																																																																															"IRON_CHESTPLATE",
																																																																																															Arrays.asList(
																																																																																																	new ESTrigger(
																																																																																																			"armored")),
																																																																																															100,
																																																																																															Frame.GOAL), EXTRA_POWER(
																																																																																																	ARMORED_EVOLUTION,
																																																																																																	"extra_power",
																																																																																																	"ENCHANTED_GOLDEN_APPLE",
																																																																																																	Arrays.asList(
																																																																																																			new ESTrigger(
																																																																																																					"power")),
																																																																																																	150,
																																																																																																	Frame.GOAL), CRUISING_ALTITUDE(
																																																																																																			EXTRA_POWER,
																																																																																																			"cruising_altitude",
																																																																																																			"ELYTRA",
																																																																																																			Arrays.asList(
																																																																																																					new ESTrigger(
																																																																																																							"elytra")),
																																																																																																			150,
																																																																																																			Frame.GOAL), GRAPHENE_ARMOR(
																																																																																																					CRUISING_ALTITUDE,
																																																																																																					"graphene_armor",
																																																																																																					"DIAMOND_CHESTPLATE",
																																																																																																					Arrays.asList(
																																																																																																							new ESTrigger(
																																																																																																									"toughness")),
																																																																																																					200,
																																																																																																					Frame.GOAL), DANGER_DEFEATED(
																																																																																																							GRAPHENE_ARMOR,
																																																																																																							"danger_defeated",
																																																																																																							"NETHER_STAR",
																																																																																																							Arrays.asList(
																																																																																																									new ESTrigger(
																																																																																																											"wither")),
																																																																																																							500,
																																																																																																							Frame.CHALLENGE), DIVINE_RETRIBUTION(
																																																																																																									DANGER_DEFEATED,
																																																																																																									"divine_retribution",
																																																																																																									"TOTEM_OF_UNDYING",
																																																																																																									Arrays.asList(
																																																																																																											new ESTrigger(
																																																																																																													"retribution")),
																																																																																																									1000,
																																																																																																									Frame.CHALLENGE), LOOK_WHAT_YOU_MADE_ME_DO(
																																																																																																											ENCHANTMENT_SOLUTION,
																																																																																																											"look_what_you_made_me_do",
																																																																																																											"CROSSBOW",
																																																																																																											Arrays.asList(
																																																																																																													new ESTrigger(
																																																																																																															"pillage")),
																																																																																																											20,
																																																																																																											Frame.TASK,
																																																																																																											4), JUST_DIE_ALREADY(
																																																																																																													LOOK_WHAT_YOU_MADE_ME_DO,
																																																																																																													"just_die_already",
																																																																																																													"PHANTOM_SPAWN_EGG",
																																																																																																													Arrays.asList(
																																																																																																															new ESTrigger(
																																																																																																																	"phantom")),
																																																																																																													75,
																																																																																																													Frame.TASK,
																																																																																																													4), UNDERKILL(
																																																																																																															JUST_DIE_ALREADY,
																																																																																																															"underkill",
																																																																																																															"DRAGON_EGG",
																																																																																																															Arrays.asList(
																																																																																																																	new ESTrigger(
																																																																																																																			"dragon")),
																																																																																																															100,
																																																																																																															Frame.GOAL,
																																																																																																															4), WHERE_DID_THAT_COME_FROM(
																																																																																																																	UNDERKILL,
																																																																																																																	"where_did_that_come_from",
																																																																																																																	"ARROW",
																																																																																																																	Arrays.asList(
																																																																																																																			new ESTrigger(
																																																																																																																					"sniper")),
																																																																																																																	500,
																																																																																																																	Frame.CHALLENGE), THAT_FOOD_IS_FINE(
																																																																																																																			ENCHANTMENT_SOLUTION,
																																																																																																																			"that_food_is_fine",
																																																																																																																			"POISONOUS_POTATO",
																																																																																																																			Arrays.asList(
																																																																																																																					new ESTrigger(
																																																																																																																							"food")),
																																																																																																																			25), DEFLECTION(
																																																																																																																					THAT_FOOD_IS_FINE,
																																																																																																																					"deflection",
																																																																																																																					"SHIELD",
																																																																																																																					Arrays.asList(
																																																																																																																							new ESTrigger(
																																																																																																																									"shield")),
																																																																																																																					100,
																																																																																																																					Frame.GOAL), IRON_MAN(
																																																																																																																							DEFLECTION,
																																																																																																																							"iron_man",
																																																																																																																							"IRON_BLOCK",
																																																																																																																							Arrays.asList(
																																																																																																																									new ESTrigger(
																																																																																																																											"blocked")),
																																																																																																																							500,
																																																																																																																							Frame.CHALLENGE), IRONT_YOU_GLAD(
																																																																																																																									ENCHANTMENT_SOLUTION,
																																																																																																																									"iront_you_glad",
																																																																																																																									"IRON_INGOT",
																																																																																																																									Arrays.asList(
																																																																																																																											new ESTrigger(
																																																																																																																													"iron")),
																																																																																																																									25), FAST_AND_FURIOUS(
																																																																																																																											IRONT_YOU_GLAD,
																																																																																																																											"fast_and_furious",
																																																																																																																											"DIAMOND_PICKAXE",
																																																																																																																											Arrays.asList(
																																																																																																																													new ESTrigger(
																																																																																																																															"diamond_pickaxe")),
																																																																																																																											40), HEY_IT_WORKS(
																																																																																																																													FAST_AND_FURIOUS,
																																																																																																																													"hey_it_works",
																																																																																																																													"PURPLE_SHULKER_BOX",
																																																																																																																													Arrays.asList(
																																																																																																																															new ESTrigger(
																																																																																																																																	"shulker_box")),
																																																																																																																													75), NO_PANIC(
																																																																																																																															HEY_IT_WORKS,
																																																																																																																															"no_panic",
																																																																																																																															"LAVA_BUCKET",
																																																																																																																															Arrays.asList(
																																																																																																																																	new ESTrigger(
																																																																																																																																			"lava")),
																																																																																																																															100,
																																																																																																																															Frame.GOAL), OVER_9000(
																																																																																																																																	NO_PANIC,
																																																																																																																																	"over_9000",
																																																																																																																																	"DIAMOND_PICKAXE",
																																																																																																																																	Arrays.asList(
																																																																																																																																			new ESTrigger(
																																																																																																																																					"stone",
																																																																																																																																					9001)),
																																																																																																																																	1000,
																																																																																																																																	Frame.CHALLENGE);
	private ESAdvancement parent;
	private Material icon;
	private boolean isEnabled = false;
	private NamespacedKey namespace;
	private List<ESTrigger> triggers = new ArrayList<ESTrigger>();
	private int activatedVersion = 0;
	private Rewards rewards;
	private Frame frame;

	ESAdvancement(String key, String icon, List<ESTrigger> triggers, int exp) {
		this(null, key, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String key, String icon, List<ESTrigger> triggers, int exp) {
		this(parent, key, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String key, String icon, List<ESTrigger> triggers, int exp, Frame frame) {
		this(parent, key, icon, triggers, exp, frame, 0);
	}

	ESAdvancement(ESAdvancement parent, String key, String icon, List<ESTrigger> triggers, int exp, Frame frame,
			int activatedVersion) {
		namespace = new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/" + key);
		for(Material m: Material.values()) {
			if (m.name().equals(icon)) {
				this.icon = m;
				break;
			}
		}
		if (this.icon == null) {
			this.icon = Material.BARRIER;
		}
		this.triggers = triggers;
		this.activatedVersion = activatedVersion;
		rewards = new Rewards().setExperience(exp);
		this.frame = frame;
		this.parent = parent;
	}

	public NamespacedKey getNamespace() {
		return namespace;
	}

	public void setNamespace(NamespacedKey namespace) {
		this.namespace = namespace;
	}

	public ESAdvancement getParent() {
		return parent;
	}

	public void setParent(ESAdvancement parent) {
		this.parent = parent;
	}

	public Material getIcon() {
		return icon;
	}

	public void setIcon(Material icon) {
		this.icon = icon;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public List<ESTrigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<ESTrigger> triggers) {
		this.triggers = triggers;
	}

	public int getActivatedVersion() {
		return activatedVersion;
	}

	public Rewards getRewards() {
		return rewards;
	}

	public void setRewards(Rewards rewards) {
		this.rewards = rewards;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
}
