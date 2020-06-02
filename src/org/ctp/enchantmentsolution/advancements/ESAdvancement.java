package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.Advancement.Frame;

public enum ESAdvancement {

	AGRICULTURAL_REVOLUTION("DIAMOND_HOE", Arrays.asList(), 0),
	FISH_STICKS(AGRICULTURAL_REVOLUTION, "COOKED_COD", Arrays.asList(new ESTrigger("cooked")), 10),
	FED_FOR_A_LIFETIME(FISH_STICKS, "COD", Arrays.asList(new ESTrigger("cod"), new ESTrigger("salmon"), new ESTrigger("tropical_fish"), new ESTrigger("pufferfish")), 15),
	FISHY_BUSINESS(FED_FOR_A_LIFETIME, "COD", Arrays.asList(new ESTrigger("cod"), new ESTrigger("salmon"), new ESTrigger("tropical_fish"), new ESTrigger("pufferfish")), 75),
	NEMO_ENIM_COQUIT(FISH_STICKS, "TROPICAL_FISH", Arrays.asList(new ESTrigger("tropical_fish")), 25),
	BONEMEAL_PLUS(AGRICULTURAL_REVOLUTION, "BONE_MEAL", Arrays.asList(new ESTrigger("bonemeal")), 10),
	JUST_AS_SWEET(BONEMEAL_PLUS, "WITHER_ROSE", Arrays.asList(new ESTrigger("wither_rose")), 100, Frame.GOAL, 4),
	NOT_THAT_KIND(AGRICULTURAL_REVOLUTION, "DIAMOND_SWORD", Arrays.asList(new ESTrigger("chicken"), new ESTrigger("strider", 0, 12, 0), new ESTrigger("hoglin", 0, 12, 0), new ESTrigger("cat", 0, 4, 0), new ESTrigger("cow"), new ESTrigger("fox", 0, 4, 0), new ESTrigger("horse"), new ESTrigger("mule"), new ESTrigger("donkey"), new ESTrigger("llama"), new ESTrigger("mushroom_cow"), new ESTrigger("ocelot", 0, 0, 3), new ESTrigger("panda", 0, 4, 0), new ESTrigger("bee", 0, 9, 0), new ESTrigger("pig"), new ESTrigger("rabbit"), new ESTrigger("sheep"), new ESTrigger("wolf"), new ESTrigger("turtle")), 25),
	HIGH_METABOLISM(NOT_THAT_KIND, "GOLDEN_CARROT", Arrays.asList(new ESTrigger("exhaustion")), 200, Frame.GOAL),
	WILDLIFE_CONSERVATION(NOT_THAT_KIND, "COW_SPAWN_EGG", Arrays.asList(new ESTrigger("chicken"), new ESTrigger("strider", 0, 12, 0), new ESTrigger("hoglin", 0, 12, 0), new ESTrigger("cat", 0, 4, 0), new ESTrigger("cow"), new ESTrigger("fox", 0, 4, 0), new ESTrigger("horse"), new ESTrigger("mule"), new ESTrigger("donkey"), new ESTrigger("llama"), new ESTrigger("mushroom_cow"), new ESTrigger("ocelot", 0, 0, 3), new ESTrigger("panda", 0, 4, 0), new ESTrigger("bee", 0, 9, 0), new ESTrigger("pig"), new ESTrigger("rabbit"), new ESTrigger("sheep"), new ESTrigger("wolf"), new ESTrigger("turtle")), 75),
	FOURTY_NINERS(BONEMEAL_PLUS, "GOLD_BLOCK", Arrays.asList(new ESTrigger("goldblock", 81)), 20),
	THAT_FOOD_IS_FINE(AGRICULTURAL_REVOLUTION, "POISONOUS_POTATO", Arrays.asList(new ESTrigger("food")), 25),
	MEAT_READY_TO_EAT(THAT_FOOD_IS_FINE, "COOKED_BEEF", Arrays.asList(new ESTrigger("beef")), 50),
	CHICKEN_OR_THE_EGG(AGRICULTURAL_REVOLUTION, "EGG", Arrays.asList(new ESTrigger("egg")), 25),
	EGGED_BY_MYSELF(CHICKEN_OR_THE_EGG, "EGG", Arrays.asList(new ESTrigger("egg")), 40),
	THORGY(AGRICULTURAL_REVOLUTION, "WOLF_SPAWN_EGG", Arrays.asList(new ESTrigger("wolf")), 50),
	FREE_PETS(THORGY, "CARROT_ON_A_STICK", Arrays.asList(new ESTrigger("wolf"), new ESTrigger("ocelot", 0, 0, 3), new ESTrigger("cat", 0, 4, 0), new ESTrigger("horse"), new ESTrigger("donkey"), new ESTrigger("mule"), new ESTrigger("parrot"), new ESTrigger("llama"), new ESTrigger("bee", 0, 9, 0)), 500, Frame.CHALLENGE),
	YUMMY_REPAIRS(AGRICULTURAL_REVOLUTION, "ENCHANTED_BOOK", Arrays.asList(new ESTrigger("repair")), 100, Frame.GOAL),
	HUNGRY_HIPPOS(YUMMY_REPAIRS, "DIAMOND_CHESTPLATE", Arrays.asList(new ESTrigger("armor")), 900, Frame.CHALLENGE),

