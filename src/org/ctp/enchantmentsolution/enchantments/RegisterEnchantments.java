package org.ctp.enchantmentsolution.enchantments;

import java.lang.reflect.Field;
import java.util.*;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.config.Configuration;
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

	public static final Enchantment SOULBOUND = new CustomEnchantmentWrapper("soulbound", "SOULBOUND");
	public static final Enchantment SOUL_REAPER = new CustomEnchantmentWrapper("soul_reaper", "SOUL_REAPER");
	public static final Enchantment SHOCK_ASPECT = new CustomEnchantmentWrapper("shock_aspect", "SHOCK_ASPECT");
	public static final Enchantment LIFE = new CustomEnchantmentWrapper("life", "LIFE");
	public static final Enchantment BEHEADING = new CustomEnchantmentWrapper("beheading", "BEHEADING");
	public static final Enchantment KNOCKUP = new CustomEnchantmentWrapper("knockip", "KNOCKUP");
	public static final Enchantment WARP = new CustomEnchantmentWrapper("warp", "WARP");
	public static final Enchantment EXP_SHARE = new CustomEnchantmentWrapper("exp_share", "EXP_SHARE");
	public static final Enchantment MAGMA_WALKER = new CustomEnchantmentWrapper("magma_walker", "MAGMA_WALKER");
	public static final Enchantment SNIPER = new CustomEnchantmentWrapper("sniper", "SNIPER");
	public static final Enchantment TELEPATHY = new CustomEnchantmentWrapper("telepathy", "TELEPATHY");
	public static final Enchantment SMELTERY = new CustomEnchantmentWrapper("smeltery", "SMELTERY");
	public static final Enchantment SACRIFICE = new CustomEnchantmentWrapper("sacrifice", "SACRIFICE");
	public static final Enchantment ANGLER = new CustomEnchantmentWrapper("angler", "ANGLER");
	public static final Enchantment FRIED = new CustomEnchantmentWrapper("fried", "FRIED");
	public static final Enchantment FREQUENT_FLYER = new CustomEnchantmentWrapper("frequent_flyer", "FREQUENT_FLYER");
	public static final Enchantment TANK = new CustomEnchantmentWrapper("tank", "TANK");
	public static final Enchantment BRINE = new CustomEnchantmentWrapper("brine", "BRINE");
	public static final Enchantment DROWNED = new CustomEnchantmentWrapper("drowned", "DROWNED");
	public static final Enchantment UNREST = new CustomEnchantmentWrapper("unrest", "UNREST");
	public static final Enchantment NO_REST = new CustomEnchantmentWrapper("no_rest", "NO_REST");
	public static final Enchantment WIDTH_PLUS_PLUS = new CustomEnchantmentWrapper("width_plus_plus", "WIDTH_PLUS_PLUS");
	public static final Enchantment HEIGHT_PLUS_PLUS = new CustomEnchantmentWrapper("height_plus_plus", "HEIGHT_PLUS_PLUS");
	public static final Enchantment VOID_WALKER = new CustomEnchantmentWrapper("void_walker", "VOID_WALKER");
	public static final Enchantment ICARUS = new CustomEnchantmentWrapper("icarus", "ICARUS");
	public static final Enchantment IRON_DEFENSE = new CustomEnchantmentWrapper("iron_defense", "IRON_DEFENSE");
	public static final Enchantment HARD_BOUNCE = new CustomEnchantmentWrapper("hard_bounce", "HARD_BOUNCE");
	public static final Enchantment MAGIC_GUARD = new CustomEnchantmentWrapper("magic_guard", "MAGIC_GUARD");
	public static final Enchantment SPLATTER_FEST = new CustomEnchantmentWrapper("splatter_fest", "SPLATTER_FEST");
	public static final Enchantment SAND_VEIL = new CustomEnchantmentWrapper("sand_veil", "SAND_VEIL");
	public static final Enchantment TRANSMUTATION = new CustomEnchantmentWrapper("transmutation", "TRANSMUTATION");
	public static final Enchantment GOLD_DIGGER = new CustomEnchantmentWrapper("gold_digger", "GOLD_DIGGER");
	public static final Enchantment FLOWER_GIFT = new CustomEnchantmentWrapper("flower_gift", "FLOWER_GIFT");
	public static final Enchantment STONE_THROW = new CustomEnchantmentWrapper("stone_throw", "STONE_THROW");
	public static final Enchantment PILLAGE = new CustomEnchantmentWrapper("pillage", "PILLAGE");
	public static final Enchantment GUNG_HO = new CustomEnchantmentWrapper("gung_ho", "GUNG_HO");
	public static final Enchantment WAND = new CustomEnchantmentWrapper("wand", "WAND");
	public static final Enchantment MOISTURIZE = new CustomEnchantmentWrapper("moisturize", "MOISTURIZE");
	public static final Enchantment IRENES_LASSO = new CustomEnchantmentWrapper("irenes_lasso", "LASSO_OF_IRENE");
	public static final Enchantment CURSE_OF_LAG = new CustomEnchantmentWrapper("lagging_curse", "LAGGING_CURSE");
	public static final Enchantment CURSE_OF_EXHAUSTION = new CustomEnchantmentWrapper("exhaustion_curse", "EXHAUSTION_CURSE");
	public static final Enchantment QUICK_STRIKE = new CustomEnchantmentWrapper("quick_strike", "QUICK_STRIKE");
	public static final Enchantment TOUGHNESS = new CustomEnchantmentWrapper("toughness", "TOUGHNESS");
	public static final Enchantment ARMORED = new CustomEnchantmentWrapper("armored", "ARMORED");
	public static final Enchantment HOLLOW_POINT = new CustomEnchantmentWrapper("hollow_point", "HOLLOW_POINT");
	public static final Enchantment DETONATOR = new CustomEnchantmentWrapper("detonator", "DETONATOR");
	public static final Enchantment OVERKILL = new CustomEnchantmentWrapper("overkill", "OVERKILL");
	public static final Enchantment CURSE_OF_CONTAGION = new CustomEnchantmentWrapper("contagion_curse", "CONTAGION_CURSE");
	public static final Enchantment RECYCLER = new CustomEnchantmentWrapper("recycler", "RECYCLER");
	public static final Enchantment LIGHT_WEIGHT = new CustomEnchantmentWrapper("light_weight", "LIGHT_WEIGHT");
	public static final Enchantment HUSBANDRY = new CustomEnchantmentWrapper("husbandry", "HUSBANDRY");
	public static final Enchantment BUTCHER = new CustomEnchantmentWrapper("butcher", "BUTCHER");
	public static final Enchantment CURSE_OF_STAGNANCY = new CustomEnchantmentWrapper("stagnancy_curse", "STAGNANCY_CURSE");
	public static final Enchantment STICKY_HOLD = new CustomEnchantmentWrapper("sticky_hold", "STICKY_HOLD");
	public static final Enchantment FORCE_FEED = new CustomEnchantmentWrapper("force_feed", "FORCE_FEED");
	public static final Enchantment PUSHBACK = new CustomEnchantmentWrapper("pushback", "PUSHBACK");
	public static final Enchantment WATER_BREATHING = new CustomEnchantmentWrapper("water_breathing", "WATER_BREATHING");
	public static final Enchantment LIFE_DRAIN = new CustomEnchantmentWrapper("life_drain", "LIFE_DRAIN");
	public static final Enchantment CURSE_OF_INSTABILITY = new CustomEnchantmentWrapper("instability_curse", "INSTABILITY_CURSE");
	public static final Enchantment BLINDNESS = new CustomEnchantmentWrapper("blindness", "BLINDNESS");
	public static final Enchantment JOGGERS = new CustomEnchantmentWrapper("joggers", "JOGGERS");
	public static final Enchantment PLYOMETRICS = new CustomEnchantmentWrapper("plyometrics", "PLYOMETRICS");
	public static final Enchantment TRUANT = new CustomEnchantmentWrapper("truant", "TRUANT");
	public static final Enchantment VENOM = new CustomEnchantmentWrapper("venom", "VENOM");
	public static final Enchantment WITHERING = new CustomEnchantmentWrapper("withering", "WITHERING");
	public static final Enchantment FROSTY = new CustomEnchantmentWrapper("frosty", "FROSTY");
	public static final Enchantment ZEAL = new CustomEnchantmentWrapper("zeal", "ZEAL");
	public static final Enchantment DEPTH_PLUS_PLUS = new CustomEnchantmentWrapper("depth_plus_plus", "DEPTH_PLUS_PLUS");
	public static final Enchantment GAIA = new CustomEnchantmentWrapper("gaia", "GAIA");
	public static final Enchantment PACIFIED = new CustomEnchantmentWrapper("pacified", "PACIFIED");
	public static final Enchantment STREAK = new CustomEnchantmentWrapper("streak", "STREAK");
	public static final Enchantment GREEN_THUMB = new CustomEnchantmentWrapper("green_thumb", "GREEN_THUMB");
	public static final Enchantment[] HWD = new Enchantment[] { HEIGHT_PLUS_PLUS, WIDTH_PLUS_PLUS, DEPTH_PLUS_PLUS };

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

	public static List<Enchantment> getHWD() {
		return Arrays.asList(HWD);
	}

	public static List<CustomEnchantment> getRegisteredEnchantmentsAlphabetical() {
		List<CustomEnchantment> alphabetical = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: REGISTERED_ENCHANTMENTS)
			if (enchantment.isEnabled()) alphabetical.add(enchantment);
		Collections.sort(alphabetical, (o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));
		return alphabetical;
	}

	public static CustomEnchantment getCustomEnchantment(Enchantment enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS)
			if (enchant.equals(enchantment.getRelativeEnchantment())) {
				if (!enchantment.isEnabled()) return null;
				return enchantment;
			}
		return null;
	}

	public static List<CustomEnchantment> getCurseEnchantments() {
		if (CURSE_ENCHANTMENTS != null) return CURSE_ENCHANTMENTS;
		CURSE_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: getRegisteredEnchantments()) {
			if (enchantment.getRelativeEnchantment() == RegisterEnchantments.CURSE_OF_CONTAGION || enchantment.getRelativeEnchantment() == RegisterEnchantments.CURSE_OF_STAGNANCY) continue;
			if (enchantment.isCurse()) CURSE_ENCHANTMENTS.add(enchantment);
		}
		return CURSE_ENCHANTMENTS;
	}

	public static List<Enchantment> getProtectionEnchantments() {
		return Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE);
	}

	public static boolean registerEnchantment(CustomEnchantment enchantment) {
		if (REGISTERED_ENCHANTMENTS.contains(enchantment)) return true;
		REGISTERED_ENCHANTMENTS.add(enchantment);
		JavaPlugin plugin = EnchantmentSolution.getPlugin();
		if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
		boolean custom = enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper;
		String error_message = "Trouble adding the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment: ";
		String success_message = "Added the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment.";
		if (!custom || Enchantment.getByKey(enchantment.getRelativeEnchantment().getKey()) != null) {
			Chatable.get().sendInfo(success_message);
			return true;
		}
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchantment.getRelativeEnchantment());
			Chatable.get().sendInfo(plugin, success_message);
			return true;
		} catch (Exception e) {
			REGISTERED_ENCHANTMENTS.remove(enchantment);

			Chatable.get().sendWarning(plugin, error_message);
			e.printStackTrace();
			return false;
		}
	}

	public static void addDefaultEnchantment(CustomEnchantment enchant) {
		ENCHANTMENTS.add(enchant);
	}

	public static void setEnchantments() {
		CURSE_ENCHANTMENTS = null;
		DISABLED_ENCHANTMENTS = new ArrayList<CustomEnchantment>();
		boolean levelFifty = ConfigString.LEVEL_FIFTY.getBoolean();
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
					Chatable.get().sendWarning("Enchantment " + enchantment.getName() + " (Display Name " + enchantment.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to set.");
					continue;
				}
				namespace = plugin.getName().toLowerCase(Locale.ROOT);
			} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) namespace = "custom_enchantments";
			if (!advanced && !(enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
				List<ItemType> enchantmentTypes = getTypes(config, namespace, enchantment, "enchantment_item_types");
				List<ItemType> anvilTypes = getTypes(config, namespace, enchantment, "anvil_item_types");
				List<EnchantmentLocation> locations = getEnchantmentLocations(config, namespace, enchantment);
				if (registerEnchantment(enchantment)) enchantment.setEnabled(true);
				else {
					enchantment.setEnabled(false);
					DISABLED_ENCHANTMENTS.add(enchantment);
				}
				String description = StringUtils.decodeString(language.getString("enchantment.descriptions.default_enchantments." + enchantment.getName()));
				enchantment.setDescription(description);
				if (levelFifty) enchantment.setLevelFifty(enchantmentTypes, anvilTypes, locations);
				else
					enchantment.setLevelThirty(enchantmentTypes, anvilTypes, locations);
				continue;
			}

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
				List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
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
	}

	public static boolean isEnabled(Enchantment enchant) {
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
		if (VersionUtils.getBukkitVersionNumber() > 3 || VersionUtils.getBukkitVersionNumber() == 0) {
			addDefaultEnchantment(CERegister.MULTISHOT);
			addDefaultEnchantment(CERegister.PIERCING);
		}
		addDefaultEnchantment(CERegister.POWER);
		addDefaultEnchantment(CERegister.PROJECTILE_PROTECTION);
		addDefaultEnchantment(CERegister.PROTECTION);
		addDefaultEnchantment(CERegister.PUNCH);
		if (VersionUtils.getBukkitVersionNumber() > 3 || VersionUtils.getBukkitVersionNumber() == 0) addDefaultEnchantment(CERegister.QUICK_CHARGE);
		addDefaultEnchantment(CERegister.RESPIRATION);
		addDefaultEnchantment(CERegister.RIPTIDE);
		addDefaultEnchantment(CERegister.SHARPNESS);
		addDefaultEnchantment(CERegister.SILK_TOUCH);
		addDefaultEnchantment(CERegister.SMITE);
		if (VersionUtils.getBukkitVersionNumber() > 11) addDefaultEnchantment(CERegister.SOUL_SPEED);
		addDefaultEnchantment(CERegister.SWEEPING_EDGE);
		addDefaultEnchantment(CERegister.THORNS);
		addDefaultEnchantment(CERegister.UNBREAKING);

		addDefaultEnchantment(CERegister.ANGLER);
		addDefaultEnchantment(CERegister.ARMORED);
		addDefaultEnchantment(CERegister.BEHEADING);
		addDefaultEnchantment(CERegister.BLINDNESS);
		addDefaultEnchantment(CERegister.BRINE);
		addDefaultEnchantment(CERegister.BUTCHER);
		addDefaultEnchantment(CERegister.CURSE_OF_CONTAGION);
		addDefaultEnchantment(CERegister.CURSE_OF_EXHAUSTION);
		addDefaultEnchantment(CERegister.CURSE_OF_INSTABILITY);
		addDefaultEnchantment(CERegister.CURSE_OF_LAG);
		addDefaultEnchantment(CERegister.CURSE_OF_STAGNANCY);
		addDefaultEnchantment(CERegister.DEPTH_PLUS_PLUS);
		addDefaultEnchantment(CERegister.DETONATOR);
		addDefaultEnchantment(CERegister.DROWNED);
		addDefaultEnchantment(CERegister.EXP_SHARE);
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
		addDefaultEnchantment(CERegister.JOGGERS);
		addDefaultEnchantment(CERegister.KNOCKUP);
		addDefaultEnchantment(CERegister.LIGHT_WEIGHT);
		addDefaultEnchantment(CERegister.LIFE);
		addDefaultEnchantment(CERegister.LIFE_DRAIN);
		addDefaultEnchantment(CERegister.MAGIC_GUARD);
		addDefaultEnchantment(CERegister.MAGMA_WALKER);
		addDefaultEnchantment(CERegister.MOISTURIZE);
		addDefaultEnchantment(CERegister.NO_REST);
		addDefaultEnchantment(CERegister.OVERKILL);
		addDefaultEnchantment(CERegister.PACIFIED);
		if (VersionUtils.getBukkitVersionNumber() > 3 || VersionUtils.getBukkitVersionNumber() == 0) addDefaultEnchantment(CERegister.PILLAGE);
		addDefaultEnchantment(CERegister.PLYOMETRICS);
		addDefaultEnchantment(CERegister.PUSHBACK);
		addDefaultEnchantment(CERegister.QUICK_STRIKE);
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
		if (VersionUtils.getBukkitVersionNumber() > 3 || VersionUtils.getBukkitVersionNumber() == 0) addDefaultEnchantment(CERegister.STONE_THROW);
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

	public static Enchantment getByKey(NamespacedKey key) {
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
