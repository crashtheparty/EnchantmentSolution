package org.ctp.enchantmentsolution.enums;

import java.util.*;

import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class Loots {

	private static List<Loots> ALL, DEFAULT;
	private final String name;
	private LootType type;
	private List<CustomEnchantment> enchantments, blacklistEnchantments;
	private boolean useWeights, allowMultiple, chanceFromDefault, lowerChances;
	private int enchantingBookshelvesMin, enchantingBookshelvesMax, enchantingBookshelvesNormalize, slotMin, slotMax, slotNormalize, enchantabilityMin,
	enchantabilityMax, enchantabilityNormalize, levelsMin, levelsMax, levelsNormalize;
	private double chance, lowerBy;

	public static void createDefaults() {
		ALL = new ArrayList<Loots>();
		Loots minigameOne = new Loots("default_minigame_loot_one", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 6, 0);
		Loots minigameTwo = new Loots("default_minigame_loot_two", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots minigameThree = new Loots("default_minigame_loot_three", Arrays.asList(), true, true, true, 0.0, true, 0.50, 5, 6, 0);
		Loots minigameDefault = new Loots("default_minigame_loot_other", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 6, 0);
		Loots enchantedBookFifty = new Loots("default_villager_book_fifty", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots enchantedBookThirty = new Loots("default_villager_book_thirty", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots ironAxe = new Loots("default_villager_iron_axe", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots ironPickaxe = new Loots("default_villager_iron_pickaxe", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots ironShovel = new Loots("default_villager_iron_shovel", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots ironSword = new Loots("default_villager_iron_sword", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots fishingRod = new Loots("default_villager_fishing_rod", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots bow = new Loots("default_villager_bow", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots crossbow = new Loots("default_villager_crossbow", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondSword = new Loots("default_villager_diamond_sword", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondShovel = new Loots("default_villager_diamond_shovel", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondBoots = new Loots("default_villager_diamond_boots", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondHelmet = new Loots("default_villager_diamond_helmet", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondPickaxe = new Loots("default_villager_diamond_pickaxe", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondAxe = new Loots("default_villager_diamond_axe", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondLeggings = new Loots("default_villager_diamond_leggings", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots diamondChestplate = new Loots("default_villager_diamond_chestplate", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots villagerOther = new Loots("default_villager_other", LootType.RANDOM, Arrays.asList(), Arrays.asList(), true, false, true, 0.0, true, 0.50);
		Loots piglinTrades = new Loots("default_piglin_trades", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots fishingLoot = new Loots("default_fishing_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots zombie = new Loots("default_zombie_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots zombieVillager = new Loots("default_zombie_villager_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots drowned = new Loots("default_drowned_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots husk = new Loots("default_husk_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots piglin = new Loots("default_piglin_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots skeleton = new Loots("default_skeleton_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots stray = new Loots("default_stray_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots witherSkeleton = new Loots("default_wither_skeleton_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots zombifiedPiglin = new Loots("default_zombified_piglin_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots defaultMob = new Loots("default_mob_loot", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots endCity = new Loots("default_end_city_treasure", Arrays.asList(), true, true, true, 0.0, true, 0.50, 15, 24, 0, 3, 6, 0);
		Loots ancientCity = new Loots("default_ancient_city", Arrays.asList(), true, true, true, 0.0, true, 0.50, 15, 24, 0, 3, 6, 0);
		Loots simpleDungeon = new Loots("default_simple_dungeon", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots shipwreckSupply = new Loots("default_shipwreck_supply", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots woodlandMansion = new Loots("default_woodland_mansion", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 1, 6, 0);
		Loots strongholdLibrary = new Loots("default_stronghold_library", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 1, 6, 0);
		Loots strongholdCrossing = new Loots("default_stronghold_crossing", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 1, 6, 0);
		Loots strongholdCorridor = new Loots("default_stronghold_corridor", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 1, 6, 0);
		Loots underwaterRuinBig = new Loots("default_underwater_ruin_big", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots underwaterRuinSmall = new Loots("default_underwater_ruin_small", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		Loots pillagerOutpost = new Loots("default_pillager_outpost", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 1, 6, 0);
		Loots bastionBridge = new Loots("default_bastion_bridge", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 2, 6, 0);
		Loots bastionHoglinStable = new Loots("default_bastion_hoglin_stable", Arrays.asList(), true, true, true, 0.0, true, 0.50, 10, 24, 0, 2, 6, 0);
		Loots bastionOther = new Loots("default_bastion_other", Arrays.asList(), true, true, true, 0.0, true, 0.50, 15, 24, 0, 2, 6, 0);
		Loots bastionTreasure = new Loots("default_bastion_treasure", Arrays.asList(), true, true, true, 0.0, true, 0.50, 12, 24, 0, 3, 6, 0);
		Loots ruinedPortal = new Loots("default_ruined_portal", Arrays.asList(), true, true, true, 0.0, true, 0.50, 6, 24, 0, 1, 6, 0);
		Loots defaultChest = new Loots("default_chest_treasure", Arrays.asList(), true, true, true, 0.0, true, 0.50, 0, 24, 0, 0, 6, 0);
		DEFAULT = Arrays.asList(minigameOne, minigameTwo, minigameThree, minigameDefault, enchantedBookFifty, enchantedBookThirty, ironAxe, ironPickaxe, ironShovel, ironSword, fishingRod, bow, crossbow, diamondSword, diamondShovel, diamondBoots, diamondHelmet, diamondPickaxe, diamondAxe, diamondLeggings, diamondChestplate, villagerOther, piglinTrades, fishingLoot, zombie, zombieVillager, drowned, husk, piglin, skeleton, stray, witherSkeleton, zombifiedPiglin, defaultMob, endCity, ancientCity, simpleDungeon, shipwreckSupply, woodlandMansion, strongholdLibrary, strongholdCrossing, strongholdCorridor, underwaterRuinBig, underwaterRuinSmall, pillagerOutpost, bastionBridge, bastionHoglinStable, bastionOther, bastionTreasure, ruinedPortal, defaultChest);
		for(Loots l: DEFAULT)
			ALL.add(l);
	}

	public static void createCustomLoot() {
		YamlConfig config = Configurations.getConfigurations().getLootTypes().getConfig();
		List<String> keys = config.getLevelEntryKeys("");
		for(String s: keys) {
			String name = s;
			if (s.toLowerCase(Locale.ROOT).startsWith("default")) {
				Chatable.get().sendWarning("Loot entries in loot_types.yml may not start with 'default': skipping " + s);
				continue;
			}
			String type = config.getString(s + ".type");
			LootType lootType = null;
			boolean useWeights = config.getBoolean(s + ".use_weights");
			boolean allowMultiple = config.getBoolean(s + ".multiple.allow");
			boolean chanceFromDefault = config.getBoolean(s + ".multiple.chance_from_default");
			double chance = config.getDouble(s + ".multiple.chance");
			boolean lowerChances = config.getBoolean(s + ".multiple.lower_chances");
			double lowerBy = config.getDouble(s + ".multiple.lower_by");
			List<CustomEnchantment> enchantments = new ArrayList<CustomEnchantment>();
			List<CustomEnchantment> blacklist = new ArrayList<CustomEnchantment>();
			Loots loot = null;
			int min = 0, max = 0, norm = 0, minBooks = 0, maxBooks = 0, normBooks = 0;
			switch (type.toLowerCase(Locale.ROOT)) {
				case "from_selection":
					if (lootType == null) lootType = LootType.FROM_SELECTION;
					if (config.getStringList(s + ".enchantments") != null) for(String ench: config.getStringList(s + ".enchantments")) {
						CustomEnchantment enchant = RegisterEnchantments.getByName(ench);
						if (enchant.isEnabled()) enchantments.add(enchant);
					}
				case "random":
					if (lootType == null) lootType = LootType.RANDOM;
					if (config.getStringList(s + ".blacklist_enchantments") != null) for(String ench: config.getStringList(s + ".blacklist_enchantments")) {
						CustomEnchantment enchant = RegisterEnchantments.getByName(ench);
						if (enchant.isEnabled()) blacklist.add(enchant);
					}
					int levelNormalize = config.getInt(s + ".level_normalize");
					loot = new Loots(name, lootType, blacklist, enchantments, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy, levelNormalize);
					break;
				case "enchanting_levels":
					if (lootType == null) lootType = LootType.LEVELS;
					min = config.getInt(s + ".levels.min");
					max = config.getInt(s + ".levels.max");
					norm = config.getInt(s + ".levels.normalize");
				case "enchantability":
					if (lootType == null) {
						lootType = LootType.ENCHANTABILITY;
						min = config.getInt(s + ".enchantability.min");
						max = config.getInt(s + ".enchantability.max");
						norm = config.getInt(s + ".enchantability.normalize");
					}
					if (config.getStringList(s + ".blacklist_enchantments") != null) for(String ench: config.getStringList(s + ".blacklist_enchantments")) {
						CustomEnchantment enchant = RegisterEnchantments.getByName(ench);
						if (enchant.isEnabled()) blacklist.add(enchant);
					}
					loot = new Loots(name, lootType, blacklist, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy, min, max, norm);
					break;
				case "enchanting_table":
					if (lootType == null) lootType = LootType.ENCHANTING_TABLE;
					minBooks = config.getInt(s + ".enchanting.bookshelves.min");
					maxBooks = config.getInt(s + ".enchanting.bookshelves.max");
					normBooks = config.getInt(s + ".enchanting.bookshelves.normalize");
				case "enchanting_table_location":
					if (lootType == null) lootType = LootType.ENCHANTING_TABLE_LOCATION;
					if (config.getStringList(s + ".blacklist_enchantments") != null) for(String ench: config.getStringList(s + ".blacklist_enchantments")) {
						CustomEnchantment enchant = RegisterEnchantments.getByName(ench);
						if (enchant.isEnabled()) blacklist.add(enchant);
					}
					int slotMin = config.getInt(s + ".enchanting.slot.min");
					int slotMax = config.getInt(s + ".enchanting.slot.max");
					int slotNorm = config.getInt(s + ".enchanting.slot.normalize");
					if (lootType == LootType.ENCHANTING_TABLE) loot = new Loots(name, blacklist, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy, minBooks, maxBooks, normBooks, slotMin, slotMax, slotNorm);
					else
						loot = new Loots(name, blacklist, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy, slotMin, slotMax, slotNorm);
					break;
			}
			if (loot != null) ALL.add(loot);
		}

	}

	public static Loots getLoot(String name) {
		for(Loots loot: ALL)
			if (loot.getName().equals(name)) return loot;
		return null;
	}

	private Loots(String name) {
		this.name = name;
		enchantments = new ArrayList<CustomEnchantment>();
		blacklistEnchantments = new ArrayList<CustomEnchantment>();
	}

	private Loots(String name, LootType type, List<CustomEnchantment> blacklist, List<CustomEnchantment> enchantments, boolean useWeights,
	boolean allowMultiple, boolean chanceFromDefault, double chance, boolean lowerChances, double lowerBy) {
		this(name);
		setType(type);
		setBlacklistEnchantments(blacklist);
		setEnchantments(enchantments);
		setUseWeights(useWeights);
		setAllowMultiple(allowMultiple);
		setChanceFromDefault(chanceFromDefault);
		setChance(chance);
		setLowerChances(lowerChances);
		setLowerBy(lowerBy);
	}

	private Loots(String name, LootType type, List<CustomEnchantment> blacklist, List<CustomEnchantment> enchantments, boolean useWeights,
	boolean allowMultiple, boolean chanceFromDefault, double chance, boolean lowerChances, double lowerBy, int levelsNormalize) {
		this(name, type, blacklist, enchantments, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy);
		setLevelsNormalize(levelsNormalize);
	}

	private Loots(String name, List<CustomEnchantment> blacklist, boolean useWeights, boolean allowMultiple, boolean chanceFromDefault, double chance,
	boolean lowerChances, double lowerBy, int enchantingSlotMin, int enchantingSlotMax, int enchantingSlotNormalize) {
		this(name, LootType.ENCHANTING_TABLE_LOCATION, blacklist, null, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy);
		setSlotMin(enchantingSlotMin);
		setSlotMax(enchantingSlotMax);
		setSlotNormalize(enchantingSlotNormalize);
	}

	private Loots(String name, List<CustomEnchantment> blacklist, boolean useWeights, boolean allowMultiple, boolean chanceFromDefault, double chance,
	boolean lowerChances, double lowerBy, int enchantingBookshelvesMin, int enchantingBookshelvesMax, int enchantingBookshelvesNormalize,
	int enchantingSlotMin, int enchantingSlotMax, int enchantingSlotNormalize) {
		this(name, LootType.ENCHANTING_TABLE, blacklist, null, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy);
		setEnchantingBookshelvesMin(enchantingBookshelvesMin);
		setEnchantingBookshelvesMax(enchantingBookshelvesMax);
		setEnchantingBookshelvesNormalize(enchantingBookshelvesNormalize);
		setSlotMin(enchantingSlotMin);
		setSlotMax(enchantingSlotMax);
		setSlotNormalize(enchantingSlotNormalize);
	}

	private Loots(String name, LootType type, List<CustomEnchantment> blacklist, boolean useWeights, boolean allowMultiple, boolean chanceFromDefault,
	double chance, boolean lowerChances, double lowerBy, int min, int max, int normalize) {
		this(name, type, blacklist, null, useWeights, allowMultiple, chanceFromDefault, chance, lowerChances, lowerBy);
		if (type == LootType.ENCHANTABILITY) {
			setEnchantabilityMin(min);
			setEnchantabilityMax(max);
			setEnchantabilityNormalize(normalize);
		} else if (type == LootType.LEVELS) {
			setLevelsMin(min);
			setLevelsMax(max);
			setLevelsNormalize(normalize);
		}
	}

	public String getName() {
		return name;
	}

	public LootType getType() {
		return type;
	}

	public void setType(LootType type) {
		this.type = type;
	}

	public List<CustomEnchantment> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<CustomEnchantment> enchantments) {
		if (enchantments == null) return;
		this.enchantments = enchantments;
	}

	public List<CustomEnchantment> getBlacklistEnchantments() {
		return blacklistEnchantments;
	}

	public void setBlacklistEnchantments(List<CustomEnchantment> blacklistEnchantments) {
		if (blacklistEnchantments == null) return;
		this.blacklistEnchantments = blacklistEnchantments;
	}

	public boolean useWeights() {
		return useWeights;
	}

	public void setUseWeights(boolean useWeights) {
		this.useWeights = useWeights;
	}

	public boolean allowMultiple() {
		return allowMultiple;
	}

	public void setAllowMultiple(boolean allowMultiple) {
		this.allowMultiple = allowMultiple;
	}

	public boolean lowerChances() {
		return lowerChances;
	}

	public void setLowerChances(boolean lowerChances) {
		this.lowerChances = lowerChances;
	}

	public int getEnchantingBookshelvesMin() {
		return enchantingBookshelvesMin;
	}

	public void setEnchantingBookshelvesMin(int enchantingBookshelvesMin) {
		this.enchantingBookshelvesMin = enchantingBookshelvesMin;
	}

	public int getEnchantingBookshelvesMax() {
		return enchantingBookshelvesMax;
	}

	public void setEnchantingBookshelvesMax(int enchantingBookshelvesMax) {
		this.enchantingBookshelvesMax = enchantingBookshelvesMax;
	}

	public int getEnchantingBookshelvesNormalize() {
		return enchantingBookshelvesNormalize;
	}

	public void setEnchantingBookshelvesNormalize(int enchantingBookshelvesNormalize) {
		this.enchantingBookshelvesNormalize = enchantingBookshelvesNormalize;
	}

	public int getSlotMin() {
		return slotMin;
	}

	public void setSlotMin(int slotMin) {
		this.slotMin = slotMin;
	}

	public int getSlotMax() {
		if (slotMax > 15 && !ConfigString.LEVEL_FIFTY.getBoolean()) return 15;
		return slotMax;
	}

	public void setSlotMax(int slotMax) {
		this.slotMax = slotMax;
	}

	public int getSlotNormalize() {
		return slotNormalize;
	}

	public void setSlotNormalize(int slotNormalize) {
		this.slotNormalize = slotNormalize;
	}

	public int getEnchantabilityMin() {
		return enchantabilityMin;
	}

	public void setEnchantabilityMin(int enchantabilityMin) {
		this.enchantabilityMin = enchantabilityMin;
	}

	public int getEnchantabilityMax() {
		return enchantabilityMax;
	}

	public void setEnchantabilityMax(int enchantabilityMax) {
		this.enchantabilityMax = enchantabilityMax;
	}

	public int getEnchantabilityNormalize() {
		return enchantabilityNormalize;
	}

	public void setEnchantabilityNormalize(int enchantabilityNormalize) {
		this.enchantabilityNormalize = enchantabilityNormalize;
	}

	public int getLevelsMin() {
		return levelsMin;
	}

	public void setLevelsMin(int levelsMin) {
		this.levelsMin = levelsMin;
	}

	public int getLevelsMax() {
		return levelsMax;
	}

	public void setLevelsMax(int levelsMax) {
		this.levelsMax = levelsMax;
	}

	public int getLevelsNormalize() {
		return levelsNormalize;
	}

	public void setLevelsNormalize(int levelsNormalize) {
		this.levelsNormalize = levelsNormalize;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public double getLowerBy() {
		return lowerBy;
	}

	public void setLowerBy(double lowerBy) {
		// Should not be increasing chances of enchantments the more get put on
		if (lowerBy > 1) lowerBy = 1;
		// Chance would be made null by anything below 0, so just put 0
		if (lowerBy < 0) lowerBy = 0;
		this.lowerBy = lowerBy;
	}

	public boolean chanceFromDefault() {
		return chanceFromDefault;
	}

	public void setChanceFromDefault(boolean chanceFromDefault) {
		this.chanceFromDefault = chanceFromDefault;
	}

	public enum LootType {
		RANDOM(), ENCHANTING_TABLE(), FROM_SELECTION(), ENCHANTABILITY(), LEVELS(), ENCHANTING_TABLE_LOCATION();

	}
}
