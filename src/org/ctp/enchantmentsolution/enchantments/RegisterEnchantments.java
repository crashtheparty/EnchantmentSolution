package org.ctp.enchantmentsolution.enchantments;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.enchantment.EnchantmentData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.item.VanillaItemType;
import org.ctp.crashapi.utils.StringUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.EnchantmentsConfiguration;
import org.ctp.enchantmentsolution.utils.config.LanguageConfiguration;

public class RegisterEnchantments {
	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	private static List<CustomEnchantment> REGISTERED_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	private static List<CustomEnchantment> CURSE_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	private static List<CustomEnchantment> DISABLED_ENCHANTMENTS = new ArrayList<CustomEnchantment>();

	public static final EnchantmentWrapper WATER_WORKER = new EnchantmentWrapper(Enchantment.WATER_WORKER.getKey(), "WATER_WORKER");
	public static final EnchantmentWrapper DAMAGE_ARTHROPODS = new EnchantmentWrapper(Enchantment.DAMAGE_ARTHROPODS.getKey(), "DAMAGE_ARTHROPODS");
	public static final EnchantmentWrapper PROTECTION_EXPLOSIONS = new EnchantmentWrapper(Enchantment.PROTECTION_EXPLOSIONS.getKey(), "PROTECTION_EXPLOSIONS");
	public static final EnchantmentWrapper CHANNELING = new EnchantmentWrapper(Enchantment.CHANNELING.getKey(), "CHANNELING");
	public static final EnchantmentWrapper BINDING_CURSE = new EnchantmentWrapper(Enchantment.BINDING_CURSE.getKey(), "BINDING_CURSE");
	public static final EnchantmentWrapper VANISHING_CURSE = new EnchantmentWrapper(Enchantment.VANISHING_CURSE.getKey(), "VANISHING_CURSE");
	public static final EnchantmentWrapper DEPTH_STRIDER = new EnchantmentWrapper(Enchantment.DEPTH_STRIDER.getKey(), "DEPTH_STRIDER");
	public static final EnchantmentWrapper DIG_SPEED = new EnchantmentWrapper(Enchantment.DIG_SPEED.getKey(), "DIG_SPEED");
	public static final EnchantmentWrapper PROTECTION_FALL = new EnchantmentWrapper(Enchantment.PROTECTION_FALL.getKey(), "PROTECTION_FALL");
	public static final EnchantmentWrapper FIRE_ASPECT = new EnchantmentWrapper(Enchantment.FIRE_ASPECT.getKey(), "FIRE_ASPECT");
	public static final EnchantmentWrapper PROTECTION_FIRE = new EnchantmentWrapper(Enchantment.PROTECTION_FIRE.getKey(), "PROTECTION_FIRE");
	public static final EnchantmentWrapper ARROW_FIRE = new EnchantmentWrapper(Enchantment.ARROW_FIRE.getKey(), "ARROW_FIRE");
	public static final EnchantmentWrapper LOOT_BONUS_BLOCKS = new EnchantmentWrapper(Enchantment.LOOT_BONUS_BLOCKS.getKey(), "LOOT_BONUS_BLOCKS");
	public static final EnchantmentWrapper FROST_WALKER = new EnchantmentWrapper(Enchantment.FROST_WALKER.getKey(), "FROST_WALKER");
	public static final EnchantmentWrapper IMPALING = new EnchantmentWrapper(Enchantment.IMPALING.getKey(), "IMPALING");
	public static final EnchantmentWrapper ARROW_INFINITE = new EnchantmentWrapper(Enchantment.ARROW_INFINITE.getKey(), "ARROW_INFINITE");
	public static final EnchantmentWrapper KNOCKBACK = new EnchantmentWrapper(Enchantment.KNOCKBACK.getKey(), "KNOCKBACK");
	public static final EnchantmentWrapper LOOT_BONUS_MOBS = new EnchantmentWrapper(Enchantment.LOOT_BONUS_MOBS.getKey(), "LOOT_BONUS_MOBS");
	public static final EnchantmentWrapper LOYALTY = new EnchantmentWrapper(Enchantment.LOYALTY.getKey(), "LOYALTY");
	public static final EnchantmentWrapper LUCK = new EnchantmentWrapper(Enchantment.LUCK.getKey(), "LUCK");
	public static final EnchantmentWrapper LURE = new EnchantmentWrapper(Enchantment.LURE.getKey(), "LURE");
	public static final EnchantmentWrapper MENDING = new EnchantmentWrapper(Enchantment.MENDING.getKey(), "MENDING");
	public static final EnchantmentWrapper MULTISHOT = new EnchantmentWrapper(Enchantment.MULTISHOT.getKey(), "MULTISHOT");
	public static final EnchantmentWrapper PIERCING = new EnchantmentWrapper(Enchantment.PIERCING.getKey(), "PIERCING");
	public static final EnchantmentWrapper ARROW_DAMAGE = new EnchantmentWrapper(Enchantment.ARROW_DAMAGE.getKey(), "ARROW_DAMAGE");
	public static final EnchantmentWrapper PROTECTION_PROJECTILE = new EnchantmentWrapper(Enchantment.PROTECTION_PROJECTILE.getKey(), "PROTECTION_PROJECTILE");
	public static final EnchantmentWrapper PROTECTION_ENVIRONMENTAL = new EnchantmentWrapper(Enchantment.PROTECTION_ENVIRONMENTAL.getKey(), "PROTECTION_ENVIRONMENTAL");
	public static final EnchantmentWrapper ARROW_KNOCKBACK = new EnchantmentWrapper(Enchantment.ARROW_KNOCKBACK.getKey(), "ARROW_KNOCKBACK");
	public static final EnchantmentWrapper QUICK_CHARGE = new EnchantmentWrapper(Enchantment.QUICK_CHARGE.getKey(), "QUICK_CHARGE");
	public static final EnchantmentWrapper OXYGEN = new EnchantmentWrapper(Enchantment.OXYGEN.getKey(), "OXYGEN");
	public static final EnchantmentWrapper RIPTIDE = new EnchantmentWrapper(Enchantment.RIPTIDE.getKey(), "RIPTIDE");
	public static final EnchantmentWrapper DAMAGE_ALL = new EnchantmentWrapper(Enchantment.DAMAGE_ALL.getKey(), "DAMAGE_ALL");
	public static final EnchantmentWrapper SILK_TOUCH = new EnchantmentWrapper(Enchantment.SILK_TOUCH.getKey(), "SILK_TOUCH");
	public static final EnchantmentWrapper DAMAGE_UNDEAD = new EnchantmentWrapper(Enchantment.DAMAGE_UNDEAD.getKey(), "DAMAGE_UNDEAD");
	public static final EnchantmentWrapper SOUL_SPEED = new EnchantmentWrapper(Enchantment.SOUL_SPEED.getKey(), "SOUL_SPEED");
	public static final EnchantmentWrapper SWEEPING_EDGE = new EnchantmentWrapper(Enchantment.SWEEPING_EDGE.getKey(), "SWEEPING_EDGE");
	public static final EnchantmentWrapper SWIFT_SNEAK = new EnchantmentWrapper(new EnchantmentData("SWIFT_SNEAK") == null ? null : new EnchantmentData("SWIFT_SNEAK").getEnchantment().getKey(), "WATER_WORKER");
	public static final EnchantmentWrapper THORNS = new EnchantmentWrapper(Enchantment.THORNS.getKey(), "THORNS");
	public static final EnchantmentWrapper DURABILITY = new EnchantmentWrapper(Enchantment.DURABILITY.getKey(), "DURABILITY");
	