	INDUSTRIAL_REVOLUTION("DIAMOND_PICKAXE", Arrays.asList(), 0),
	EASY_OUT(INDUSTRIAL_REVOLUTION, "CAMPFIRE", Arrays.asList(new ESTrigger("campfire")), 20, Frame.TASK, 4),
	JUST_ADD_WATER(EASY_OUT, "WHITE_CONCRETE_POWDER", Arrays.asList(new ESTrigger("concrete")), 20),
	REPAIRED(JUST_ADD_WATER, "STONE_BRICKS", Arrays.asList(new ESTrigger("broken_bricks")), 25),
	FLAME_KEEPER(INDUSTRIAL_REVOLUTION, "FLINT_AND_STEEL", Arrays.asList(new ESTrigger("flame")), 40),
	DETERMINED_CHEATER(FLAME_KEEPER, "OBSIDIAN", Arrays.asList(new ESTrigger("cheater")), 100, Frame.GOAL),
	IRONT_YOU_GLAD(INDUSTRIAL_REVOLUTION, "IRON_INGOT", Arrays.asList(new ESTrigger("iron")), 25),
	STAINLESS_STEEL(IRONT_YOU_GLAD, "IRON_PICKAXE", Arrays.asList(new ESTrigger("iron_pickaxe")), 150, Frame.GOAL),
	NETHER_DULL(STAINLESS_STEEL, "NETHERITE_SWORD", Arrays.asList(new ESTrigger("netherite_sword", 0, 12, 0)), 750, Frame.CHALLENGE, 12),
	FAST_AND_FURIOUS(IRONT_YOU_GLAD, "DIAMOND_PICKAXE", Arrays.asList(new ESTrigger("diamond_pickaxe")), 40),
	CARPET_BOMBS(FAST_AND_FURIOUS, "TNT", Arrays.asList(new ESTrigger("explosion", 100)), 200, Frame.GOAL),
	OVER_9000(FAST_AND_FURIOUS, "DIAMOND_PICKAXE", Arrays.asList(new ESTrigger("stone", 9001)), 1000, Frame.CHALLENGE),
	HEY_IT_WORKS(INDUSTRIAL_REVOLUTION, "PURPLE_SHULKER_BOX", Arrays.asList(new ESTrigger("shulker_box")), 75),
	NO_PANIC(HEY_IT_WORKS, "LAVA_BUCKET", Arrays.asList(new ESTrigger("lava")), 100, Frame.GOAL),

