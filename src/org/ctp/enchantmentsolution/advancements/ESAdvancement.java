package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.Advancement.Frame;
import org.ctp.enchantmentsolution.api.Rewards;
import org.ctp.enchantmentsolution.api.trigger.ImpossibleTrigger;

public enum ESAdvancement {

	ENCHANTMENT_SOLUTION(new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/enchantment_solution"), 
			"ENCHANTED_BOOK", Arrays.asList(), new Rewards(), Frame.TASK),
	FISH_STICKS(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fish_sticks"), "COOKED_COD",
			Arrays.asList(new ESTrigger("cooked", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	FED_FOR_A_LIFETIME(FISH_STICKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fed_for_a_lifetime"), "COD",
			Arrays.asList(new ESTrigger("cod", new ImpossibleTrigger()), new ESTrigger("salmon", new ImpossibleTrigger()), 
			new ESTrigger("tropical_fish", new ImpossibleTrigger()), new ESTrigger("pufferfish", new ImpossibleTrigger())),
			new Rewards().setExperience(15), Frame.TASK),
	NEMO_ENIM_COQUIT(FISH_STICKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/nemo_enim_coquit"), "TROPICAL_FISH",
			Arrays.asList(new ESTrigger("tropical_fish", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	BREAKER_BREAKER(NEMO_ENIM_COQUIT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/breaker_breaker"),  "TORCH",
			Arrays.asList(new ESTrigger("torch", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	THORGY(BREAKER_BREAKER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/thorgy"),  "WOLF_SPAWN_EGG",
			Arrays.asList(new ESTrigger("wolf", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	DID_YOU_REALLY_WAND_TO_DO_THAT(THORGY, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/did_you_really_wand_to_do_that"), 
			"CARROT_ON_A_STICK", Arrays.asList(new ESTrigger("break", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	FREE_PETS(DID_YOU_REALLY_WAND_TO_DO_THAT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/free_pets"), "CARROT_ON_A_STICK",
			Arrays.asList(new ESTrigger("wolf", new ImpossibleTrigger()), new ESTrigger("ocelot", new ImpossibleTrigger(), 0, 0, 3), 
			new ESTrigger("cat", new ImpossibleTrigger(), 0, 4, 0), new ESTrigger("horse", new ImpossibleTrigger()), new ESTrigger("donkey", new ImpossibleTrigger()), 
			new ESTrigger("mule", new ImpossibleTrigger()), new ESTrigger("parrot", new ImpossibleTrigger()), 
			new ESTrigger("llama", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	SHARING_IS_CARING(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/sharing_is_caring"), "EXPERIENCE_BOTTLE",
			Arrays.asList(new ESTrigger("player", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	NOT_THAT_KIND(SHARING_IS_CARING, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/not_that_kind"), "DIAMOND_SWORD",
			Arrays.asList(new ESTrigger("chicken", new ImpossibleTrigger()), new ESTrigger("cat", new ImpossibleTrigger(), 0, 4, 0), 
			new ESTrigger("cow", new ImpossibleTrigger()), new ESTrigger("fox", new ImpossibleTrigger(), 0, 4, 0), new ESTrigger("horse", new ImpossibleTrigger()),
			new ESTrigger("llama", new ImpossibleTrigger()), new ESTrigger("mushroom_cow", new ImpossibleTrigger()),
			new ESTrigger("ocelot", new ImpossibleTrigger(), 0, 0, 3), new ESTrigger("panda", new ImpossibleTrigger(), 0, 4, 0), 
			new ESTrigger("pig", new ImpossibleTrigger()), new ESTrigger("rabbit", new ImpossibleTrigger()), new ESTrigger("sheep", new ImpossibleTrigger()), 
			new ESTrigger("wolf", new ImpossibleTrigger()), new ESTrigger("turtle", new ImpossibleTrigger())),
			new Rewards().setExperience(25), Frame.TASK),
	SUPER_CHARGED(NOT_THAT_KIND, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/super_charged"), "DIAMOND_AXE",
			Arrays.asList(new ESTrigger("lightning", new ImpossibleTrigger())), new Rewards().setExperience(35), Frame.TASK),
	NOT_VERY_EFFECTIVE(SUPER_CHARGED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/not_very_effective"), "NAUTILUS_SHELL",
			Arrays.asList(new ESTrigger("drowned", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	PRE_COMBAT_UPDATE(NOT_VERY_EFFECTIVE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/pre_combat_update"), "IRON_SWORD",
			Arrays.asList(new ESTrigger("combat_update", new ImpossibleTrigger())), new Rewards().setExperience(80), Frame.TASK),
	HEADHUNTER(PRE_COMBAT_UPDATE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/headhunter"), "PLAYER_HEAD",
			Arrays.asList(new ESTrigger("player_head", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	DOUBLE_HEADER(HEADHUNTER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/double_header"), "WITHER_SKELETON_SKULL",
			Arrays.asList(new ESTrigger("wither_skull", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	SUPER_EFFECTIVE(DOUBLE_HEADER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/super_effective"), "HEART_OF_THE_SEA",
			Arrays.asList(new ESTrigger("boss", new ImpossibleTrigger())), new Rewards().setExperience(250), Frame.GOAL),
	MOTHERLOAD(SUPER_EFFECTIVE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/motherload"), "DRAGON_EGG",
			Arrays.asList(new ESTrigger("dragon", new ImpossibleTrigger())), new Rewards(), Frame.CHALLENGE),
	HEX_BAG(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/hex_bag"), "WITCH_SPAWN_EGG",
			Arrays.asList(new ESTrigger("player", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	SEVEN_POINT_EIGHT(HEX_BAG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/seven_point_eight"), "WATER_BUCKET",
			Arrays.asList(new ESTrigger("drowning", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	POSEIDONS_DAY_OFF(SEVEN_POINT_EIGHT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/poseidons_day_off"), 
			"PRISMARINE_SHARD", Arrays.asList(new ESTrigger("day_off", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	FISHIER_BUSINESS(POSEIDONS_DAY_OFF, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fishy_business"), "COD",
			Arrays.asList(new ESTrigger("cod", new ImpossibleTrigger()), new ESTrigger("salmon", new ImpossibleTrigger()), 
			new ESTrigger("tropical_fish", new ImpossibleTrigger()), new ESTrigger("pufferfish", new ImpossibleTrigger())), 
			new Rewards().setExperience(75), Frame.TASK),
	POSEIDON_REBORN(FISHIER_BUSINESS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/poseidon_reborn"), "TRIDENT",
			Arrays.asList(new ESTrigger("trident", new ImpossibleTrigger())), new Rewards().setExperience(200), Frame.CHALLENGE),
	CERBERUS(POSEIDON_REBORN, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/cerberus"), "SOUL_SAND",
			Arrays.asList(new ESTrigger("obsidian", new ImpossibleTrigger())), new Rewards().setExperience(1000), Frame.CHALLENGE),
	BONEMEAL_PLUS(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/bonemeal_plus"), "BONE_MEAL",
			Arrays.asList(new ESTrigger("bonemeal", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	FOURTY_NINERS(BONEMEAL_PLUS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fourty_niners"), "GOLD_BLOCK",
			Arrays.asList(new ESTrigger("goldblock", new ImpossibleTrigger(), 81)), new Rewards().setExperience(20), Frame.TASK),
	CHICKEN_OR_THE_EGG(FOURTY_NINERS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/chicken_or_the_egg"), "EGG",
			Arrays.asList(new ESTrigger("egg", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	EGGED_BY_MYSELF(CHICKEN_OR_THE_EGG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/egged_by_myself"), "EGG",
			Arrays.asList(new ESTrigger("egg", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	MISSED(EGGED_BY_MYSELF, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/missed"), "SAND",
			Arrays.asList(new ESTrigger("sand", new ImpossibleTrigger(), 16)), new Rewards().setExperience(60), Frame.TASK),
	JUST_AS_SWEET(MISSED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_as_sweet"), "WITHER_ROSE",
			Arrays.asList(new ESTrigger("wither_rose", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL, 4),
	LAAAGGGGGG(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/laaagggggg"), "FIREWORK_ROCKET",
			Arrays.asList(new ESTrigger("lag", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	EASY_OUT(LAAAGGGGGG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/easy_out"), "CAMPFIRE",
			Arrays.asList(new ESTrigger("campfire", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK, 4),
	JUST_ADD_WATER(EASY_OUT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_add_water"), "WHITE_CONCRETE_POWDER",
			Arrays.asList(new ESTrigger("concrete", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK),
	REPAIRED(JUST_ADD_WATER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/repaired"), "STONE_BRICKS",
			Arrays.asList(new ESTrigger("broken_bricks", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	KEPT_ON_HAND(REPAIRED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/kept_on_hand"), "ENCHANTED_BOOK",
			Arrays.asList(new ESTrigger("soulbound", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	FEAR_THE_REAPER(KEPT_ON_HAND, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fear_the_reaper"), "DIAMOND_HOE",
			Arrays.asList(new ESTrigger("reaper", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	HIGH_METABOLISM(FEAR_THE_REAPER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/high_metabolism"), 
			"GOLDEN_CARROT", Arrays.asList(new ESTrigger("exhaustion", new ImpossibleTrigger())), new Rewards().setExperience(200), Frame.GOAL),
	READY_AFTER_DEATH(HIGH_METABOLISM, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/ready_after_death"),  
			"DIAMOND_CHESTPLATE", Arrays.asList(new ESTrigger("soulbound", new ImpossibleTrigger())), new Rewards().setExperience(400), Frame.GOAL),
	REAPED_THE_REAPER(READY_AFTER_DEATH, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/reaped_the_reaper"), "DIAMOND_HOE",
			Arrays.asList(new ESTrigger("reaper", new ImpossibleTrigger())), new Rewards().setExperience(1200), Frame.CHALLENGE),
	COFFEE_BREAK(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/coffee_break"), "SUGAR",
			Arrays.asList(new ESTrigger("coffee", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK),
	THIS_GIRL_IS_ON_FIRE(COFFEE_BREAK, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/this_girl_is_on_fire"), "LAVA_BUCKET",
			Arrays.asList(new ESTrigger("lava", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	FLAME_KEEPER(THIS_GIRL_IS_ON_FIRE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/flame_keeper"), "FLINT_AND_STEEL",
			Arrays.asList(new ESTrigger("flame", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	MADE_FOR_WALKING(FLAME_KEEPER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/made_for_walking"), 
			"DIAMOND_BOOTS", Arrays.asList(new ESTrigger("boots", new ImpossibleTrigger())), new Rewards().setExperience(60), Frame.TASK),
	DETERMINED_CHEATER(MADE_FOR_WALKING, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/determined_cheater"), "OBSIDIAN",
			Arrays.asList(new ESTrigger("cheater", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	I_AINT_AFRAID_OF_NO_GHOSTS(DETERMINED_CHEATER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/i_aint_afraid_of_no_ghosts"), "WHITE_BED",
			Arrays.asList(new ESTrigger("unrest", new ImpossibleTrigger(), 10)), new Rewards().setExperience(150), Frame.GOAL),
	IM_YOU_BUT_SHORTER(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/im_you_but_shorter"), "ENDER_PEARL",
			Arrays.asList(new ESTrigger("enderpearl", new ImpossibleTrigger())), new Rewards().setExperience(30), Frame.TASK),
	PANZER_SOLDIER(IM_YOU_BUT_SHORTER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/panzer_soldier"), "OBSIDIAN",
			Arrays.asList(new ESTrigger("tank", new ImpossibleTrigger())), new Rewards().setExperience(60), Frame.TASK),
	TOO_CLOSE(PANZER_SOLDIER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/too_close"), "ELYTRA",
			Arrays.asList(new ESTrigger("failure", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK),
	ARMORED_EVOLUTION(TOO_CLOSE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/armored_evolution"), 
			"IRON_CHESTPLATE", Arrays.asList(new ESTrigger("armored", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	EXTRA_POWER(ARMORED_EVOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/extra_power"), 
			"ENCHANTED_GOLDEN_APPLE", Arrays.asList(new ESTrigger("power", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	CRUISING_ALTITUDE(EXTRA_POWER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/cruising_altitude"), "ELYTRA",
			Arrays.asList(new ESTrigger("elytra", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	GRAPHENE_ARMOR(CRUISING_ALTITUDE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/graphene_armor"), "DIAMOND_CHESTPLATE",
			Arrays.asList(new ESTrigger("toughness", new ImpossibleTrigger())), new Rewards().setExperience(200), Frame.GOAL),
	DANGER_DEFEATED(GRAPHENE_ARMOR, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/danger_defeated"), "NETHER_STAR",
			Arrays.asList(new ESTrigger("wither", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	DIVINE_RETRIBUTION(DANGER_DEFEATED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/divine_retribution"), 
			"TOTEM_OF_UNDYING", Arrays.asList(new ESTrigger("retribution", new ImpossibleTrigger())), new Rewards().setExperience(1000), Frame.CHALLENGE),
	LOOK_WHAT_YOU_MADE_ME_DO(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/look_what_you_made_me_do"), "CROSSBOW",
			Arrays.asList(new ESTrigger("pillage", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK, 4),
	JUST_DIE_ALREADY(LOOK_WHAT_YOU_MADE_ME_DO, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_die_already"), "PHANTOM_SPAWN_EGG",
			Arrays.asList(new ESTrigger("phantom", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK, 4),
	UNDERKILL(JUST_DIE_ALREADY, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/underkill"), "DRAGON_EGG",
			Arrays.asList(new ESTrigger("dragon", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL, 4),
	WHERE_DID_THAT_COME_FROM(UNDERKILL, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/where_did_that_come_from"), "ARROW",
			Arrays.asList(new ESTrigger("sniper", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	THAT_FOOD_IS_FINE(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/that_food_is_fine"), 
			"POISONOUS_POTATO", Arrays.asList(new ESTrigger("food", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	DEFLECTION(THAT_FOOD_IS_FINE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/deflection"), "SHIELD",
			Arrays.asList(new ESTrigger("shield", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	IRON_MAN(DEFLECTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/iron_man"), "IRON_BLOCK",
			Arrays.asList(new ESTrigger("blocked", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	IRONT_YOU_GLAD(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/iront_you_glad"), 
			"IRON_INGOT", Arrays.asList(new ESTrigger("iron", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	FAST_AND_FURIOUS(IRONT_YOU_GLAD, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fast_and_furious"), "DIAMOND_PICKAXE",
			Arrays.asList(new ESTrigger("diamond_pickaxe", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	HEY_IT_WORKS(FAST_AND_FURIOUS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/hey_it_works"), "PURPLE_SHULKER_BOX",
			Arrays.asList(new ESTrigger("shulker_box", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK),
	NO_PANIC(HEY_IT_WORKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/no_panic"), "LAVA_BUCKET",
			Arrays.asList(new ESTrigger("lava", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	OVER_9000(NO_PANIC, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/over_9000"), "DIAMOND_PICKAXE",
			Arrays.asList(new ESTrigger("stone", new ImpossibleTrigger(), 9001)), new Rewards().setExperience(1000), Frame.CHALLENGE),
	;
	private ESAdvancement parent;
	private Material icon;
	private boolean isEnabled = false;
	private NamespacedKey namespace;
	private List<ESTrigger> triggers = new ArrayList<ESTrigger>();
	private int activatedVersion = 0;
	private Rewards rewards;
	private Frame frame;
	
	ESAdvancement(NamespacedKey key, String icon, List<ESTrigger> triggers, Rewards rewards, Frame frame){
		this.namespace = key;
		for(Material m : Material.values()) {
			if(m.name().equals(icon)) {
				this.icon = m;
				break;
			}
		}
		if(this.icon == null) {
			this.icon = Material.BARRIER;
		}
		this.triggers = triggers;
		this.rewards = rewards;
		this.frame = frame;
	}
	
	ESAdvancement(ESAdvancement parent, NamespacedKey key, String icon, List<ESTrigger> triggers, 
			Rewards rewards, Frame frame){
		this.namespace = key;
		for(Material m : Material.values()) {
			if(m.name().equals(icon)) {
				this.icon = m;
				break;
			}
		}
		if(this.icon == null) {
			this.icon = Material.BARRIER;
		}
		this.triggers = triggers;
		this.rewards = rewards;
		this.frame = frame;
		this.parent = parent;
	}
	
	ESAdvancement(ESAdvancement parent, NamespacedKey key, String icon, List<ESTrigger> triggers, 
			Rewards rewards, Frame frame, int activatedVersion){
		this.namespace = key;
		for(Material m : Material.values()) {
			if(m.name().equals(icon)) {
				this.icon = m;
				break;
			}
		}
		if(this.icon == null) {
			this.icon = Material.BARRIER;
		}
		this.triggers = triggers;
		this.activatedVersion = activatedVersion;
		this.rewards = rewards;
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