	public static final EnchantmentWrapper SOULBOUND = new CustomEnchantmentWrapper("soulbound", "SOULBOUND");
	public static final EnchantmentWrapper SOUL_REAPER = new CustomEnchantmentWrapper("soul_reaper", "SOUL_REAPER");
	public static final EnchantmentWrapper SHOCK_ASPECT = new CustomEnchantmentWrapper("shock_aspect", "SHOCK_ASPECT");
	public static final EnchantmentWrapper LIFE = new CustomEnchantmentWrapper("life", "LIFE");
	public static final EnchantmentWrapper BEHEADING = new CustomEnchantmentWrapper("beheading", "BEHEADING");
	public static final EnchantmentWrapper KNOCKUP = new CustomEnchantmentWrapper("knockip", "KNOCKUP");
	public static final EnchantmentWrapper WARP = new CustomEnchantmentWrapper("warp", "WARP");
	public static final EnchantmentWrapper EXP_SHARE = new CustomEnchantmentWrapper("exp_share", "EXP_SHARE");
	public static final EnchantmentWrapper MAGMA_WALKER = new CustomEnchantmentWrapper("magma_walker", "MAGMA_WALKER");
	public static final EnchantmentWrapper SNIPER = new CustomEnchantmentWrapper("sniper", "SNIPER");
	public static final EnchantmentWrapper TELEPATHY = new CustomEnchantmentWrapper("telepathy", "TELEPATHY");
	public static final EnchantmentWrapper SMELTERY = new CustomEnchantmentWrapper("smeltery", "SMELTERY");
	public static final EnchantmentWrapper SACRIFICE = new CustomEnchantmentWrapper("sacrifice", "SACRIFICE");
	public static final EnchantmentWrapper ANGLER = new CustomEnchantmentWrapper("angler", "ANGLER");
	public static final EnchantmentWrapper FRIED = new CustomEnchantmentWrapper("fried", "FRIED");
	public static final EnchantmentWrapper FREQUENT_FLYER = new CustomEnchantmentWrapper("frequent_flyer", "FREQUENT_FLYER");
	public static final EnchantmentWrapper TANK = new CustomEnchantmentWrapper("tank", "TANK");
	public static final EnchantmentWrapper BRINE = new CustomEnchantmentWrapper("brine", "BRINE");
	public static final EnchantmentWrapper DROWNED = new CustomEnchantmentWrapper("drowned", "DROWNED");
	public static final EnchantmentWrapper UNREST = new CustomEnchantmentWrapper("unrest", "UNREST");
	public static final EnchantmentWrapper NO_REST = new CustomEnchantmentWrapper("no_rest", "NO_REST");
	public static final EnchantmentWrapper WIDTH_PLUS_PLUS = new CustomEnchantmentWrapper("width_plus_plus", "WIDTH_PLUS_PLUS");
	public static final EnchantmentWrapper HEIGHT_PLUS_PLUS = new CustomEnchantmentWrapper("height_plus_plus", "HEIGHT_PLUS_PLUS");
	public static final EnchantmentWrapper VOID_WALKER = new CustomEnchantmentWrapper("void_walker", "VOID_WALKER");
	public static final EnchantmentWrapper ICARUS = new CustomEnchantmentWrapper("icarus", "ICARUS");
	public static final EnchantmentWrapper IRON_DEFENSE = new CustomEnchantmentWrapper("iron_defense", "IRON_DEFENSE");
	public static final EnchantmentWrapper HARD_BOUNCE = new CustomEnchantmentWrapper("hard_bounce", "HARD_BOUNCE");
	public static final EnchantmentWrapper MAGIC_GUARD = new CustomEnchantmentWrapper("magic_guard", "MAGIC_GUARD");
	public static final EnchantmentWrapper SPLATTER_FEST = new CustomEnchantmentWrapper("splatter_fest", "SPLATTER_FEST");
	public static final EnchantmentWrapper SAND_VEIL = new CustomEnchantmentWrapper("sand_veil", "SAND_VEIL");
	public static final EnchantmentWrapper TRANSMUTATION = new CustomEnchantmentWrapper("transmutation", "TRANSMUTATION");
	public static final EnchantmentWrapper GOLD_DIGGER = new CustomEnchantmentWrapper("gold_digger", "GOLD_DIGGER");
	public static final EnchantmentWrapper FLOWER_GIFT = new CustomEnchantmentWrapper("flower_gift", "FLOWER_GIFT");
	public static final EnchantmentWrapper STONE_THROW = new CustomEnchantmentWrapper("stone_throw", "STONE_THROW");
	public static final EnchantmentWrapper PILLAGE = new CustomEnchantmentWrapper("pillage", "PILLAGE");
	public static final EnchantmentWrapper GUNG_HO = new CustomEnchantmentWrapper("gung_ho", "GUNG_HO");
	public static final EnchantmentWrapper WAND = new CustomEnchantmentWrapper("wand", "WAND");
	public static final EnchantmentWrapper MOISTURIZE = new CustomEnchantmentWrapper("moisturize", "MOISTURIZE");
	public static final EnchantmentWrapper IRENES_LASSO = new CustomEnchantmentWrapper("irenes_lasso", "LASSO_OF_IRENE");
	public static final EnchantmentWrapper CURSE_OF_LAG = new CustomEnchantmentWrapper("lagging_curse", "LAGGING_CURSE");
	public static final EnchantmentWrapper CURSE_OF_EXHAUSTION = new CustomEnchantmentWrapper("exhaustion_curse", "EXHAUSTION_CURSE");
	public static final EnchantmentWrapper QUICK_STRIKE = new CustomEnchantmentWrapper("quick_strike", "QUICK_STRIKE");
	public static final EnchantmentWrapper TOUGHNESS = new CustomEnchantmentWrapper("toughness", "TOUGHNESS");
	public static final EnchantmentWrapper ARMORED = new CustomEnchantmentWrapper("armored", "ARMORED");
	public static final EnchantmentWrapper HOLLOW_POINT = new CustomEnchantmentWrapper("hollow_point", "HOLLOW_POINT");
	public static final EnchantmentWrapper DETONATOR = new CustomEnchantmentWrapper("detonator", "DETONATOR");
	public static final EnchantmentWrapper OVERKILL = new CustomEnchantmentWrapper("overkill", "OVERKILL");
	public static final EnchantmentWrapper CURSE_OF_CONTAGION = new CustomEnchantmentWrapper("contagion_curse", "CONTAGION_CURSE");
	public static final EnchantmentWrapper RECYCLER = new CustomEnchantmentWrapper("recycler", "RECYCLER");
	public static final EnchantmentWrapper LIGHT_WEIGHT = new CustomEnchantmentWrapper("light_weight", "LIGHT_WEIGHT");
	public static final EnchantmentWrapper HUSBANDRY = new CustomEnchantmentWrapper("husbandry", "HUSBANDRY");
	public static final EnchantmentWrapper BUTCHER = new CustomEnchantmentWrapper("butcher", "BUTCHER");
	public static final EnchantmentWrapper CURSE_OF_STAGNANCY = new CustomEnchantmentWrapper("stagnancy_curse", "STAGNANCY_CURSE");
	public static final EnchantmentWrapper STICKY_HOLD = new CustomEnchantmentWrapper("sticky_hold", "STICKY_HOLD");
	public static final EnchantmentWrapper FORCE_FEED = new CustomEnchantmentWrapper("force_feed", "FORCE_FEED");
	public static final EnchantmentWrapper PUSHBACK = new CustomEnchantmentWrapper("pushback", "PUSHBACK");
	public static final EnchantmentWrapper WATER_BREATHING = new CustomEnchantmentWrapper("water_breathing", "WATER_BREATHING");
	public static final EnchantmentWrapper LIFE_DRAIN = new CustomEnchantmentWrapper("life_drain", "LIFE_DRAIN");
	public static final EnchantmentWrapper CURSE_OF_INSTABILITY = new CustomEnchantmentWrapper("instability_curse", "INSTABILITY_CURSE");
	public static final EnchantmentWrapper BLINDNESS = new CustomEnchantmentWrapper("blindness", "BLINDNESS");
	public static final EnchantmentWrapper JOGGERS = new CustomEnchantmentWrapper("joggers", "JOGGERS");
	public static final EnchantmentWrapper PLYOMETRICS = new CustomEnchantmentWrapper("plyometrics", "PLYOMETRICS");
	public static final EnchantmentWrapper TRUANT = new CustomEnchantmentWrapper("truant", "TRUANT");
	public static final EnchantmentWrapper VENOM = new CustomEnchantmentWrapper("venom", "VENOM");
	public static final EnchantmentWrapper WITHERING = new CustomEnchantmentWrapper("withering", "WITHERING");
	public static final EnchantmentWrapper FROSTY = new CustomEnchantmentWrapper("frosty", "FROSTY");
	public static final EnchantmentWrapper ZEAL = new CustomEnchantmentWrapper("zeal", "ZEAL");
	public static final EnchantmentWrapper DEPTH_PLUS_PLUS = new CustomEnchantmentWrapper("depth_plus_plus", "DEPTH_PLUS_PLUS");
	public static final EnchantmentWrapper GAIA = new CustomEnchantmentWrapper("gaia", "GAIA");
	public static final EnchantmentWrapper PACIFIED = new CustomEnchantmentWrapper("pacified", "PACIFIED");
	public static final EnchantmentWrapper STREAK = new CustomEnchantmentWrapper("streak", "STREAK");
	public static final EnchantmentWrapper GREEN_THUMB = new CustomEnchantmentWrapper("green_thumb", "GREEN_THUMB");
	public static final EnchantmentWrapper FLING = new CustomEnchantmentWrapper("fling", "FLING");
	public static final EnchantmentWrapper FLASH = new CustomEnchantmentWrapper("flash", "FLASH");
	public static final EnchantmentWrapper LANCER = new CustomEnchantmentWrapper("lancer", "LANCER");
	public static final EnchantmentWrapper JAVELIN = new CustomEnchantmentWrapper("javelin", "JAVELIN");
	public static final EnchantmentWrapper BANE_OF_ANTHROPOIDS = new CustomEnchantmentWrapper("bane_of_anthropoids", "BANE_OF_ANTHROPOIDS");
	public static final EnchantmentWrapper RARE_EARTH = new CustomEnchantmentWrapper("rare_earth", "RARE_EARTH");
	public static final EnchantmentWrapper WHIPPED = new CustomEnchantmentWrapper("whipped", "WHIPPED");
	public static final EnchantmentWrapper CURSE_OF_INFESTATION = new CustomEnchantmentWrapper("infestation_curse", "INFESTATION_CURSE");
	public static final EnchantmentWrapper[] HWD = new EnchantmentWrapper[] { HEIGHT_PLUS_PLUS, WIDTH_PLUS_PLUS, DEPTH_PLUS_PLUS };