	SCIENTIFIC_REVOLUTION("ENCHANTED_BOOK", Arrays.asList(), 0),
	BREAKER_BREAKER(SCIENTIFIC_REVOLUTION, "TORCH", Arrays.asList(new ESTrigger("torch")), 25),
	DID_YOU_REALLY_WAND_TO_DO_THAT(BREAKER_BREAKER, "CARROT_ON_A_STICK", Arrays.asList(new ESTrigger("break")), 100, Frame.GOAL),
	SHARING_IS_CARING(SCIENTIFIC_REVOLUTION, "EXPERIENCE_BOTTLE", Arrays.asList(new ESTrigger("player")), 10),
	LAAAGGGGGG(SCIENTIFIC_REVOLUTION, "FIREWORK_ROCKET", Arrays.asList(new ESTrigger("lag")), 10),
	KEPT_ON_HAND(SCIENTIFIC_REVOLUTION, "ENCHANTED_BOOK", Arrays.asList(new ESTrigger("soulbound")), 50),
	FEAR_THE_REAPER(SCIENTIFIC_REVOLUTION, "DIAMOND_HOE", Arrays.asList(new ESTrigger("reaper")), 150, Frame.GOAL),
	READY_AFTER_DEATH(KEPT_ON_HAND, "DIAMOND_CHESTPLATE", Arrays.asList(new ESTrigger("soulbound")), 400, Frame.GOAL),
	REAPED_THE_REAPER(FEAR_THE_REAPER, "DIAMOND_HOE", Arrays.asList(new ESTrigger("reaper")), 1200, Frame.CHALLENGE),
	PLAGUE_INC(LAAAGGGGGG, "POISONOUS_POTATO", Arrays.asList(new ESTrigger("contagion")), 15),
	EXTERMINATION(PLAGUE_INC, "ROTTEN_FLESH", Arrays.asList(new ESTrigger("contagion")), 50),
	ENVIRONMENTAL_PROTECTION(SCIENTIFIC_REVOLUTION, "EXPERIENCE_BOTTLE", Arrays.asList(new ESTrigger("experience", 1000)), 300, Frame.GOAL),
	LIGHT_AS_A_FEATHER(SCIENTIFIC_REVOLUTION, "DIAMOND_BOOTS", Arrays.asList(new ESTrigger("boots")), 60),

	IMPERIAL_REVOLUTION("DIAMOND_SWORD", Arrays.asList(), 0),
	HEX_BAG(IMPERIAL_REVOLUTION, "WITCH_SPAWN_EGG", Arrays.asList(new ESTrigger("player")), 25),
	SUPER_CHARGED(HEX_BAG, "DIAMOND_AXE", Arrays.asList(new ESTrigger("lightning")), 35),
	SEVEN_POINT_EIGHT(HEX_BAG, "WATER_BUCKET", Arrays.asList(new ESTrigger("drowning")), 40),
	NOT_VERY_EFFECTIVE(IMPERIAL_REVOLUTION, "NAUTILUS_SHELL", Arrays.asList(new ESTrigger("drowned")), 50),
	SUPER_EFFECTIVE(NOT_VERY_EFFECTIVE, "HEART_OF_THE_SEA", Arrays.asList(new ESTrigger("boss")), 250, Frame.GOAL),
	PRE_COMBAT_UPDATE(NOT_VERY_EFFECTIVE, "IRON_SWORD", Arrays.asList(new ESTrigger("combat_update")), 80),
	HEADHUNTER(IMPERIAL_REVOLUTION, "PLAYER_HEAD", Arrays.asList(new ESTrigger("player_head")), 100, Frame.GOAL),
	DOUBLE_HEADER(HEADHUNTER, "WITHER_SKELETON_SKULL", Arrays.asList(new ESTrigger("wither_skull")), 150, Frame.GOAL),
	MOTHERLOAD(SUPER_EFFECTIVE, "DRAGON_EGG", Arrays.asList(new ESTrigger("dragon")), 0, Frame.CHALLENGE),
	POSEIDONS_DAY_OFF(IMPERIAL_REVOLUTION, "PRISMARINE_SHARD", Arrays.asList(new ESTrigger("day_off")), 50),
	POSEIDON_REBORN(POSEIDONS_DAY_OFF, "TRIDENT", Arrays.asList(new ESTrigger("trident")), 200, Frame.CHALLENGE),
	CERBERUS(POSEIDON_REBORN, "SOUL_SAND", Arrays.asList(new ESTrigger("obsidian")), 1000, Frame.CHALLENGE),
	MISSED(IMPERIAL_REVOLUTION, "SAND", Arrays.asList(new ESTrigger("sand", 16)), 60),
	LOOK_WHAT_YOU_MADE_ME_DO(IMPERIAL_REVOLUTION, "CROSSBOW", Arrays.asList(new ESTrigger("pillage")), 20, Frame.TASK, 4),
	JUST_DIE_ALREADY(LOOK_WHAT_YOU_MADE_ME_DO, "PHANTOM_SPAWN_EGG", Arrays.asList(new ESTrigger("phantom")), 75, Frame.TASK, 4),
	UNDERKILL(LOOK_WHAT_YOU_MADE_ME_DO, "DRAGON_EGG", Arrays.asList(new ESTrigger("dragon")), 100, Frame.GOAL, 4),
	WHERE_DID_THAT_COME_FROM(UNDERKILL, "ARROW", Arrays.asList(new ESTrigger("sniper")), 500, Frame.CHALLENGE),
	BLAST_OFF(IMPERIAL_REVOLUTION, "TNT", Arrays.asList(new ESTrigger("creeper")), 30),
	PENETRATION(BLAST_OFF, "ARROW", Arrays.asList(new ESTrigger("arrow")), 100, Frame.GOAL),
	KILIMANJARO(PENETRATION, "DIAMOND_SWORD", Arrays.asList(new ESTrigger("kills")), 1000, Frame.CHALLENGE),
	KNOCKBACK_REVERSED(IMPERIAL_REVOLUTION, "SHIELD", Arrays.asList(new ESTrigger("knockback")), 30),

