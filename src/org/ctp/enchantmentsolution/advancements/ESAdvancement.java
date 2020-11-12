package org.ctp.enchantmentsolution.advancements;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.resources.advancements.Advancement.Frame;
import org.ctp.crashapi.resources.advancements.CrashAdvancement;
import org.ctp.crashapi.resources.advancements.CrashTrigger;
import org.ctp.crashapi.resources.advancements.Rewards;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public enum ESAdvancement implements CrashAdvancement {

	AGRICULTURAL_REVOLUTION("DIAMOND_HOE", Arrays.asList(), 0),
	FISH_STICKS(AGRICULTURAL_REVOLUTION, "COOKED_COD", Arrays.asList(new CrashTrigger("cooked")), 10),
	FED_FOR_A_LIFETIME(FISH_STICKS, "COD", Arrays.asList(new CrashTrigger("cod"), new CrashTrigger("salmon"), new CrashTrigger("tropical_fish"), new CrashTrigger("pufferfish")), 15),
	FISHY_BUSINESS(FED_FOR_A_LIFETIME, "COD", Arrays.asList(new CrashTrigger("cod"), new CrashTrigger("salmon"), new CrashTrigger("tropical_fish"), new CrashTrigger("pufferfish")), 75),
	NEMO_ENIM_COQUIT(FISH_STICKS, "TROPICAL_FISH", Arrays.asList(new CrashTrigger("tropical_fish")), 25),
	BONEMEAL_PLUS(AGRICULTURAL_REVOLUTION, "BONE_MEAL", Arrays.asList(new CrashTrigger("bonemeal")), 10),
	JUST_AS_SWEET(BONEMEAL_PLUS, "WITHER_ROSE", Arrays.asList(new CrashTrigger("wither_rose")), 100, Frame.GOAL, 4),
	NOT_THAT_KIND(AGRICULTURAL_REVOLUTION, "DIAMOND_SWORD", Arrays.asList(new CrashTrigger("chicken"), new CrashTrigger("strider", 0, 12, 0), new CrashTrigger("hoglin", 0, 12, 0), new CrashTrigger("cat", 0, 4, 0), new CrashTrigger("cow"), new CrashTrigger("fox", 0, 4, 0), new CrashTrigger("horse"), new CrashTrigger("mule"), new CrashTrigger("donkey"), new CrashTrigger("llama"), new CrashTrigger("mushroom_cow"), new CrashTrigger("ocelot", 0, 0, 3), new CrashTrigger("panda", 0, 4, 0), new CrashTrigger("bee", 0, 9, 0), new CrashTrigger("pig"), new CrashTrigger("rabbit"), new CrashTrigger("sheep"), new CrashTrigger("wolf"), new CrashTrigger("turtle")), 25),
	HIGH_METABOLISM(NOT_THAT_KIND, "GOLDEN_CARROT", Arrays.asList(new CrashTrigger("exhaustion")), 200, Frame.GOAL),
	WILDLIFE_CONSERVATION(NOT_THAT_KIND, "COW_SPAWN_EGG", Arrays.asList(new CrashTrigger("chicken"), new CrashTrigger("strider", 0, 12, 0), new CrashTrigger("hoglin", 0, 12, 0), new CrashTrigger("cat", 0, 4, 0), new CrashTrigger("cow"), new CrashTrigger("fox", 0, 4, 0), new CrashTrigger("horse"), new CrashTrigger("mule"), new CrashTrigger("donkey"), new CrashTrigger("llama"), new CrashTrigger("mushroom_cow"), new CrashTrigger("ocelot", 0, 0, 3), new CrashTrigger("panda", 0, 4, 0), new CrashTrigger("bee", 0, 9, 0), new CrashTrigger("pig"), new CrashTrigger("rabbit"), new CrashTrigger("sheep"), new CrashTrigger("wolf"), new CrashTrigger("turtle")), 75),
	FOURTY_NINERS(BONEMEAL_PLUS, "GOLD_BLOCK", Arrays.asList(new CrashTrigger("goldblock", 81)), 20),
	THAT_FOOD_IS_FINE(AGRICULTURAL_REVOLUTION, "POISONOUS_POTATO", Arrays.asList(new CrashTrigger("food")), 25),
	MEAT_READY_TO_EAT(THAT_FOOD_IS_FINE, "COOKED_BEEF", Arrays.asList(new CrashTrigger("beef")), 50),
	CHICKEN_OR_THE_EGG(AGRICULTURAL_REVOLUTION, "EGG", Arrays.asList(new CrashTrigger("egg")), 25),
	EGGED_BY_MYSELF(CHICKEN_OR_THE_EGG, "EGG", Arrays.asList(new CrashTrigger("egg")), 40),
	THORGY(AGRICULTURAL_REVOLUTION, "WOLF_SPAWN_EGG", Arrays.asList(new CrashTrigger("wolf")), 50),
	FREE_PETS(THORGY, "CARROT_ON_A_STICK", Arrays.asList(new CrashTrigger("wolf"), new CrashTrigger("ocelot", 0, 0, 3), new CrashTrigger("cat", 0, 4, 0), new CrashTrigger("horse"), new CrashTrigger("donkey"), new CrashTrigger("mule"), new CrashTrigger("parrot"), new CrashTrigger("llama"), new CrashTrigger("bee", 0, 9, 0)), 500, Frame.CHALLENGE),
	YUMMY_REPAIRS(AGRICULTURAL_REVOLUTION, "ENCHANTED_BOOK", Arrays.asList(new CrashTrigger("repair")), 100, Frame.GOAL),
	HUNGRY_HIPPOS(YUMMY_REPAIRS, "DIAMOND_CHESTPLATE", Arrays.asList(new CrashTrigger("armor")), 900, Frame.CHALLENGE),
	THUMBS_UP(AGRICULTURAL_REVOLUTION, "STONE_HOE", Arrays.asList(new CrashTrigger("replant")), 50),
	REFORESTATION(THUMBS_UP, "OAK_SAPLING", Arrays.asList(new CrashTrigger("tree", 1000)), 1000, Frame.CHALLENGE),

	INDUSTRIAL_REVOLUTION("DIAMOND_PICKAXE", Arrays.asList(), 0),
	EASY_OUT(INDUSTRIAL_REVOLUTION, "CAMPFIRE", Arrays.asList(new CrashTrigger("campfire")), 20, Frame.TASK, 4),
	JUST_ADD_WATER(EASY_OUT, "WHITE_CONCRETE_POWDER", Arrays.asList(new CrashTrigger("concrete")), 20),
	REPAIRED(JUST_ADD_WATER, "STONE_BRICKS", Arrays.asList(new CrashTrigger("broken_bricks")), 25),
	FLAME_KEEPER(INDUSTRIAL_REVOLUTION, "FLINT_AND_STEEL", Arrays.asList(new CrashTrigger("flame")), 40),
	DETERMINED_CHEATER(FLAME_KEEPER, "OBSIDIAN", Arrays.asList(new CrashTrigger("cheater")), 100, Frame.GOAL),
	IRONT_YOU_GLAD(INDUSTRIAL_REVOLUTION, "IRON_INGOT", Arrays.asList(new CrashTrigger("iron")), 25),
	STAINLESS_STEEL(IRONT_YOU_GLAD, "IRON_PICKAXE", Arrays.asList(new CrashTrigger("iron_pickaxe")), 150, Frame.GOAL),
	NETHER_DULL(STAINLESS_STEEL, "NETHERITE_SWORD", Arrays.asList(new CrashTrigger("netherite_sword", 0, 12, 0)), 750, Frame.CHALLENGE, 12),
	FAST_AND_FURIOUS(IRONT_YOU_GLAD, "DIAMOND_PICKAXE", Arrays.asList(new CrashTrigger("diamond_pickaxe")), 40),
	CARPET_BOMBS(FAST_AND_FURIOUS, "TNT", Arrays.asList(new CrashTrigger("explosion", 100)), 200, Frame.GOAL),
	OVER_9000(FAST_AND_FURIOUS, "DIAMOND_PICKAXE", Arrays.asList(new CrashTrigger("stone", 9001)), 1000, Frame.CHALLENGE),
	HEY_IT_WORKS(INDUSTRIAL_REVOLUTION, "PURPLE_SHULKER_BOX", Arrays.asList(new CrashTrigger("shulker_box")), 75),
	NO_PANIC(HEY_IT_WORKS, "LAVA_BUCKET", Arrays.asList(new CrashTrigger("lava")), 100, Frame.GOAL),
	SCOURGE_OF_THE_FOREST(INDUSTRIAL_REVOLUTION, "IRON_AXE", Arrays.asList(new CrashTrigger("tree")), 20),
	DEFORESTATION(SCOURGE_OF_THE_FOREST, "BARRIER", Arrays.asList(new CrashTrigger("tree", 1000)), 500, Frame.CHALLENGE),

	SCIENTIFIC_REVOLUTION("ENCHANTED_BOOK", Arrays.asList(), 0),
	BREAKER_BREAKER(SCIENTIFIC_REVOLUTION, "TORCH", Arrays.asList(new CrashTrigger("torch")), 25),
	DID_YOU_REALLY_WAND_TO_DO_THAT(BREAKER_BREAKER, "CARROT_ON_A_STICK", Arrays.asList(new CrashTrigger("break")), 100, Frame.GOAL),
	SHARING_IS_CARING(SCIENTIFIC_REVOLUTION, "EXPERIENCE_BOTTLE", Arrays.asList(new CrashTrigger("player")), 10),
	LAAAGGGGGG(SCIENTIFIC_REVOLUTION, "FIREWORK_ROCKET", Arrays.asList(new CrashTrigger("lag")), 10),
	KEPT_ON_HAND(SCIENTIFIC_REVOLUTION, "ENCHANTED_BOOK", Arrays.asList(new CrashTrigger("soulbound")), 50),
	FEAR_THE_REAPER(SCIENTIFIC_REVOLUTION, "DIAMOND_HOE", Arrays.asList(new CrashTrigger("reaper")), 150, Frame.GOAL),
	READY_AFTER_DEATH(KEPT_ON_HAND, "DIAMOND_CHESTPLATE", Arrays.asList(new CrashTrigger("soulbound")), 400, Frame.GOAL),
	REAPED_THE_REAPER(FEAR_THE_REAPER, "DIAMOND_HOE", Arrays.asList(new CrashTrigger("reaper")), 1200, Frame.CHALLENGE),
	PLAGUE_INC(LAAAGGGGGG, "POISONOUS_POTATO", Arrays.asList(new CrashTrigger("contagion")), 15),
	EXTERMINATION(PLAGUE_INC, "ROTTEN_FLESH", Arrays.asList(new CrashTrigger("contagion")), 50),
	BROKEN_DREAMS(PLAGUE_INC, "CYAN_CONCRETE", Arrays.asList(new CrashTrigger("unstable")), 50),
	ENVIRONMENTAL_PROTECTION(SCIENTIFIC_REVOLUTION, "EXPERIENCE_BOTTLE", Arrays.asList(new CrashTrigger("experience", 1000)), 300, Frame.GOAL),
	BLIND_AS_A_BAT(SCIENTIFIC_REVOLUTION, "BAT_SPAWN_EGG", Arrays.asList(new CrashTrigger("blindness")), 30),
	LIGHT_AS_A_FEATHER(BLIND_AS_A_BAT, "DIAMOND_BOOTS", Arrays.asList(new CrashTrigger("boots")), 60),
	WORLD_RECORD(SCIENTIFIC_REVOLUTION, "SUNFLOWER", Arrays.asList(new CrashTrigger("record")), 400, Frame.GOAL),
	REPLENISHED(SCIENTIFIC_REVOLUTION, "GOLDEN_APPLE", Arrays.asList(new CrashTrigger("life")), 20),
	THE_SNOWMAN(SCIENTIFIC_REVOLUTION, "SNOW_BLOCK", Arrays.asList(new CrashTrigger("snowball")), 20),

	IMPERIAL_REVOLUTION("DIAMOND_SWORD", Arrays.asList(), 0),
	HEX_BAG(IMPERIAL_REVOLUTION, "WITCH_SPAWN_EGG", Arrays.asList(new CrashTrigger("player")), 25),
	SUPER_CHARGED(HEX_BAG, "DIAMOND_AXE", Arrays.asList(new CrashTrigger("lightning")), 35),
	DOUBLE_DAMAGE(SUPER_CHARGED, "DIAMOND_SWORD", Arrays.asList(new CrashTrigger("kills")), 100, Frame.GOAL),
	SEVEN_POINT_EIGHT(HEX_BAG, "WATER_BUCKET", Arrays.asList(new CrashTrigger("drowning")), 40),
	NOT_VERY_EFFECTIVE(IMPERIAL_REVOLUTION, "NAUTILUS_SHELL", Arrays.asList(new CrashTrigger("drowned")), 50),
	SUPER_EFFECTIVE(NOT_VERY_EFFECTIVE, "HEART_OF_THE_SEA", Arrays.asList(new CrashTrigger("boss")), 250, Frame.GOAL),
	PRE_COMBAT_UPDATE(NOT_VERY_EFFECTIVE, "IRON_SWORD", Arrays.asList(new CrashTrigger("combat_update")), 80),
	HEADHUNTER(IMPERIAL_REVOLUTION, "PLAYER_HEAD", Arrays.asList(new CrashTrigger("player_head")), 100, Frame.GOAL),
	DOUBLE_HEADER(HEADHUNTER, "WITHER_SKELETON_SKULL", Arrays.asList(new CrashTrigger("wither_skull")), 150, Frame.GOAL),
	SPOOKY_SCARY_SKELETON(HEADHUNTER, "SKELETON_SKULL", Arrays.asList(new CrashTrigger("skeleton_skull")), 100, Frame.GOAL),
	MOTHERLOAD(SUPER_EFFECTIVE, "DRAGON_EGG", Arrays.asList(new CrashTrigger("dragon")), 0, Frame.CHALLENGE),
	POSEIDONS_DAY_OFF(IMPERIAL_REVOLUTION, "PRISMARINE_SHARD", Arrays.asList(new CrashTrigger("day_off")), 50),
	POSEIDON_REBORN(POSEIDONS_DAY_OFF, "TRIDENT", Arrays.asList(new CrashTrigger("trident")), 200, Frame.CHALLENGE),
	CERBERUS(POSEIDON_REBORN, "SOUL_SAND", Arrays.asList(new CrashTrigger("obsidian")), 1000, Frame.CHALLENGE),
	MISSED(IMPERIAL_REVOLUTION, "SAND", Arrays.asList(new CrashTrigger("sand", 16)), 60),
	SAVING_GRACE(MISSED, "WOLF_SPAWN_EGG", Arrays.asList(new CrashTrigger("animal")), 100, Frame.CHALLENGE),
	LOOK_WHAT_YOU_MADE_ME_DO(IMPERIAL_REVOLUTION, "CROSSBOW", Arrays.asList(new CrashTrigger("pillage")), 20, Frame.TASK, 4),
	JUST_DIE_ALREADY(LOOK_WHAT_YOU_MADE_ME_DO, "PHANTOM_SPAWN_EGG", Arrays.asList(new CrashTrigger("phantom")), 75, Frame.TASK, 4),
	UNDERKILL(LOOK_WHAT_YOU_MADE_ME_DO, "DRAGON_EGG", Arrays.asList(new CrashTrigger("dragon")), 100, Frame.GOAL, 4),
	WHERE_DID_THAT_COME_FROM(UNDERKILL, "ARROW", Arrays.asList(new CrashTrigger("sniper")), 500, Frame.CHALLENGE),
	KILL_THE_MESSENGER(UNDERKILL, "FIRE_CHARGE", Arrays.asList(new CrashTrigger("ghast")), 200, Frame.GOAL),
	BLAST_OFF(IMPERIAL_REVOLUTION, "TNT", Arrays.asList(new CrashTrigger("creeper")), 30),
	PENETRATION(BLAST_OFF, "ARROW", Arrays.asList(new CrashTrigger("arrow")), 100, Frame.GOAL),
	KILIMANJARO(PENETRATION, "DIAMOND_SWORD", Arrays.asList(new CrashTrigger("kills")), 1000, Frame.CHALLENGE),
	KNOCKBACK_REVERSED(IMPERIAL_REVOLUTION, "SHIELD", Arrays.asList(new CrashTrigger("knockback")), 30),
	BEFORE_I_MELT_AWAY(IMPERIAL_REVOLUTION, "SNOWBALL", Arrays.asList(new CrashTrigger("blaze")), 200, Frame.GOAL),

	MECHANICAL_REVOLUTION("DIAMOND_CHESTPLATE", Arrays.asList(), 0),
	COFFEE_BREAK(MECHANICAL_REVOLUTION, "SUGAR", Arrays.asList(new CrashTrigger("coffee")), 20),
	TOO_HIGH(COFFEE_BREAK, "IRON_BOOTS", Arrays.asList(new CrashTrigger("fall_damage")), 50),
	EVENING_STROLL(COFFEE_BREAK, "IRON_LEGGINGS", Arrays.asList(new CrashTrigger("evening")), 50),
	SLACKIN(COFFEE_BREAK, "LEATHER_BOOTS", Arrays.asList(new CrashTrigger("truant")), 30),
	SPIDER_SENSES(COFFEE_BREAK, "SPIDER_EYE", Arrays.asList(new CrashTrigger("venom")), 30),
	I_AINT_AFRAID_OF_NO_GHOSTS(COFFEE_BREAK, "WHITE_BED", Arrays.asList(new CrashTrigger("unrest", 10)), 150, Frame.GOAL),
	THIS_GIRL_IS_ON_FIRE(MECHANICAL_REVOLUTION, "LAVA_BUCKET", Arrays.asList(new CrashTrigger("lava")), 25),
	MADE_FOR_WALKING(THIS_GIRL_IS_ON_FIRE, "DIAMOND_BOOTS", Arrays.asList(new CrashTrigger("boots")), 60),
	IM_YOU_BUT_SHORTER(MECHANICAL_REVOLUTION, "ENDER_PEARL", Arrays.asList(new CrashTrigger("enderpearl")), 30),
	PANZER_SOLDIER(IM_YOU_BUT_SHORTER, "OBSIDIAN", Arrays.asList(new CrashTrigger("tank")), 60),
	ARMORED_EVOLUTION(PANZER_SOLDIER, "IRON_CHESTPLATE", Arrays.asList(new CrashTrigger("armored")), 100, Frame.GOAL),
	GRAPHENE_ARMOR(PANZER_SOLDIER, "DIAMOND_CHESTPLATE", Arrays.asList(new CrashTrigger("toughness")), 200, Frame.GOAL),
	DANGER_DEFEATED(GRAPHENE_ARMOR, "NETHER_STAR", Arrays.asList(new CrashTrigger("wither")), 500, Frame.CHALLENGE),
	EXTRA_POWER(ARMORED_EVOLUTION, "ENCHANTED_GOLDEN_APPLE", Arrays.asList(new CrashTrigger("power")), 150, Frame.GOAL),
	DIVINE_RETRIBUTION(EXTRA_POWER, "TOTEM_OF_UNDYING", Arrays.asList(new CrashTrigger("retribution")), 1000, Frame.CHALLENGE),
	TOO_CLOSE(MECHANICAL_REVOLUTION, "ELYTRA", Arrays.asList(new CrashTrigger("failure")), 75),
	CRUISING_ALTITUDE(TOO_CLOSE, "ELYTRA", Arrays.asList(new CrashTrigger("elytra")), 150, Frame.GOAL),
	DEFLECTION(MECHANICAL_REVOLUTION, "SHIELD", Arrays.asList(new CrashTrigger("shield")), 100, Frame.GOAL),
	IRON_MAN(DEFLECTION, "IRON_BLOCK", Arrays.asList(new CrashTrigger("blocked")), 500, Frame.CHALLENGE),
	SIMPLE_REPAIR(MECHANICAL_REVOLUTION, "STICK", Arrays.asList(new CrashTrigger("repair")), 60),
	STICKY_BEES(SIMPLE_REPAIR, "STICK", Arrays.asList(new CrashTrigger("break", 8)), 400, Frame.GOAL);

	private ESAdvancement parent;
	private Material icon;
	private boolean isEnabled = false;
	private NamespacedKey namespace;
	private List<CrashTrigger> triggers = new ArrayList<CrashTrigger>();
	private int activatedVersion = 0;
	private Rewards rewards;
	private Frame frame;

	ESAdvancement(String icon, List<CrashTrigger> triggers, int exp) {
		this(null, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<CrashTrigger> triggers, int exp) {
		this(parent, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<CrashTrigger> triggers, int exp, Frame frame) {
		this(parent, icon, triggers, exp, frame, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<CrashTrigger> triggers, int exp, Frame frame,
	int activatedVersion) {
		namespace = new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/" + name().toLowerCase(Locale.ROOT));
		for(Material m: Material.values())
			if (m.name().equals(icon)) {
				this.icon = m;
				break;
			}
		if (this.icon == null) this.icon = Material.BARRIER;
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

	public List<CrashTrigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<CrashTrigger> triggers) {
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

	@Override
	public CrashAPIPlugin getPlugin() {
		return EnchantmentSolution.getPlugin();
	}
}