	private RegisterEnchantments() {}

	public static List<CustomEnchantment> getEnchantments() {
		return ENCHANTMENTS;
	}

	public static List<CustomEnchantment> getDisabledEnchantments() {
		return DISABLED_ENCHANTMENTS;
	}

	public static List<CustomEnchantment> getRegisteredEnchantments() {
		return REGISTERED_ENCHANTMENTS;
	}

	public static List<EnchantmentWrapper> getHWD() {
		return Arrays.asList(HWD);
	}

	public static List<CustomEnchantment> getRegisteredEnchantmentsAlphabetical() {
		List<CustomEnchantment> alphabetical = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: REGISTERED_ENCHANTMENTS)
			if (enchantment.isEnabled()) alphabetical.add(enchantment);
		Collections.sort(alphabetical, (o1, o2) -> ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', o1.getDisplayName())).compareTo(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', o2.getDisplayName()))));
		return alphabetical;
	}

	public static CustomEnchantment getCustomEnchantment(Enchantment enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS)
			if (enchant.getKey().equals(enchantment.getRelativeEnchantment().getKey())) return enchantment;
		return null;
	}

	public static CustomEnchantment getCustomEnchantment(EnchantmentWrapper enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS)
			if (enchant.equals(enchantment.getRelativeEnchantment())) return enchantment;
		return null;
	}

	public static List<CustomEnchantment> getCurseEnchantments() {
		if (CURSE_ENCHANTMENTS != null) return CURSE_ENCHANTMENTS;
		CURSE_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: ENCHANTMENTS) {
			if (RegisterEnchantments.CURSE_OF_CONTAGION.equals(enchantment.getRelativeEnchantment()) || RegisterEnchantments.CURSE_OF_STAGNANCY.equals(enchantment.getRelativeEnchantment())) continue;
			if (enchantment.isCurse()) CURSE_ENCHANTMENTS.add(enchantment);
		}
		return CURSE_ENCHANTMENTS;
	}

	public static List<EnchantmentWrapper> getProtectionEnchantments() {
		return Arrays.asList(PROTECTION_ENVIRONMENTAL, PROTECTION_EXPLOSIONS, PROTECTION_FIRE, PROTECTION_PROJECTILE);
	}

	public static boolean registerEnchantment(CustomEnchantment enchantment) {
		if (REGISTERED_ENCHANTMENTS.contains(enchantment)) return true;
		REGISTERED_ENCHANTMENTS.add(enchantment);
		boolean custom = enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper;
		String success_message = "Added the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment.";
		Chatable.sendDebug(success_message);
		return true;
	}

	public static void addDefaultEnchantment(CustomEnchantment enchant) {
		ENCHANTMENTS.add(enchant);
	}

	public static void setEnchantments() {
		CURSE_ENCHANTMENTS = null;
		DISABLED_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
		boolean levelFifty = ConfigString.LEVEL_FIFTY.getBoolean();
		Chatable.get().sendInfo("Initializing enchantments...");
		for(int i = 0; i < ENCHANTMENTS.size(); i++) {
			CustomEnchantment enchantment = ENCHANTMENTS.get(i);
			Configurations c = EnchantmentSolution.getPlugin().getConfigurations();
			LanguageConfiguration language = c.getLanguage();
			EnchantmentsConfiguration config = c.getEnchantments();
			boolean advanced = ConfigString.ADVANCED_OPTIONS.getBoolean();

			String namespace = "default_enchantments";
			if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					Chatable.get().sendWarning("Enchantment " + enchantment.getName() + " (Display Name " + ChatColor.translateAlternateColorCodes('&', enchantment.getDisplayName()) + ")" + " does not have a JavaPlugin set. Refusing to set.");
					continue;
				}
				namespace = plugin.getName().toLowerCase(Locale.ROOT);
			} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) namespace = "custom_enchantments";

			if (registerEnchantment(enchantment)) {
				if (config.getBoolean(namespace + "." + enchantment.getName() + ".enabled")) enchantment.setEnabled(true);
				else {
					enchantment.setEnabled(false);
					DISABLED_ENCHANTMENTS.add(enchantment);
				}
			} else {
				enchantment.setEnabled(false);
				DISABLED_ENCHANTMENTS.add(enchantment);
			}
			String displayName = StringUtils.decodeString(language.getString("enchantment.display_names." + namespace + "." + enchantment.getName()));
			String description = StringUtils.decodeString(language.getString("enchantment.descriptions." + namespace + "." + enchantment.getName()));

			List<ItemType> enchantmentTypes = getTypes(config, namespace, enchantment, "enchantment_item_types");
			List<ItemType> anvilTypes = getTypes(config, namespace, enchantment, "anvil_item_types");
			List<EnchantmentLocation> locations = getEnchantmentLocations(config, namespace, enchantment);
			if (advanced) {
				int constant = config.getInt(namespace + "." + enchantment.getName() + ".advanced.enchantability_constant");
				int modifier = config.getInt(namespace + "." + enchantment.getName() + ".advanced.enchantability_modifier");
				int startLevel = config.getInt(namespace + "." + enchantment.getName() + ".advanced.enchantability_start_level");
				int maxLevel = config.getInt(namespace + "." + enchantment.getName() + ".advanced.enchantability_max_level");
				Weight weight = Weight.getWeight(config.getString(namespace + "." + enchantment.getName() + ".advanced.weight"));
				List<String> conflictingEnchantmentsString = config.getStringList(namespace + "." + enchantment.getName() + ".advanced.conflicting_enchantments");
				List<EnchantmentWrapper> conflictingEnchantments = new ArrayList<EnchantmentWrapper>();
				if (conflictingEnchantmentsString != null) for(String s: conflictingEnchantmentsString) {
					CustomEnchantment enchant = getByName(s);
					if (enchant != null) conflictingEnchantments.add(enchant.getRelativeEnchantment());
				}

				enchantment.setCustom(constant, modifier, startLevel, maxLevel, weight, enchantmentTypes, anvilTypes, locations);
				enchantment.setConflictingEnchantments(conflictingEnchantments);
			} else if (levelFifty) enchantment.setLevelFifty(enchantmentTypes, anvilTypes, locations);
			else
				enchantment.setLevelThirty(enchantmentTypes, anvilTypes, locations);
			enchantment.setDisplayName(displayName);
			enchantment.setDescription(description);
		}
		Chatable.get().sendInfo("All enchantments initialized!");
	}

	public static boolean isEnabled(EnchantmentWrapper enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS)
			if (enchant.equals(enchantment.getRelativeEnchantment())) return enchantment.isEnabled();
		return false;
	}

	public static void addEnchantments() {
		if (getEnchantments().size() > 0) return;
		addDefaultEnchantment(CERegister.AQUA_AFFINITY);
		addDefaultEnchantment(CERegister.BANE_OF_ARTHROPODS);
		addDefaultEnchantment(CERegister.BLAST_PROTECTION);
		addDefaultEnchantment(CERegister.CHANNELING);
		addDefaultEnchantment(CERegister.BINDING_CURSE);
		addDefaultEnchantment(CERegister.VANISHING_CURSE);
		addDefaultEnchantment(CERegister.DEPTH_STRIDER);
		addDefaultEnchantment(CERegister.EFFICIENCY);
		addDefaultEnchantment(CERegister.FEATHER_FALLING);
		addDefaultEnchantment(CERegister.FIRE_ASPECT);
		addDefaultEnchantment(CERegister.FIRE_PROTECTION);
		addDefaultEnchantment(CERegister.FLAME);
		addDefaultEnchantment(CERegister.FORTUNE);
		addDefaultEnchantment(CERegister.FROST_WALKER);
		addDefaultEnchantment(CERegister.IMPALING);
		addDefaultEnchantment(CERegister.INFINITY);
		addDefaultEnchantment(CERegister.KNOCKBACK);
		addDefaultEnchantment(CERegister.LOOTING);
		addDefaultEnchantment(CERegister.LOYALTY);
		addDefaultEnchantment(CERegister.LUCK_OF_THE_SEA);
		addDefaultEnchantment(CERegister.LURE);
		addDefaultEnchantment(CERegister.MENDING);
		addDefaultEnchantment(CERegister.MULTISHOT);
		addDefaultEnchantment(CERegister.PIERCING);
		addDefaultEnchantment(CERegister.POWER);
		addDefaultEnchantment(CERegister.PROJECTILE_PROTECTION);
		addDefaultEnchantment(CERegister.PROTECTION);
		addDefaultEnchantment(CERegister.PUNCH);
		addDefaultEnchantment(CERegister.QUICK_CHARGE);
		addDefaultEnchantment(CERegister.RESPIRATION);
		addDefaultEnchantment(CERegister.RIPTIDE);
		addDefaultEnchantment(CERegister.SHARPNESS);
		addDefaultEnchantment(CERegister.SILK_TOUCH);
		addDefaultEnchantment(CERegister.SMITE);
		addDefaultEnchantment(CERegister.SOUL_SPEED);
		if (VersionUtils.isSimilarOrAbove(1, 19, 0)) addDefaultEnchantment(CERegister.SWIFT_SNEAK);
		addDefaultEnchantment(CERegister.SWEEPING_EDGE);
		addDefaultEnchantment(CERegister.THORNS);
		addDefaultEnchantment(CERegister.UNBREAKING);

		addDefaultEnchantment(CERegister.ANGLER);
		addDefaultEnchantment(CERegister.ARMORED);
		addDefaultEnchantment(CERegister.BANE_OF_ANTHROPOIDS);
		addDefaultEnchantment(CERegister.BEHEADING);
		addDefaultEnchantment(CERegister.BLINDNESS);
		addDefaultEnchantment(CERegister.BRINE);
		addDefaultEnchantment(CERegister.BUTCHER);
		addDefaultEnchantment(CERegister.CURSE_OF_CONTAGION);
		addDefaultEnchantment(CERegister.CURSE_OF_EXHAUSTION);
		addDefaultEnchantment(CERegister.CURSE_OF_INFESTATION);
		addDefaultEnchantment(CERegister.CURSE_OF_INSTABILITY);
		addDefaultEnchantment(CERegister.CURSE_OF_LAG);
		addDefaultEnchantment(CERegister.CURSE_OF_STAGNANCY);
		addDefaultEnchantment(CERegister.DEPTH_PLUS_PLUS);
		addDefaultEnchantment(CERegister.DETONATOR);
		addDefaultEnchantment(CERegister.DROWNED);
		addDefaultEnchantment(CERegister.EXP_SHARE);
		if (VersionUtils.isSimilarOrAbove(1, 17, 0)) addDefaultEnchantment(CERegister.FLASH);
		addDefaultEnchantment(CERegister.FLING);
		addDefaultEnchantment(CERegister.FLOWER_GIFT);
		addDefaultEnchantment(CERegister.FORCE_FEED);
		addDefaultEnchantment(CERegister.FREQUENT_FLYER);
		addDefaultEnchantment(CERegister.FRIED);
		addDefaultEnchantment(CERegister.FROSTY);
		addDefaultEnchantment(CERegister.GAIA);
		addDefaultEnchantment(CERegister.GOLD_DIGGER);
		addDefaultEnchantment(CERegister.GREEN_THUMB);
		addDefaultEnchantment(CERegister.GUNG_HO);
		addDefaultEnchantment(CERegister.HARD_BOUNCE);
		addDefaultEnchantment(CERegister.HEIGHT_PLUS_PLUS);
		addDefaultEnchantment(CERegister.HOLLOW_POINT);
		addDefaultEnchantment(CERegister.HUSBANDRY);
		addDefaultEnchantment(CERegister.ICARUS);
		addDefaultEnchantment(CERegister.IRENES_LASSO);
		addDefaultEnchantment(CERegister.IRON_DEFENSE);
		addDefaultEnchantment(CERegister.JAVELIN);
		addDefaultEnchantment(CERegister.JOGGERS);
		addDefaultEnchantment(CERegister.KNOCKUP);
		addDefaultEnchantment(CERegister.LANCER);
		addDefaultEnchantment(CERegister.LIGHT_WEIGHT);
		addDefaultEnchantment(CERegister.LIFE);
		addDefaultEnchantment(CERegister.LIFE_DRAIN);
		addDefaultEnchantment(CERegister.MAGIC_GUARD);
		addDefaultEnchantment(CERegister.MAGMA_WALKER);
		addDefaultEnchantment(CERegister.MOISTURIZE);
		addDefaultEnchantment(CERegister.NO_REST);
		addDefaultEnchantment(CERegister.OVERKILL);
		addDefaultEnchantment(CERegister.PACIFIED);
		addDefaultEnchantment(CERegister.PILLAGE);
		addDefaultEnchantment(CERegister.PLYOMETRICS);
		addDefaultEnchantment(CERegister.PUSHBACK);
		addDefaultEnchantment(CERegister.QUICK_STRIKE);
		if (VersionUtils.isSimilarOrAbove(1, 17, 0)) addDefaultEnchantment(CERegister.RARE_EARTH);
		addDefaultEnchantment(CERegister.RECYCLER);
		addDefaultEnchantment(CERegister.SACRIFICE);
		addDefaultEnchantment(CERegister.SAND_VEIL);
		addDefaultEnchantment(CERegister.SHOCK_ASPECT);
		addDefaultEnchantment(CERegister.SMELTERY);
		addDefaultEnchantment(CERegister.SNIPER);
		addDefaultEnchantment(CERegister.SOULBOUND);
		addDefaultEnchantment(CERegister.SOUL_REAPER);
		addDefaultEnchantment(CERegister.SPLATTER_FEST);
		addDefaultEnchantment(CERegister.STICKY_HOLD);
		addDefaultEnchantment(CERegister.STONE_THROW);
		addDefaultEnchantment(CERegister.STREAK);
		addDefaultEnchantment(CERegister.TANK);
		addDefaultEnchantment(CERegister.TELEPATHY);
		addDefaultEnchantment(CERegister.TOUGHNESS);
		addDefaultEnchantment(CERegister.TRANSMUTATION);
		addDefaultEnchantment(CERegister.TRUANT);
		addDefaultEnchantment(CERegister.UNREST);
		addDefaultEnchantment(CERegister.VENOM);
		addDefaultEnchantment(CERegister.VOID_WALKER);
		addDefaultEnchantment(CERegister.WAND);
		addDefaultEnchantment(CERegister.WARP);
		addDefaultEnchantment(CERegister.WATER_BREATHING);
		addDefaultEnchantment(CERegister.WHIPPED);
		addDefaultEnchantment(CERegister.WIDTH_PLUS_PLUS);
		addDefaultEnchantment(CERegister.WITHERING);
		addDefaultEnchantment(CERegister.ZEAL);
	}

	public static List<String> getEnchantmentNames() {
		List<String> names = new ArrayList<String>();
		for(CustomEnchantment enchant: getEnchantments())
			names.add(enchant.getName());
		return names;
	}

	public static CustomEnchantment getByName(String name) {
		for(CustomEnchantment enchant: getEnchantments())
			if (enchant.getName().equalsIgnoreCase(name)) return enchant;
		return null;
	}

	public static EnchantmentWrapper getByKey(NamespacedKey key) {
		for(CustomEnchantment enchant: getEnchantments())
			if (enchant.getRelativeEnchantment().getKey().equals(key)) return enchant.getRelativeEnchantment();
		return null;
	}

	private static List<EnchantmentLocation> getEnchantmentLocations(Configuration config, String namespace, CustomEnchantment enchantment) {
		List<EnchantmentLocation> locations = new ArrayList<EnchantmentLocation>();
		List<String> enchantmentLocationsString = config.getStringList(namespace + "." + enchantment.getName() + ".enchantment_locations");
		if (enchantmentLocationsString != null) for(String s: enchantmentLocationsString)
			try {
				EnchantmentLocation location = EnchantmentLocation.valueOf(s.toUpperCase(Locale.ROOT));
				if (location != null) locations.add(location);
			} catch (Exception ex) {}
		return locations;
	}

	private static List<ItemType> getTypes(Configuration config, String namespace, CustomEnchantment enchantment, String path) {
		List<String> itemTypes = config.getStringList(namespace + "." + enchantment.getName() + "." + path);
		List<ItemType> types = new ArrayList<ItemType>();
		if (itemTypes != null) for(String s: itemTypes) {
			ItemType type = null;
			if (s.contains(":")) type = ItemType.getCustomType(VanillaItemType.get(s.toUpperCase(Locale.ROOT)), s.toUpperCase(Locale.ROOT));
			else
				type = ItemType.getItemType(s.toUpperCase(Locale.ROOT));
			if (type != null) types.add(type);
		}
		return types;
	}
}
