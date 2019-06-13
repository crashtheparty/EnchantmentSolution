package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.Advancement.Frame;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.api.Rewards;
import org.ctp.enchantmentsolution.api.trigger.ImpossibleTrigger;

public enum ESAdvancement {

	ENCHANTMENT_SOLUTION(new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/enchantment_solution"), 
			Arrays.asList(new ESLocalization(Language.US, "Enchantment Solution", "Advancements surrounding the added enchantments")), 
			"ENCHANTED_BOOK", Arrays.asList(), new Rewards(), Frame.TASK),
	FISH_STICKS(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fish_sticks"), 
			Arrays.asList(new ESLocalization(Language.US, "Fish Sticks", "Cook a fish with Fried")), "COOKED_COD",
			Arrays.asList(new ESTrigger("cooked", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	FED_FOR_A_LIFETIME(FISH_STICKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fed_for_a_lifetime"), 
			Arrays.asList(new ESLocalization(Language.US, "Fed For a Lifetime", "Get more than one of each kind of fish with Angler")), "COD",
			Arrays.asList(new ESTrigger("cod", new ImpossibleTrigger()), new ESTrigger("salmon", new ImpossibleTrigger()), 
			new ESTrigger("tropical_fish", new ImpossibleTrigger()), new ESTrigger("pufferfish", new ImpossibleTrigger())),
			new Rewards().setExperience(15), Frame.TASK),
	NEMO_ENIM_COQUIT(FISH_STICKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/nemo_enim_coquit"), 
			Arrays.asList(new ESLocalization(Language.US, "Nemo Enim Coquit", "Catch a tropical fish with Fried")), "TROPICAL_FISH",
			Arrays.asList(new ESTrigger("tropical_fish", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	BREAKER_BREAKER(NEMO_ENIM_COQUIT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/breaker_breaker"), 
			Arrays.asList(new ESLocalization(Language.US, "Breaker, Breaker", "Place blocks over a torch using Wand")), "TORCH",
			Arrays.asList(new ESTrigger("torch", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	THORGY(BREAKER_BREAKER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/thorgy"), 
			Arrays.asList(new ESLocalization(Language.US, "Thorgy!", "Put a tamed wolf into your Irene's Lasso")), "WOLF_SPAWN_EGG",
			Arrays.asList(new ESTrigger("wolf", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	DID_YOU_REALLY_WAND_TO_DO_THAT(THORGY, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/did_you_really_wand_to_do_that"), 
			Arrays.asList(new ESLocalization(Language.US, "Did You Really Wand To Do That?", "Break a Carrot on a Stick with the Wand enchantment")), 
			"CARROT_ON_A_STICK", Arrays.asList(new ESTrigger("break", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	FREE_PETS(DID_YOU_REALLY_WAND_TO_DO_THAT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/free_pets"), 
			Arrays.asList(new ESLocalization(Language.US, "Free Pets", "Grab every tameable animal in Irene's Lasso")), "CARROT_ON_A_STICK",
			Arrays.asList(new ESTrigger("wolf", new ImpossibleTrigger()), new ESTrigger("ocelot", new ImpossibleTrigger(), 0, 0, 3), 
			new ESTrigger("cat", new ImpossibleTrigger(), 0, 4, 0), new ESTrigger("horse", new ImpossibleTrigger()), new ESTrigger("donkey", new ImpossibleTrigger()), 
			new ESTrigger("mule", new ImpossibleTrigger()), new ESTrigger("parrot", new ImpossibleTrigger()), 
			new ESTrigger("llama", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	SHARING_IS_CARING(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/sharing_is_caring"), 
			Arrays.asList(new ESLocalization(Language.US, "Sharing Is Caring", "Give your friend an item with Exp. Share")), "EXPERIENCE_BOTTLE",
			Arrays.asList(new ESTrigger("player", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	NOT_THAT_KIND(SHARING_IS_CARING, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/not_that_kind"), 
			Arrays.asList(new ESLocalization(Language.US, "Not That Kind", "Kill every breedable animal with Knock Up")), "DIAMOND_SWORD",
			Arrays.asList(new ESTrigger("chicken", new ImpossibleTrigger()), new ESTrigger("cat", new ImpossibleTrigger(), 0, 4, 0), 
			new ESTrigger("cow", new ImpossibleTrigger()), new ESTrigger("fox", new ImpossibleTrigger(), 0, 4, 0), new ESTrigger("horse", new ImpossibleTrigger()),
			new ESTrigger("llama", new ImpossibleTrigger()), new ESTrigger("mushroom_cow", new ImpossibleTrigger()),
			new ESTrigger("ocelot", new ImpossibleTrigger(), 0, 0, 3), new ESTrigger("panda", new ImpossibleTrigger(), 0, 4, 0), 
			new ESTrigger("pig", new ImpossibleTrigger()), new ESTrigger("rabbit", new ImpossibleTrigger()), new ESTrigger("sheep", new ImpossibleTrigger()), 
			new ESTrigger("wolf", new ImpossibleTrigger()), new ESTrigger("turtle", new ImpossibleTrigger())),
			new Rewards().setExperience(25), Frame.TASK),
	SUPER_CHARGED(NOT_THAT_KIND, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/super_charged"), 
			Arrays.asList(new ESLocalization(Language.US, "Super Charged", "Create a charged creeper with a Shock Aspect attack")), "DIAMOND_AXE",
			Arrays.asList(new ESTrigger("lightning", new ImpossibleTrigger())), new Rewards().setExperience(35), Frame.TASK),
	NOT_VERY_EFFECTIVE(SUPER_CHARGED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/not_very_effective"), 
			Arrays.asList(new ESLocalization(Language.US, "Not Very Effective...", "Kill a Drowned with a Brine sword")), "NAUTILUS_SHELL",
			Arrays.asList(new ESTrigger("drowned", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	HEADHUNTER(NOT_VERY_EFFECTIVE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/headhunter"), 
			Arrays.asList(new ESLocalization(Language.US, "Headhunter", "Steal a player's head")), "PLAYER_HEAD",
			Arrays.asList(new ESTrigger("player_head", new ImpossibleTrigger())),
			new Rewards().setExperience(100), Frame.GOAL),
	DOUBLE_HEADER(HEADHUNTER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/double_header"), 
			Arrays.asList(new ESLocalization(Language.US, "Double Header", "Get two Wither Skeleton skulls from one kill")), "WITHER_SKELETON_SKULL",
			Arrays.asList(new ESTrigger("wither_skull", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	SUPER_EFFECTIVE(DOUBLE_HEADER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/super_effective"), 
			Arrays.asList(new ESLocalization(Language.US, "Super Effective!", "Kill a Minecraft boss with a Brine sword")), "HEART_OF_THE_SEA",
			Arrays.asList(new ESTrigger("boss", new ImpossibleTrigger())), new Rewards().setExperience(250), Frame.GOAL),
	MOTHERLOAD(SUPER_EFFECTIVE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/motherload"), 
			Arrays.asList(new ESLocalization(Language.US, "Motherload", "Kill the Ender Dragon using Exp. Share")), "DRAGON_EGG",
			Arrays.asList(new ESTrigger("dragon", new ImpossibleTrigger())), 
			new Rewards(), Frame.CHALLENGE),
	HEX_BAG(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/hex_bag"), 
			Arrays.asList(new ESLocalization(Language.US, "Hex Bag", "Drown a player using Drowned")), "WITCH_SPAWN_EGG",
			Arrays.asList(new ESTrigger("player", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	SEVEN_POINT_EIGHT(HEX_BAG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/seven_point_eight"), 
			Arrays.asList(new ESLocalization(Language.US, "7.8/10", "Have 3 mobs drowning at the same time with Drowned")), "WATER_BUCKET",
			Arrays.asList(new ESTrigger("drowning", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	POSEIDONS_DAY_OFF(SEVEN_POINT_EIGHT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/poseidons_day_off"), 
			Arrays.asList(new ESLocalization(Language.US, "Poseidon's Day Off", "Kill a mob with Transmutation without changing the loot")), 
			"PRISMARINE_SHARD", Arrays.asList(new ESTrigger("day_off", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	FISHIER_BUSINESS(POSEIDONS_DAY_OFF, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fishy_business"), 
			Arrays.asList(new ESLocalization(Language.US, "Fishier Business", "Collect all types of fish using Transmutation")), "COD",
			Arrays.asList(new ESTrigger("cod", new ImpossibleTrigger()), new ESTrigger("salmon", new ImpossibleTrigger()), 
			new ESTrigger("tropical_fish", new ImpossibleTrigger()), new ESTrigger("pufferfish", new ImpossibleTrigger())), 
			new Rewards().setExperience(75), Frame.TASK),
	POSEIDON_REBORN(FISHIER_BUSINESS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/poseidon_reborn"), 
			Arrays.asList(new ESLocalization(Language.US, "Poseidon Reborn", "Use Transmutation to obtain a Trident")), "TRIDENT",
			Arrays.asList(new ESTrigger("trident", new ImpossibleTrigger())), new Rewards().setExperience(200), Frame.CHALLENGE),
	CERBERUS(POSEIDON_REBORN, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/cerberus"), 
			Arrays.asList(new ESLocalization(Language.US, "Cerberus?", "Increase your reward while using a Transmutation trident")), "SOUL_SAND",
			Arrays.asList(new ESTrigger("obsidian", new ImpossibleTrigger())), new Rewards().setExperience(1000), Frame.CHALLENGE),
	BONEMEAL_PLUS(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/bonemeal_plus"), 
			Arrays.asList(new ESLocalization(Language.US, "Bonemeal Plus", "Use Flower Gift on a two-high flower")), "BONE_MEAL",
			Arrays.asList(new ESTrigger("bonemeal", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	FOURTY_NINERS(BONEMEAL_PLUS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fourty_niners"), 
			Arrays.asList(new ESLocalization(Language.US, "49ers", "Get 81 nuggets from using Gold Digger")), "GOLD_BLOCK",
			Arrays.asList(new ESTrigger("goldblock", new ImpossibleTrigger(), 81)), new Rewards().setExperience(20), Frame.TASK),
	CHICKEN_OR_THE_EGG(FOURTY_NINERS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/chicken_or_the_egg"), 
			Arrays.asList(new ESLocalization(Language.US, "Chicken or the Egg?", "Hit a chicken with an egg using Splatter Fest")), "EGG",
			Arrays.asList(new ESTrigger("egg", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	EGGED_BY_MYSELF(CHICKEN_OR_THE_EGG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/egged_by_myself"), 
			Arrays.asList(new ESLocalization(Language.US, "Egged By Myself", "Hit yourself with an egg launched from Splatter Fest")), "EGG",
			Arrays.asList(new ESTrigger("egg", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	MISSED(EGGED_BY_MYSELF, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/missed"), 
			Arrays.asList(new ESLocalization(Language.US, "Missed", "Make 16 attacks miss with Sand Veil")), "SAND",
			Arrays.asList(new ESTrigger("sand", new ImpossibleTrigger(), 16)), new Rewards().setExperience(60), Frame.TASK),
	JUST_AS_SWEET(MISSED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_as_sweet"), 
			Arrays.asList(new ESLocalization(Language.US, "Just As Sweet", "Successfully get a Wither Rose using Flower Gift")), "WITHER_ROSE",
			Arrays.asList(new ESTrigger("wither_rose", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL, 4),
	LAAAGGGGGG(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/laaagggggg"), 
			Arrays.asList(new ESLocalization(Language.US, "Laaagggggg", "Lag the server with various particle effects")), "FIREWORK_ROCKET",
			Arrays.asList(new ESTrigger("lag", new ImpossibleTrigger())), new Rewards().setExperience(10), Frame.TASK),
	EASY_OUT(LAAAGGGGGG, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/easy_out"), 
			Arrays.asList(new ESLocalization(Language.US, "Easy Out", "Turn off the campfire using Moisturize")), "CAMPFIRE",
			Arrays.asList(new ESTrigger("campfire", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK, 4),
	JUST_ADD_WATER(EASY_OUT, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_add_water"), 
			Arrays.asList(new ESLocalization(Language.US, "Just Add Water", "Add water to concrete powder using Moisturize")), "WHITE_CONCRETE_POWDER",
			Arrays.asList(new ESTrigger("concrete", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK),
	REPAIRED(JUST_ADD_WATER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/repaired"), 
			Arrays.asList(new ESLocalization(Language.US, "Repaired", "Repair a broken block with Moisturize")), "STONE_BRICKS",
			Arrays.asList(new ESTrigger("broken_bricks", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	KEPT_ON_HAND(REPAIRED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/kept_on_hand"), 
			Arrays.asList(new ESLocalization(Language.US, "Kept On Hand", "Keep an item after death using Soulbound")), "ENCHANTED_BOOK",
			Arrays.asList(new ESTrigger("soulbound", new ImpossibleTrigger())), new Rewards().setExperience(50), Frame.TASK),
	FEAR_THE_REAPER(KEPT_ON_HAND, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fear_the_reaper"), 
			Arrays.asList(new ESLocalization(Language.US, "Fear the Reaper", "Steal another player's item using Soul Reaper")), "DIAMOND_HOE",
			Arrays.asList(new ESTrigger("reaper", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	READY_AFTER_DEATH(FEAR_THE_REAPER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/ready_after_death"), 
			Arrays.asList(new ESLocalization(Language.US, "Ready After Death", "Save 9 unique types of diamond tools, weapons, and armor in death using Soulbound")), 
			"DIAMOND_CHESTPLATE", Arrays.asList(new ESTrigger("soulbound", new ImpossibleTrigger())), new Rewards().setExperience(400), Frame.GOAL),
	REAPED_THE_REAPER(READY_AFTER_DEATH, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/reaped_the_reaper"), 
			Arrays.asList(new ESLocalization(Language.US, "Reaped the Reaper", "Steal another player's Soul Reaper item with Soul Reaper")), "DIAMOND_HOE",
			Arrays.asList(new ESTrigger("reaper", new ImpossibleTrigger())), new Rewards().setExperience(1200), Frame.CHALLENGE),
	COFFEE_BREAK(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/coffee_break"), 
			Arrays.asList(new ESLocalization(Language.US, "Coffee Break", "Put a No Rest helmet on when Phantoms can spawn")), "SUGAR",
			Arrays.asList(new ESTrigger("coffee", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK),
	THIS_GIRL_IS_ON_FIRE(COFFEE_BREAK, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/this_girl_is_on_fire"), 
			Arrays.asList(new ESLocalization(Language.US, "This Girl is on Fire", "While walking on Magma-ized lava, catch fire")), "LAVA_BUCKET",
			Arrays.asList(new ESTrigger("lava", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	FLAME_KEEPER(THIS_GIRL_IS_ON_FIRE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/flame_keeper"), 
			Arrays.asList(new ESLocalization(Language.US, "Flame Keeper", "Create magma in a world that shouldn't have lava")), "FLINT_AND_STEEL",
			Arrays.asList(new ESTrigger("flame", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	MADE_FOR_WALKING(FLAME_KEEPER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/made_for_walking"), 
			Arrays.asList(new ESLocalization(Language.US, "Made for Walking", "Walk over the void with Void Walker")), 
			"DIAMOND_BOOTS", Arrays.asList(new ESTrigger("boots", new ImpossibleTrigger())), new Rewards().setExperience(60), Frame.TASK),
	DETERMINED_CHEATER(MADE_FOR_WALKING, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/determined_cheater"), 
			Arrays.asList(new ESLocalization(Language.US, "Determined Cheater", "Break obsidian made using Void Walker")), "OBSIDIAN",
			Arrays.asList(new ESTrigger("cheater", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	I_AINT_AFRAID_OF_NO_GHOSTS(DETERMINED_CHEATER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/i_aint_afraid_of_no_ghosts"), 
			Arrays.asList(new ESLocalization(Language.US, "I Ain't Afraid of No Ghosts", "Sleep 10 times while wearing an Unrest helmet")), "WHITE_BED",
			Arrays.asList(new ESTrigger("unrest", new ImpossibleTrigger(), 10)), new Rewards().setExperience(150), Frame.GOAL),
	IM_YOU_BUT_SHORTER(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/im_you_but_shorter"), 
			Arrays.asList(new ESLocalization(Language.US, "I'm You, But Shorter", "Teleport away from an enderman using Warp")), "ENDER_PEARL",
			Arrays.asList(new ESTrigger("enderpearl", new ImpossibleTrigger())), new Rewards().setExperience(30), Frame.TASK),
	PANZER_SOLDIER(IM_YOU_BUT_SHORTER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/panzer_soldier"), 
			Arrays.asList(new ESLocalization(Language.US, "Panzer Soldier", "Wear 4 pieces of armor with the highest level of Tank")), "OBSIDIAN",
			Arrays.asList(new ESTrigger("tank", new ImpossibleTrigger())), new Rewards().setExperience(60), Frame.TASK),
	TOO_CLOSE(PANZER_SOLDIER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/too_close"), 
			Arrays.asList(new ESLocalization(Language.US, "Too Close", "Fail to activate Icarus in flight")), "ELYTRA",
			Arrays.asList(new ESTrigger("failure", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK),
	EXTRA_POWER(TOO_CLOSE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/extra_power"), 
			Arrays.asList(new ESLocalization(Language.US, "Extra Power", "Eat an enchanted golden apple while wearing the highest level of Life")), 
			"ENCHANTED_GOLDEN_APPLE", Arrays.asList(new ESTrigger("power", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	CRUISING_ALTITUDE(EXTRA_POWER, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/cruising_altitude"), 
			Arrays.asList(new ESLocalization(Language.US, "Cruising Altitude", "Travel to 12000m using Frequent Flyer")), "ELYTRA",
			Arrays.asList(new ESTrigger("elytra", new ImpossibleTrigger())), new Rewards().setExperience(150), Frame.GOAL),
	DANGER_DEFEATED(CRUISING_ALTITUDE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/danger_defeated"), 
			Arrays.asList(new ESLocalization(Language.US, "Danger Defeated", "Kill a Wither using Gung Ho")), "NETHER_STAR",
			Arrays.asList(new ESTrigger("wither", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	DIVINE_RETRIBUTION(DANGER_DEFEATED, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/divine_retribution"), 
			Arrays.asList(new ESLocalization(Language.US, "Divine Retribution", "Kill a player from the dead using a Sacrifice chestplate")), 
			"TOTEM_OF_UNDYING", Arrays.asList(new ESTrigger("retribution", new ImpossibleTrigger())), new Rewards().setExperience(1000), Frame.CHALLENGE),
	LOOK_WHAT_YOU_MADE_ME_DO(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/look_what_you_made_me_do"), 
			Arrays.asList(new ESLocalization(Language.US, "Look What You Made Me Do", "Kill a Pillager using a Pillage crossbow")), "CROSSBOW",
			Arrays.asList(new ESTrigger("pillage", new ImpossibleTrigger())), new Rewards().setExperience(20), Frame.TASK, 4),
	JUST_DIE_ALREADY(LOOK_WHAT_YOU_MADE_ME_DO, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/just_die_already"), 
			Arrays.asList(new ESLocalization(Language.US, "Just DIE Already!", "Kill a Phantom in one shot using Stone Throw")), "PHANTOM_SPAWN_EGG",
			Arrays.asList(new ESTrigger("phantom", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK, 4),
	UNDERKILL(JUST_DIE_ALREADY, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/underkill"), 
			Arrays.asList(new ESLocalization(Language.US, "Underkill", "Hit the Ender Dragon using Stone Throw")), "DRAGON_EGG",
			Arrays.asList(new ESTrigger("dragon", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL, 4),
	WHERE_DID_THAT_COME_FROM(UNDERKILL, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/where_did_that_come_from"), 
			Arrays.asList(new ESLocalization(Language.US, "Where Did That Come From?", "Kill a skeleton from 100 blocks away using Sniper")), "ARROW",
			Arrays.asList(new ESTrigger("sniper", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	THAT_FOOD_IS_FINE(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/that_food_is_fine"), 
			Arrays.asList(new ESLocalization(Language.US, "That Food is... Fine?", "Eat a piece of food with a negative potion effect and remove it with Magic Guard")), 
			"POISONOUS_POTATO", Arrays.asList(new ESTrigger("food", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	DEFLECTION(THAT_FOOD_IS_FINE, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/deflection"), 
			Arrays.asList(new ESLocalization(Language.US, "Deflection", "Kill a mob with an arrow deflected with Hard Bounce")), "SHIELD",
			Arrays.asList(new ESTrigger("shield", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	IRON_MAN(DEFLECTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/iron_man"), 
			Arrays.asList(new ESLocalization(Language.US, "Iron Man", "Live through a death blow because of Iron Defense")), "IRON_BLOCK",
			Arrays.asList(new ESTrigger("blocked", new ImpossibleTrigger())), new Rewards().setExperience(500), Frame.CHALLENGE),
	IRONT_YOU_GLAD(ENCHANTMENT_SOLUTION, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/iront_you_glad"), 
			Arrays.asList(new ESLocalization(Language.US, "Iron't You Glad?", "Obtain more than one iron ingot from an iron ore using Smeltery")), 
			"IRON_INGOT", Arrays.asList(new ESTrigger("iron", new ImpossibleTrigger())), new Rewards().setExperience(25), Frame.TASK),
	FAST_AND_FURIOUS(IRONT_YOU_GLAD, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/fast_and_furious"), 
			Arrays.asList(new ESLocalization(Language.US, "Fast and Furious", "Break multiple blocks at once using Height++ or Width++")), "DIAMOND_PICKAXE",
			Arrays.asList(new ESTrigger("diamond_pickaxe", new ImpossibleTrigger())), new Rewards().setExperience(40), Frame.TASK),
	HEY_IT_WORKS(FAST_AND_FURIOUS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/hey_it_works"), 
			Arrays.asList(new ESLocalization(Language.US, "Hey It Works!", "Break a Shulker Box using Telepathy")), "PURPLE_SHULKER_BOX",
			Arrays.asList(new ESTrigger("shulker_box", new ImpossibleTrigger())), new Rewards().setExperience(75), Frame.TASK),
	NO_PANIC(HEY_IT_WORKS, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/no_panic"), 
			Arrays.asList(new ESLocalization(Language.US, "No Panic", "Grab an item directly over lava using Telepathy")), "LAVA_BUCKET",
			Arrays.asList(new ESTrigger("lava", new ImpossibleTrigger())), new Rewards().setExperience(100), Frame.GOAL),
	OVER_9000(NO_PANIC, new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/over_9000"), 
			Arrays.asList(new ESLocalization(Language.US, "Over 9000", "Collect 9001 extra items with Height++ or Width++")), "DIAMOND_PICKAXE",
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
	private List<ESLocalization> localizations;
	
	ESAdvancement(NamespacedKey key, List<ESLocalization> defaultLocalizations, String icon, List<ESTrigger> triggers, Rewards rewards, Frame frame){
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
		this.localizations = defaultLocalizations;
	}
	
	ESAdvancement(ESAdvancement parent, NamespacedKey key, List<ESLocalization> defaultLocalizations, String icon, List<ESTrigger> triggers, 
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
		this.localizations = defaultLocalizations;
	}
	
	ESAdvancement(ESAdvancement parent, NamespacedKey key, List<ESLocalization> defaultLocalizations, String icon, List<ESTrigger> triggers, 
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
		this.localizations = defaultLocalizations;
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

	public List<ESLocalization> getLocalizations() {
		return localizations;
	}

	public void setLocalizations(List<ESLocalization> localizations) {
		this.localizations = localizations;
	}
}
