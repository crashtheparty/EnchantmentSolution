package org.ctp.enchantmentsolution.enchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.*;
import org.ctp.enchantmentsolution.utils.config.*;

public class RegisterEnchantments {
	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	private static List<CustomEnchantment> REGISTERED_ENCHANTMENTS = new ArrayList<CustomEnchantment>();

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
	public static final Enchantment WIDTH_PLUS_PLUS = new CustomEnchantmentWrapper("width_plus_plus",
	"WIDTH_PLUS_PLUS");
	public static final Enchantment HEIGHT_PLUS_PLUS = new CustomEnchantmentWrapper("height_plus_plus",
	"HEIGHT_PLUS_PLUS");
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
	public static final Enchantment CURSE_OF_EXHAUSTION = new CustomEnchantmentWrapper("exhaustion_curse",
	"EXHAUSTION_CURSE");
	public static final Enchantment QUICK_STRIKE = new CustomEnchantmentWrapper("quick_strike", "QUICK_STRIKE");
	public static final Enchantment TOUGHNESS = new CustomEnchantmentWrapper("toughness", "TOUGHNESS");
	public static final Enchantment ARMORED = new CustomEnchantmentWrapper("armored", "ARMORED");

	private RegisterEnchantments() {
	}

	public static List<CustomEnchantment> getEnchantments() {
		return ENCHANTMENTS;
	}

	public static List<CustomEnchantment> getRegisteredEnchantments() {
		return REGISTERED_ENCHANTMENTS;
	}