	MECHANICAL_REVOLUTION("DIAMOND_CHESTPLATE", Arrays.asList(), 0),
	COFFEE_BREAK(MECHANICAL_REVOLUTION, "SUGAR", Arrays.asList(new ESTrigger("coffee")), 20),
	I_AINT_AFRAID_OF_NO_GHOSTS(COFFEE_BREAK, "WHITE_BED", Arrays.asList(new ESTrigger("unrest", 10)), 150, Frame.GOAL),
	THIS_GIRL_IS_ON_FIRE(MECHANICAL_REVOLUTION, "LAVA_BUCKET", Arrays.asList(new ESTrigger("lava")), 25),
	MADE_FOR_WALKING(THIS_GIRL_IS_ON_FIRE, "DIAMOND_BOOTS", Arrays.asList(new ESTrigger("boots")), 60),
	IM_YOU_BUT_SHORTER(MECHANICAL_REVOLUTION, "ENDER_PEARL", Arrays.asList(new ESTrigger("enderpearl")), 30),
	PANZER_SOLDIER(IM_YOU_BUT_SHORTER, "OBSIDIAN", Arrays.asList(new ESTrigger("tank")), 60),
	ARMORED_EVOLUTION(PANZER_SOLDIER, "IRON_CHESTPLATE", Arrays.asList(new ESTrigger("armored")), 100, Frame.GOAL),
	GRAPHENE_ARMOR(PANZER_SOLDIER, "DIAMOND_CHESTPLATE", Arrays.asList(new ESTrigger("toughness")), 200, Frame.GOAL),
	DANGER_DEFEATED(GRAPHENE_ARMOR, "NETHER_STAR", Arrays.asList(new ESTrigger("wither")), 500, Frame.CHALLENGE),
	EXTRA_POWER(ARMORED_EVOLUTION, "ENCHANTED_GOLDEN_APPLE", Arrays.asList(new ESTrigger("power")), 150, Frame.GOAL),
	DIVINE_RETRIBUTION(EXTRA_POWER, "TOTEM_OF_UNDYING", Arrays.asList(new ESTrigger("retribution")), 1000, Frame.CHALLENGE),
	TOO_CLOSE(MECHANICAL_REVOLUTION, "ELYTRA", Arrays.asList(new ESTrigger("failure")), 75),
	CRUISING_ALTITUDE(TOO_CLOSE, "ELYTRA", Arrays.asList(new ESTrigger("elytra")), 150, Frame.GOAL),
	DEFLECTION(MECHANICAL_REVOLUTION, "SHIELD", Arrays.asList(new ESTrigger("shield")), 100, Frame.GOAL),
	IRON_MAN(DEFLECTION, "IRON_BLOCK", Arrays.asList(new ESTrigger("blocked")), 500, Frame.CHALLENGE),
	SIMPLE_REPAIR(MECHANICAL_REVOLUTION, "STICK", Arrays.asList(new ESTrigger("repair")), 60),
	STICKY_BEES(SIMPLE_REPAIR, "STICK", Arrays.asList(new ESTrigger("break", 8)), 400, Frame.GOAL);

	private ESAdvancement parent;
	private Material icon;
	private boolean isEnabled = false;
	private NamespacedKey namespace;
	private List<ESTrigger> triggers = new ArrayList<ESTrigger>();
	private int activatedVersion = 0;
	private Rewards rewards;
	private Frame frame;

	ESAdvancement(String icon, List<ESTrigger> triggers, int exp) {
		this(null, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<ESTrigger> triggers, int exp) {
		this(parent, icon, triggers, exp, Frame.TASK, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<ESTrigger> triggers, int exp, Frame frame) {
		this(parent, icon, triggers, exp, frame, 0);
	}

	ESAdvancement(ESAdvancement parent, String icon, List<ESTrigger> triggers, int exp, Frame frame,
	int activatedVersion) {
		namespace = new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/" + name().toLowerCase());
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