	public static List<CustomEnchantment> getRegisteredEnchantmentsAlphabetical() {
		List<CustomEnchantment> alphabetical = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: REGISTERED_ENCHANTMENTS) {
			alphabetical.add(enchantment);
		}
		Collections.sort(alphabetical, (o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));
		return alphabetical;
	}

	public static CustomEnchantment getCustomEnchantment(Enchantment enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS) {
			if (enchant.equals(enchantment.getRelativeEnchantment())) {
				if (!enchantment.isEnabled()) {
					return null;
				}
				return enchantment;
			}
		}
		return null;
	}

	public static boolean registerEnchantment(CustomEnchantment enchantment) {
		if (REGISTERED_ENCHANTMENTS.contains(enchantment)) {
			return true;
		}
		REGISTERED_ENCHANTMENTS.add(enchantment);
		boolean custom = enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper;
		String error_message = "Trouble adding the " + enchantment.getName() + (custom ? " custom" : "")
		+ " enchantment: ";
		String success_message = "Added the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment.";
		if (!custom) {
			ChatUtils.sendToConsole(Level.INFO, success_message);
			return true;
		}
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchantment.getRelativeEnchantment());
			ChatUtils.sendToConsole(Level.INFO, success_message);
			return true;
		} catch (Exception e) {
			REGISTERED_ENCHANTMENTS.remove(enchantment);

			ChatUtils.sendToConsole(Level.WARNING, error_message);
			e.printStackTrace();
			return false;
		}
	}

	private static void addDefaultEnchantment(CustomEnchantment enchant) {
		ENCHANTMENTS.add(enchant);
	}

	public static void setEnchantments() {
		boolean levelFifty = ConfigUtils.isLevel50();
		for(int i = 0; i < ENCHANTMENTS.size(); i++) {
			CustomEnchantment enchantment = ENCHANTMENTS.get(i);
			LanguageConfiguration language = Configurations.getLanguage();
			EnchantmentsConfiguration config = Configurations.getEnchantments();
			boolean advanced = config.getBoolean("advanced_features");

			String namespace = "default_enchantments";
			if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchantment.getName() + " (Display Name "
					+ enchantment.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to set.");
					continue;
				}
				namespace = plugin.getName().toLowerCase();
			} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				namespace = "custom_enchantments";
			}
			if (!advanced && !(enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
				if (registerEnchantment(enchantment)) {
					enchantment.setEnabled(true);
				} else {
					enchantment.setEnabled(false);
				}
				String description = StringUtils.decodeString(
				language.getString("enchantment.descriptions.default_enchantments." + enchantment.getName()));
				enchantment.setDescription(description);
				if (levelFifty) {
					enchantment.setLevelFifty();
				} else {
					enchantment.setLevelThirty();
				}
				continue;
			}

			if (registerEnchantment(enchantment)) {
				if (config.getBoolean(namespace + "." + enchantment.getName() + ".enabled")) {
					enchantment.setEnabled(true);
				} else {
					enchantment.setEnabled(false);
				}
			} else {
				enchantment.setEnabled(false);
			}
			if (config.getBoolean(namespace + "." + enchantment.getName() + ".treasure")) {
				enchantment.setTreasure(true);
			} else {
				enchantment.setTreasure(false);
			}
			String displayName = StringUtils.decodeString(
			language.getString("enchantment.display_names." + namespace + "." + enchantment.getName()));
			String description = StringUtils.decodeString(
			language.getString("enchantment.descriptions." + namespace + "." + enchantment.getName()));
			if (advanced) {
				int constant = config.getInt(namespace + "." + enchantment.getName() + ".enchantability_constant");
				int modifier = config.getInt(namespace + "." + enchantment.getName() + ".enchantability_modifier");
				int startLevel = config.getInt(namespace + "." + enchantment.getName() + ".enchantability_start_level");
				int maxLevel = config.getInt(namespace + "." + enchantment.getName() + ".enchantability_max_level");
				Weight weight = Weight.getWeight(config.getString(namespace + "." + enchantment.getName() + ".weight"));
				List<String> conflictingEnchantmentsString = config
				.getStringList(namespace + "." + enchantment.getName() + ".conflicting_enchantments");
				List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
				if (conflictingEnchantmentsString != null) {
					for(String s: conflictingEnchantmentsString) {
						CustomEnchantment enchant = getByName(s);
						if (enchant != null) {
							conflictingEnchantments.add(enchant.getRelativeEnchantment());
						}
					}
				}
				List<String> disabledItemsString = config
				.getStringList(namespace + "." + enchantment.getName() + ".disabled_items");
				List<Material> disabledItems = new ArrayList<Material>();
				if (disabledItemsString != null) {
					for(String s: disabledItemsString) {
						Material mat = Material.getMaterial(s);
						if (mat != null) {
							disabledItems.add(mat);
						}
					}
				}
				enchantment.setCustom(constant, modifier, startLevel, maxLevel, weight);
				enchantment.setConflictingEnchantments(conflictingEnchantments);
				enchantment.setDisabledItems(disabledItems);
			} else {
				if (levelFifty) {
					enchantment.setLevelFifty();
				} else {
					enchantment.setLevelThirty();
				}
			}
			if (!namespace.equals("default_enchantments")) {
				enchantment.setDisplayName(displayName);
			} else {
				enchantment.setDisplayName(ConfigUtils.getLanguage());
			}
			enchantment.setDescription(description);
		}
	}

	public static boolean isEnabled(Enchantment enchant) {
		for(CustomEnchantment enchantment: ENCHANTMENTS) {
			if (enchant.equals(enchantment.getRelativeEnchantment())) {
				return enchantment.isEnabled();
			}
		}
		return false;
	}

	public static void addRegisterEnchantments() {
		if (getEnchantments().size() > 0) {
			return;
		}
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
		if (VersionUtils.getBukkitVersionNumber() > 3) {
			addDefaultEnchantment(CERegister.MULTISHOT);
			addDefaultEnchantment(CERegister.PIERCING);
		}
		addDefaultEnchantment(CERegister.POWER);
		addDefaultEnchantment(CERegister.PROJECTILE_PROTECTION);
		addDefaultEnchantment(CERegister.PROTECTION);
		addDefaultEnchantment(CERegister.PUNCH);
		if (VersionUtils.getBukkitVersionNumber() > 3) {
			addDefaultEnchantment(CERegister.QUICK_CHARGE);
		}
		addDefaultEnchantment(CERegister.RESPIRATION);
		addDefaultEnchantment(CERegister.RIPTIDE);
		addDefaultEnchantment(CERegister.SHARPNESS);
		addDefaultEnchantment(CERegister.SILK_TOUCH);
		addDefaultEnchantment(CERegister.SMITE);
		addDefaultEnchantment(CERegister.SWEEPING_EDGE);
		addDefaultEnchantment(CERegister.THORNS);
		addDefaultEnchantment(CERegister.UNBREAKING);

		addDefaultEnchantment(CERegister.ANGLER);
		addDefaultEnchantment(CERegister.ARMORED);
		addDefaultEnchantment(CERegister.BEHEADING);
		addDefaultEnchantment(CERegister.BRINE);
		addDefaultEnchantment(CERegister.CURSE_OF_EXHAUSTION);
		addDefaultEnchantment(CERegister.CURSE_OF_LAG);
		addDefaultEnchantment(CERegister.DROWNED);
		addDefaultEnchantment(CERegister.EXP_SHARE);
		addDefaultEnchantment(CERegister.FLOWER_GIFT);
		addDefaultEnchantment(CERegister.FREQUENT_FLYER);
		addDefaultEnchantment(CERegister.FRIED);
		addDefaultEnchantment(CERegister.GOLD_DIGGER);
		addDefaultEnchantment(CERegister.GUNG_HO);
		addDefaultEnchantment(CERegister.HARD_BOUNCE);
		addDefaultEnchantment(CERegister.HEIGHT_PLUS_PLUS);
		addDefaultEnchantment(CERegister.ICARUS);
		addDefaultEnchantment(CERegister.IRENES_LASSO);
		addDefaultEnchantment(CERegister.IRON_DEFENSE);
		addDefaultEnchantment(CERegister.KNOCKUP);
		addDefaultEnchantment(CERegister.LIFE);
		addDefaultEnchantment(CERegister.MAGIC_GUARD);
		addDefaultEnchantment(CERegister.MAGMA_WALKER);
		addDefaultEnchantment(CERegister.MOISTURIZE);
		addDefaultEnchantment(CERegister.NO_REST);
		if (VersionUtils.getBukkitVersionNumber() > 3) {
			addDefaultEnchantment(CERegister.PILLAGE);
		}
		addDefaultEnchantment(CERegister.QUICK_STRIKE);
		addDefaultEnchantment(CERegister.SACRIFICE);
		addDefaultEnchantment(CERegister.SAND_VEIL);
		addDefaultEnchantment(CERegister.SHOCK_ASPECT);
		addDefaultEnchantment(CERegister.SMELTERY);
		addDefaultEnchantment(CERegister.SNIPER);
		addDefaultEnchantment(CERegister.SOULBOUND);
		addDefaultEnchantment(CERegister.SOUL_REAPER);
		addDefaultEnchantment(CERegister.SPLATTER_FEST);
		if (VersionUtils.getBukkitVersionNumber() > 3) {
			addDefaultEnchantment(CERegister.STONE_THROW);
		}
		addDefaultEnchantment(CERegister.TANK);
		addDefaultEnchantment(CERegister.TELEPATHY);
		addDefaultEnchantment(CERegister.TOUGHNESS);
		addDefaultEnchantment(CERegister.TRANSMUTATION);
		addDefaultEnchantment(CERegister.UNREST);
		addDefaultEnchantment(CERegister.VOID_WALKER);
		addDefaultEnchantment(CERegister.WAND);
		addDefaultEnchantment(CERegister.WARP);
		addDefaultEnchantment(CERegister.WIDTH_PLUS_PLUS);
	}

	public static List<String> getEnchantmentNames() {
		List<String> names = new ArrayList<String>();
		for(CustomEnchantment enchant: getEnchantments()) {
			names.add(enchant.getName());
		}
		return names;
	}

	public static CustomEnchantment getByName(String name) {
		for(CustomEnchantment enchant: getEnchantments()) {
			if (enchant.getName().equalsIgnoreCase(name)) {
				return enchant;
			}
		}
		return null;
	}
}
